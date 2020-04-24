package com.lin.missyou.service;

import com.lin.missyou.model.Theme;
import com.lin.missyou.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/22
 */
@Service
public class ThemeService {
    @Autowired
    private ThemeRepository themeRepository;

    public List<Theme> getByNames(List<String> names){
        return themeRepository.findAllByNameIn(names);
    }

    public Optional<Theme> getByNameWithSpu(String name){
        return themeRepository.findByName(name);
    }
}
