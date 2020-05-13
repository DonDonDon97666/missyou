package com.lin.missyou.core.enumeration;

import java.util.stream.Stream;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/28
 */
public enum CouponStatus {

    AVAILABLE(1, "可以使用,未过期"),
    USED(2, "已使用"),
    EXPIRED(3, "未使用，已过期");

    private Integer stauts;

    public Integer getStauts() {
        return stauts;
    }

    CouponStatus(Integer status, String description) {
        this.stauts = status;
    }

    public static CouponStatus toType(Integer value) {
        return Stream.of(CouponStatus.values())
                .filter(c -> c.getStauts() == value)
                .findAny()
                .orElse(null);
    }
}
