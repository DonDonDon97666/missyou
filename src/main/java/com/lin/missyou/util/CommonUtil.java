package com.lin.missyou.util;

import com.lin.missyou.bo.PageCounter;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/16
 */
public class CommonUtil {

    public static PageCounter convertToPageParameter(Integer start, Integer count) {
        Integer page = start / count;
        PageCounter pageCounter = PageCounter.builder()
                .page(page)
                .count(count)
                .build();
        return pageCounter;
    }
}
