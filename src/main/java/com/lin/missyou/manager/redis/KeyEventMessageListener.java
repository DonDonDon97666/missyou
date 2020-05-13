package com.lin.missyou.manager.redis;

import com.lin.missyou.bo.OrderMessageBO;
import com.lin.missyou.service.CouponCancleService;
import com.lin.missyou.service.OrderCancleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/5/10
 */
public class KeyEventMessageListener implements MessageListener {

    @Autowired
    private OrderCancleService orderCancleService;

    @Autowired
    private CouponCancleService couponCancleService;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();

        String expiredKey = new String(body);
        String topic = new String(channel);

        OrderMessageBO orderMessageBO = new OrderMessageBO(expiredKey);
        orderCancleService.cancle(orderMessageBO);
        couponCancleService.returnBack(orderMessageBO);
    }
}
