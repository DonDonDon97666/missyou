package com.lin.missyou.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/5/6
 */
@Getter
@Setter
public class OrderSimplifyVO {
    private Long id;
    private String orderNo;
    private BigDecimal totalPrice;
    private Long totalCount;
    private String snapImg;
    private String snapTitle;
    private BigDecimal finalTotalPrice;
    private Integer status;
    private LocalDateTime expiredTime;
    private LocalDateTime placedTime;
    private Long period;
}
