package com.lin.missyou.vo;

import com.lin.missyou.model.Activity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/26
 */
@Getter
@Setter
public class ActivityCouponVO extends ActivityPureVO{
    private List<CouponPureVO> coupons;

    public ActivityCouponVO(Activity activity){
        super(activity);
        coupons = activity.getCoupons().stream()
                .map(CouponPureVO::new)
                .collect(Collectors.toList());
    }
}
