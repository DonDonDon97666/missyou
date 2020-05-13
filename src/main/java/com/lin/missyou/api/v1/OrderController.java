package com.lin.missyou.api.v1;

import com.lin.missyou.bo.PageCounter;
import com.lin.missyou.core.LocalUser;
import com.lin.missyou.core.annotations.ScopeLevel;
import com.lin.missyou.dto.OrderDTO;
import com.lin.missyou.exception.http.NotFoundException;
import com.lin.missyou.logic.OrderChecker;
import com.lin.missyou.model.Order;
import com.lin.missyou.service.OrderService;
import com.lin.missyou.util.CommonUtil;
import com.lin.missyou.vo.OrderIdVO;
import com.lin.missyou.vo.OrderPureVO;
import com.lin.missyou.vo.OrderSimplifyVO;
import com.lin.missyou.vo.PagingDozer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/5/6
 */
@RestController
@RequestMapping("/order")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Value("${missyou.order.pay-time-limit}")
    private Long payTimeLimit;

    @PostMapping("")
    @ScopeLevel()
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO) {
        Long uid = LocalUser.getUser().getId();
        OrderChecker orderChecker = orderService.isOK(uid, orderDTO);
        Long oid = orderService.placeOrder(uid, orderDTO, orderChecker);
        return new OrderIdVO(oid);
    }

    @ScopeLevel
    @GetMapping("/status/unpaid")
    @SuppressWarnings("all")
    public PagingDozer<Order, OrderSimplifyVO> getUnpaid(
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "10") Integer count) {
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Order> unpaid = orderService.getUnpaid(pageCounter.getPage(), pageCounter.getCount());
        PagingDozer<Order, OrderSimplifyVO> pagingDozer = new PagingDozer<>(unpaid, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach(item -> {
            ((OrderSimplifyVO) item).setPeriod(this.payTimeLimit);
        });
        return pagingDozer;
    }

    @ScopeLevel
    @GetMapping("/by/status/{status}")
    @SuppressWarnings("all")
    public PagingDozer<Order, OrderSimplifyVO> getByStatus(
            @PathVariable Integer status,
            @RequestParam(name = "start", defaultValue = "0") Integer start,
            @RequestParam(name = "count", defaultValue = "10") Integer count) {

        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orders = this.orderService.getByStatus(pageCounter.getPage(), pageCounter.getCount(), status);
        PagingDozer<Order, OrderSimplifyVO> pagingDozer = new PagingDozer<>(orders, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach(item -> {
            ((OrderSimplifyVO) item).setPeriod(this.payTimeLimit);
        });
        return pagingDozer;
    }

    @RequestMapping("/detail/{id}")
    @ScopeLevel
    public OrderPureVO getOrderDetail(@PathVariable Long id) {
        Optional<Order> order = this.orderService.getDetailById(id);
        return order.map(o -> new OrderPureVO(o, this.payTimeLimit))
                .orElseThrow(() -> new NotFoundException(50011));
    }
}
