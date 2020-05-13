package com.lin.missyou.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/5/10
 */
@Getter
@Setter
public class OrderMessageBO {
    private String message;
    private Long userId;
    private Long orderId;
    private Long couponId;

    public OrderMessageBO(String message) {
        this.message = message;
        this.parseId();
    }

    private void parseId() {
        String[] ids = this.message.split(",");
        this.userId = Long.parseLong(ids[0]);
        this.orderId = Long.parseLong(ids[1]);
        this.couponId = Long.parseLong(ids[2]);
    }
}
