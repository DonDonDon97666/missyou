package com.lin.missyou.repository;

import com.lin.missyou.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author DELL
 * @create 2020/4/14
 */
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select c from Coupon c\n" +
            "join c.categories ca\n" +
            "join Activity a on a.id = c.activityId\n" +
            "where a.id = :cid\n" +
            "and a.startTime < :now\n" +
            "and a.endTime > :now")
    Optional<List<Coupon>> findByCategoriy(Long cid, Date now);

    @Query("select c from Coupon c\n" +
            "join Activity a on a.id = c.activityId\n" +
            "where c.wholeStore =:isWholeStore\n" +
            "and a.startTime<:now\n" +
            "and a.endTime>:now")
    Optional<List<Coupon>> findWholeStoreCoupons(Boolean isWholeStore, Date now);

    @Query("select c from Coupon c\n" +
            "join UserCoupon uc on c.id = uc.couponId\n" +
            "join User u on u.id = uc.userId\n" +
            "where u.id = :uid\n" +
            "and uc.status = 1\n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now\n" +
            "and uc.orderId is null")
    Optional<List<Coupon>> findMyAvailableCoupons(Long uid, LocalDateTime now);

    @Query("select c from Coupon c\n" +
            "join UserCoupon uc on c.id = uc.couponId\n" +
            "join User u on u.id = uc.userId\n" +
            "where u.id = :uid\n" +
            "and uc.status = 2\n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now\n" +
            "and uc.orderId is not null")
    Optional<List<Coupon>> findMyUsedCoupons(Long uid, LocalDateTime now);

    @Query("select c from Coupon c\n" +
            "join UserCoupon uc on c.id = uc.couponId\n" +
            "join User u on u.id = uc.userId\n" +
            "where u.id = :uid\n" +
            "and uc.status <> 2\n" +
            "and c.endTime < :now\n" +
            "and uc.orderId is null")
    Optional<List<Coupon>> findMyExpiredCoupons(Long uid, LocalDateTime now);

}
