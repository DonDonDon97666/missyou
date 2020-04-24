package com.lin.missyou.vo;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/16
 */
public class PagingDozer<T, K> extends Paging {

    @SuppressWarnings("unchecked")
    public PagingDozer(Page<T> pageT, Class<K> classK){
        this.initParameters(pageT);

        List<T> tList = pageT.getContent();
        List<K> voList = new ArrayList<>();

        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        tList.forEach(t->{
            K vo = mapper.map(t, classK);
            voList.add(vo);
        });

        this.setItems(voList);
    }
}
