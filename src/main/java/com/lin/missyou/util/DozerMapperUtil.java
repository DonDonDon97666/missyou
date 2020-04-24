package com.lin.missyou.util;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/22
 */
public class DozerMapperUtil {

    private static final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <T, K> K mapToObject(T source, Class<K> classK) {
        K k = (K) mapper.map(source, classK);
        return k;
    }

    public static <T, K> List<K> mapToList(List<T> source, Class<K> classK) {
        List<K> resultList = new ArrayList<>();
        source.forEach(s -> {
            K k = mapper.map(s, classK);
            resultList.add(k);
        });
        return resultList;
    }
}
