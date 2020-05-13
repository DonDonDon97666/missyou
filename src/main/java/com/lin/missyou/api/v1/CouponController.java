package com.lin.missyou.api.v1;

import com.lin.missyou.core.LocalUser;
import com.lin.missyou.core.UnifyResponse;
import com.lin.missyou.core.annotations.ScopeLevel;
import com.lin.missyou.core.enumeration.CouponStatus;
import com.lin.missyou.exception.http.ParameterException;
import com.lin.missyou.model.Coupon;
import com.lin.missyou.service.CouponService;
import com.lin.missyou.vo.CouponCategoryVO;
import com.lin.missyou.vo.CouponPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/27
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/by/category_id/{cid}")
    public List<CouponPureVO> getCouponByCategory(@PathVariable Long cid) {
        Optional<List<Coupon>> couponList = couponService.getCouponByCategory(cid);
        if (!couponList.isPresent()) {
            return Collections.emptyList();
        }
        List<CouponPureVO> voList = CouponPureVO.getList(couponList.get());
        return voList;
    }

    @GetMapping("/whole_store")
    public List<CouponPureVO> getWholeStoreCoupons() {
        Optional<List<Coupon>> wholeStoreCoupons = couponService.getWholeStoreCoupons(true);
        if (!wholeStoreCoupons.isPresent()) {
            return Collections.emptyList();
        }
        List<CouponPureVO> pureVOS = CouponPureVO.getList(wholeStoreCoupons.get());
        return pureVOS;
    }

    @PostMapping("/collect/{couponId}")
    @ScopeLevel()
    public void collectCoupon(@PathVariable Long couponId) {
        Long uid = LocalUser.getUser().getId();
        couponService.collectCoupon(uid, couponId);
        UnifyResponse.createSuccess();
    }

    @GetMapping("/myself/by/status/{status}")
    @ScopeLevel
    public List<CouponPureVO> getCouponByStatus(@PathVariable Integer status) {
        Long uid = LocalUser.getUser().getId();
        Optional<List<Coupon>> list;

        switch (CouponStatus.toType(status)) {
            case AVAILABLE:
                list = couponService.getMyAvailableCoupon(uid);
                break;
            case USED:
                list = couponService.getMyUsedCoupon(uid);
                break;
            case EXPIRED:
                list = couponService.getMyExpiredCoupon(uid);
                break;
            default:
                throw new ParameterException(40006);
        }

        if (!list.isPresent()) {
            return Collections.emptyList();
        }
        return CouponPureVO.getList(list.get());
    }

    @GetMapping("/myself/available/with_category")
    @ScopeLevel()
    public List<CouponCategoryVO> getCouponWithCategory() {
        Long uid = LocalUser.getUser().getId();
        Optional<List<Coupon>> availableCoupon = couponService.getMyAvailableCoupon(uid);
        if (!availableCoupon.isPresent()) {
            return Collections.emptyList();
        }
        return availableCoupon.get().stream()
                .map(CouponCategoryVO::new)
                .collect(Collectors.toList());
    }
}
