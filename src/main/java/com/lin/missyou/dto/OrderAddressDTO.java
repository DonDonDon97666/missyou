package com.lin.missyou.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/29
 */
@Setter
@Getter
public class OrderAddressDTO {
    private String username;
    private String province;
    private String city;
    private String county;
    private String mobile;
    private String nationalCode;
    private String postalCode;
    private String detail;
}
