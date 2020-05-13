package com.lin.missyou.core.enumeration;

import java.util.stream.Stream;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/5/6
 */
public enum OrderStatus {
    All(0, "全部"),
    UNPAID(1, "待支付"),
    PAID(2, "已支付"),
    DELIVERED(3, "已发货"),
    FINISHED(4, "已完成"),
    CANCELED(5, "已取消");

    private Integer value;

    OrderStatus(Integer value, String text) {
        this.value = value;
    }

    public Integer value() {
        return this.value;
    }

    public static OrderStatus toType(Integer value) {
        return Stream.of(OrderStatus.values())
                .filter(c -> c.value.equals(value))
                .findAny()
                .orElse(null);
    }
}
