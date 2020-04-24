package com.lin.missyou.api.v1;

import com.lin.missyou.model.Category;
import com.lin.missyou.model.GridCategory;
import com.lin.missyou.service.CategoryService;
import com.lin.missyou.service.GridCategoryService;
import com.lin.missyou.vo.CategoryAllVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/18
 */
@RequestMapping("/category")
@RestController
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GridCategoryService gridCategoryService;

    @GetMapping("/all")
    public CategoryAllVO getAllCategories(){
        Map<Integer, List<Category>> categories = categoryService.getAll();
        return new CategoryAllVO(categories);
    }

    @GetMapping("/grid/all")
    public List<GridCategory> getAllGridCategories(){
        return gridCategoryService.getAll();
    }
}
