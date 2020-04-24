package com.lin.missyou.repository;

import com.lin.missyou.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author DELL
 * @create 2020/4/23
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByOpenid(String openId);
}
