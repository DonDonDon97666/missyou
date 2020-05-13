package com.lin.missyou.service;

import com.lin.missyou.core.LocalUser;
import com.lin.missyou.core.enumeration.OrderStatus;
import com.lin.missyou.core.money.IMoneyDiscount;
import com.lin.missyou.dto.OrderDTO;
import com.lin.missyou.dto.SkuInfoDTO;
import com.lin.missyou.exception.http.ForbiddenException;
import com.lin.missyou.exception.http.NotFoundException;
import com.lin.missyou.exception.http.ParameterException;
import com.lin.missyou.logic.CouponChecker;
import com.lin.missyou.logic.OrderChecker;
import com.lin.missyou.model.Coupon;
import com.lin.missyou.model.Order;
import com.lin.missyou.model.OrderSku;
import com.lin.missyou.model.Sku;
import com.lin.missyou.repository.CouponRepository;
import com.lin.missyou.repository.OrderRepository;
import com.lin.missyou.repository.SkuRepository;
import com.lin.missyou.repository.UserCouponRepozitory;
import com.lin.missyou.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/29
 */
@Service
public class OrderService {

    @Autowired
    private SkuService skuService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private IMoneyDiscount iMoneyDiscount;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${missyou.order.max-sku-limit}")
    private int maxSkuLimit;

    @Value("${missyou.order.pay-time-limit}")
    private Integer payTimeLimit;

    @Autowired
    private UserCouponRepozitory userCouponRepozitory;

    public Page<Order> getUnpaid(Integer page, Integer count) {
        Pageable pageable = PageRequest.of(page, count, Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        LocalDateTime now = LocalDateTime.now();
        return this.orderRepository.findAllByExpiredTimeGreaterThanAndStatusAndUserId(now, OrderStatus.UNPAID.value(), uid, pageable);
    }

    public void updateOrderPrepayId(Long orderId, String prePayId) {
        Optional<Order> order = this.orderRepository.findById(orderId);
        order.ifPresent(o -> {
            o.setPrepayId(prePayId);
            this.orderRepository.save(o);
        });
        order.orElseThrow(() -> new ParameterException(10007));
    }

    public Page<Order> getByStatus(Integer page, Integer count, Integer status) {
        Pageable pageable = PageRequest.of(page, count, Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        if (status.equals(OrderStatus.All.value())) {
            return this.orderRepository.findAllByUserId(uid, pageable);
        }
        return this.orderRepository.findAllByStatusAndUserId(status, uid, pageable);
    }

    public Optional<Order> getDetailById(Long id) {
        Long uid = LocalUser.getUser().getId();
        return this.orderRepository.findById(id);
    }

    /**
     * 下订单步骤：
     * 1、生成订单
     * 2、预减少库存
     * 3、核销优惠券
     * 4、将订单加入延迟消息队列
     *
     * @param uid
     * @param orderDTO
     * @param orderChecker
     * @return
     */
    @Transactional
    public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker) {
        String orderNo = OrderUtil.makeOrderNo();
        LocalDateTime placedOrderTime = LocalDateTime.now();
        LocalDateTime expiredTime = placedOrderTime.plusSeconds(payTimeLimit);

        Order order = Order.builder()
                .orderNo(orderNo)
                .totalPrice(orderDTO.getTotalPrice())
                .finalTotalPrice(orderDTO.getFinalTotalPrice())
                .userId(uid)
                .totalCount(orderChecker.getTotalCount())
                .snapImg(orderChecker.getLeaderImg())
                .snapTitle(orderChecker.getLeaderTitle())
                .status(OrderStatus.UNPAID.value())
                .placedTime(placedOrderTime)
                .expiredTime(expiredTime)
                .build();

        order.setSnapItems(orderChecker.getOrderSkuList());
        order.setSnapAddress(orderDTO.getAddress());

        this.orderRepository.save(order);
        //减库存
        this.reduceStock(orderChecker);
        //核销优惠券
        Long couponId = -1L;
        if (orderDTO.getCouponId() != null) {
            this.writeOffCoupon(orderDTO.getCouponId(), order.getId(), uid);
            couponId = orderDTO.getCouponId();
        }
        //加入延迟消息队列
        this.sentToRedis(uid, order.getId(), couponId);

        return order.getId();
    }

    public void sentToRedis(Long uid, Long oid, Long couponId) {
        String key = uid + "," + oid + "," + couponId;
        try {
            this.stringRedisTemplate.opsForValue().set(key, "1", this.payTimeLimit, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeOffCoupon(Long couponId, Long oid, Long uid) {
        int result = this.userCouponRepozitory.writeOffCoupon(couponId, oid, uid);
        if (result != 1) {
            throw new ForbiddenException(40012);
        }
    }

    private void reduceStock(OrderChecker orderChecker) {
        List<OrderSku> orderSkuList = orderChecker.getOrderSkuList();
        orderSkuList.forEach(orderSku -> {
            int result = this.skuRepository.reduceStock(orderSku.getId(), orderSku.getCount().longValue());
            if (result != 1) {
                throw new ParameterException(50006);
            }
        });
    }

    public OrderChecker isOK(Long uid, OrderDTO orderDTO) {

        List<Long> skuIds = orderDTO.getSkuInfoList().stream()
                .map(SkuInfoDTO::getId)
                .collect(Collectors.toList());

        List<Sku> skuList = skuService.getSkuListByIdsOrderByIdAsc(skuIds)
                .orElse(null);

        //校验优惠券
        Long couponId = orderDTO.getCouponId();
        CouponChecker couponChecker = null;
        if (couponId != null) {
            Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow(() -> new NotFoundException(40002));
            /*UserCoupon userCoupon = userCouponRepozitory.findFirstByUserIdAndCouponId(uid, couponId)
                    .orElseThrow(() -> new NotFoundException(50001));*/
            couponChecker = new CouponChecker(coupon, iMoneyDiscount);
        }
        OrderChecker orderChecker = new OrderChecker(orderDTO, skuList, couponChecker, this.maxSkuLimit);
        orderChecker.isOK();
        return orderChecker;
    }
}
