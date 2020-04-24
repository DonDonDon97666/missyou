package com.lin.missyou.api.v1;

import com.lin.missyou.exception.http.NotFoundException;
import com.lin.missyou.model.Theme;
import com.lin.missyou.service.ThemeService;
import com.lin.missyou.util.DozerMapperUtil;
import com.lin.missyou.vo.ThemePureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/22
 */
@RestController
@Validated
@RequestMapping("/theme")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @GetMapping("/by/names")
    public List<ThemePureVO> getByNames(@RequestParam(name = "names") @NotBlank String names) {
        List<String> nameList = Arrays.asList(names.split(","));
        List<Theme> themes = themeService.getByNames(nameList);
        if (themes.isEmpty()) {
            throw new NotFoundException(30006);
        }
        return DozerMapperUtil.mapToList(themes, ThemePureVO.class);
    }

    @GetMapping("/name/{name}/with_spu")
    public Theme getByNameWithSpu(@PathVariable String name) {
        Optional<Theme> opTheme = themeService.getByNameWithSpu(name);
        return opTheme.orElseThrow(() -> new NotFoundException(30006));
    }
}
