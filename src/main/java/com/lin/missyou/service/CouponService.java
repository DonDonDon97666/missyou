package com.lin.missyou.service;

import com.lin.missyou.core.enumeration.CouponStatus;
import com.lin.missyou.exception.http.NotFoundException;
import com.lin.missyou.exception.http.ParameterException;
import com.lin.missyou.model.Activity;
import com.lin.missyou.model.Coupon;
import com.lin.missyou.model.UserCoupon;
import com.lin.missyou.repository.ActivityRepository;
import com.lin.missyou.repository.CouponRepository;
import com.lin.missyou.repository.UserCouponRepozitory;
import com.lin.missyou.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/27
 */
@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserCouponRepozitory userCouponRepozitory;

    public Optional<List<Coupon>> getCouponByCategory(Long cid) {
        Date now = new Date();
        return couponRepository.findByCategoriy(cid, now);
    }

    public Optional<List<Coupon>> getWholeStoreCoupons(Boolean isWholeStore) {
        Date now = new Date();
        return couponRepository.findWholeStoreCoupons(isWholeStore, now);
    }

    public void collectCoupon(Long uid, Long couponId) {
        couponRepository.findById(couponId)
                .orElseThrow(() -> new NotFoundException(40002));

        Activity activity = activityRepository.findActivityByCouponsId(couponId)
                .orElseThrow(() -> new NotFoundException(40003));

        LocalDateTime now = LocalDateTime.now();
        boolean inTimeLine = DateUtils.isInTimeLine(now, activity.getStartTime(), activity.getEndTime());
        if (!inTimeLine) {
            throw new NotFoundException(40004);
        }

        userCouponRepozitory.findFirstByUserIdAndCouponId(uid, couponId)
                .ifPresent((t) -> {
                    throw new ParameterException(40005);
                });

        UserCoupon userCoupon = UserCoupon.builder()
                .userId(uid)
                .couponId(couponId)
                .status(CouponStatus.AVAILABLE.getStauts())
                .createTime(now)
                .build();

        userCouponRepozitory.save(userCoupon);
    }

    public Optional<List<Coupon>> getMyAvailableCoupon(Long uid) {
        LocalDateTime now = LocalDateTime.now();
        return couponRepository.findMyAvailableCoupons(uid, now);
    }

    public Optional<List<Coupon>> getMyUsedCoupon(Long uid) {
        LocalDateTime now = LocalDateTime.now();
        return couponRepository.findMyUsedCoupons(uid, now);
    }

    public Optional<List<Coupon>> getMyExpiredCoupon(Long uid) {
        LocalDateTime now = LocalDateTime.now();
        return couponRepository.findMyExpiredCoupons(uid, now);
    }

}
