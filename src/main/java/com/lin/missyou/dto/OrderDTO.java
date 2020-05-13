package com.lin.missyou.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/29
 */
@Setter
@Getter
public class OrderDTO {
    @DecimalMin(value = "0.00", message = "不在合法范围内")
    @DecimalMax(value = "9999999999.99", message = "不在合法范围内")
    private BigDecimal totalPrice;

    @DecimalMin(value = "0.00", message = "不在合法范围内")
    @DecimalMax(value = "9999999999.99", message = "不在合法范围内")
    private BigDecimal finalTotalPrice;
    private Long couponId;
    private List<SkuInfoDTO> skuInfoList;
    private OrderAddressDTO address;
}
