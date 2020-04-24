package com.lin.missyou.repository;

import com.lin.missyou.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/22
 */
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    List<Theme> findAllByNameIn(List<String> names);

    Optional<Theme> findByName(String name);
}
