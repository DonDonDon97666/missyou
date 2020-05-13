package com.lin.missyou.util;

import com.lin.missyou.bo.PageCounter;

import java.math.BigDecimal;
import java.util.Calendar;

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

    public static String timestamp10(){
        Long timestamp13 = Calendar.getInstance().getTimeInMillis();
        String timestamp13Str = timestamp13.toString();
        return timestamp13Str.substring(0, timestamp13Str.length() - 3);
    }

    public static String yuanToFenPlainString(BigDecimal p){
        p = p.multiply(new BigDecimal("100"));
        return CommonUtil.toPlain(p);
    }

    public static String toPlain(BigDecimal p){
        return p.stripTrailingZeros().toPlainString();
    }
}
