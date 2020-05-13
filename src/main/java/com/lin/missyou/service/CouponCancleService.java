package com.lin.missyou.service;

import com.lin.missyou.bo.OrderMessageBO;
import com.lin.missyou.core.enumeration.OrderStatus;
import com.lin.missyou.exception.http.ServerErrorException;
import com.lin.missyou.model.Order;
import com.lin.missyou.repository.OrderRepository;
import com.lin.missyou.repository.UserCouponRepozitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/5/10
 */
@Service
public class CouponCancleService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserCouponRepozitory userCouponRepozitory;

    public void returnBack(OrderMessageBO orderMessageBO) {
        Long couponId = orderMessageBO.getCouponId();
        if (couponId.equals("-1")) {
            return;
        }

        Order order = orderRepository.findByIdAndUserId(orderMessageBO.getOrderId(), orderMessageBO.getUserId())
                .orElseThrow(() -> new ServerErrorException(9999));

        if (order.getStatus().equals(OrderStatus.UNPAID.value()) ||
                order.getStatus().equals(OrderStatus.CANCELED.value())) {
            userCouponRepozitory.returnBack(orderMessageBO.getUserId(), orderMessageBO.getCouponId());
        }
    }
}
