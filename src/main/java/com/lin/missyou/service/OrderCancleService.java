package com.lin.missyou.service;

import com.lin.missyou.bo.OrderMessageBO;
import com.lin.missyou.exception.http.ServerErrorException;
import com.lin.missyou.model.Order;
import com.lin.missyou.repository.OrderRepository;
import com.lin.missyou.repository.SkuRepository;
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
public class OrderCancleService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SkuRepository skuRepository;

    public void cancle(OrderMessageBO orderMessageBO) {
        if (orderMessageBO.getOrderId() <= 0) {
            throw new ServerErrorException(9999);
        }

        Order order = orderRepository.findById(orderMessageBO.getOrderId())
                .orElseThrow(() -> new ServerErrorException(9999));

        int res = orderRepository.calcleOrder(order.getId());
        if (res != 1) {
            return;
        }

        order.getSnapItems().forEach(s -> {
            this.skuRepository.recoverStock(s.getId(), s.getCount());
        });
    }
}
