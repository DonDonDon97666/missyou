package com.lin.missyou.repository;

import com.lin.missyou.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author DELL
 * @create 2020/4/18
 */
public interface UserCouponRepozitory extends JpaRepository<UserCoupon, Long> {
    Optional<UserCoupon> findFirstByUserIdAndCouponId(Long uid, Long couponId);

    @Modifying
    @Query("update UserCoupon uc\n" +
            "set uc.status = 2, uc.orderId = :oid\n" +
            "where uc.userId = :uid\n" +
            "and uc.couponId = :couponId\n" +
            "and uc.status = 1 \n" +
            "and uc.orderId is null")
    int writeOffCoupon(Long couponId, Long oid, Long uid);

    @Modifying
    @Query("update UserCoupon uc\n" +
            "set uc.status=1, uc.orderId=null \n" +
            "where uc.userId=:uid\n" +
            "and uc.couponId=:cid\n" +
            "and uc.orderId is not null\n" +
            "and uc.status = 2")
    int returnBack(Long uid, Long cid);
}
