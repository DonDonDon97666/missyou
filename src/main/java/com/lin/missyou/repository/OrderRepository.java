package com.lin.missyou.repository;

import com.lin.missyou.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author DELL
 * @create 2020/4/14
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByExpiredTimeGreaterThanAndStatusAndUserId(LocalDateTime now, Integer status, Long uid, Pageable pageable);

    Page<Order> findAllByStatusAndUserId(Integer status, Long uid, Pageable pageable);

    Page<Order> findAllByUserId(Long uid, Pageable pageable);

    Optional<Order> findById(Long id);

    Optional<Order> findByIdAndUserId(Long id, Long uid);

    Optional<Order> findByOrderNo(String orderNum);

    @Modifying
    @Query("update Order o set o.status=:status where o.orderNo=:orderNo")
    int updateStatusByOrderNo(String orderNo, Integer status);

    @Modifying
    @Query("update Order o\n" +
            "set o.status=5\n" +
            "where o.id=:oid\n" +
            "and o.status=1")
    int calcleOrder(Long oid);
}
