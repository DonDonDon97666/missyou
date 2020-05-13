package com.lin.missyou.vo;

import com.lin.missyou.model.Coupon;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/29
 */
@Getter
@Setter
public class CouponCategoryVO extends CouponPureVO{
    private List<CategoryPureVO> categories;

    public CouponCategoryVO(Coupon coupon){
        super(coupon);
        categories = coupon.getCategories().stream()
                .map(CategoryPureVO::new)
                .collect(Collectors.toList());
    }
}
