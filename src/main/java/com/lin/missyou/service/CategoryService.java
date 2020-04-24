package com.lin.missyou.service;

import com.lin.missyou.model.Category;
import com.lin.missyou.repository.CategoryRepozitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/18
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepozitory categoryRepozitory;

    public Map<Integer, List<Category>> getAll(){
        List<Category> roots = this.categoryRepozitory.findAllByIsRootOrderByIndexAsc(true);
        List<Category> subs = this.categoryRepozitory.findAllByIsRootOrderByIndexAsc(false);
        Map<Integer, List<Category>> map = new HashMap<>();
        map.put(1, roots);
        map.put(2, subs);
        return map;
    }
}
