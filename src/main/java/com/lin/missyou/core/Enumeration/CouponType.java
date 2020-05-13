package com.lin.missyou.core.enumeration;

import java.util.stream.Stream;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/30
 */
public enum CouponType {
    FULL_MINUS(1, "满减券"),
    FULL_OFF(2, "满减折扣券"),
    NO_THRESHOLD_MINUS(3, "无门槛减除券");

    private Integer value;

    CouponType(Integer value, String description) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static CouponType toType(Integer value) {
        return Stream.of(CouponType.values())
                .filter(c -> c.getValue() == value)
                .findAny()
                .orElse(null);
    }
}
