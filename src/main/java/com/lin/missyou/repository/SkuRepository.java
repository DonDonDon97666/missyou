package com.lin.missyou.repository;

import com.lin.missyou.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author DELL
 * @create 2020/4/14
 */
public interface SkuRepository extends JpaRepository<Sku, Long> {
    Optional<List<Sku>> findAllByIdInOrderByIdAsc(List<Long> ids);

    @Modifying
    @Query("update Sku s \n" +
            "set s.stock = s.stock - :quantity\n" +
            "where s.id = :sid\n" +
            "and s.stock >= :quantity")
    int reduceStock(Long sid, Long quantity);

    @Modifying
    @Query("update Sku s \n" +
            "set s.stock = s.stock+:quantity\n" +
            "where s.id=:id")
    int recoverStock(Long id, Integer quantity);
}
