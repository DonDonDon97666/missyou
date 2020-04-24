package com.lin.missyou.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/16
 */
@Getter
@Setter
@NoArgsConstructor
public class Paging<T> {

    private Long total;
    private Integer count;
    private Integer page;
    private Integer totalPage;
    private List<T> items;

    @SuppressWarnings("all")
    public Paging(Page<T> pageT){
        this.initParameters(pageT);
        this.items = pageT.getContent();
    }

    protected void initParameters(Page<T> pageT){
        this.total = pageT.getTotalElements();
        this.count = pageT.getSize();
        this.page = pageT.getNumber();
        this.totalPage = pageT.getTotalPages();
    }
}
