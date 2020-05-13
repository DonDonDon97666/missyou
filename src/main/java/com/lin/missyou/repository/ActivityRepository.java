package com.lin.missyou.repository;

import com.lin.missyou.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author DELL
 * @create 2020/4/14
 */
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> getActivityByName(String name);

    Optional<Activity> findActivityByCouponsId(Long couponId);
}
