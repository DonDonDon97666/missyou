package com.lin.missyou.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lin.missyou.util.GenericAndJson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/16
 */
@Entity
@Getter
@Setter
public class Sku extends BaseEntity{
    @Id
    private Long id;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean online;
    private String img;
    private String title;
    private Long spuId;
    //@Convert(converter = ListAndJson.class)
    private String specs;
    private String code;
    private Long stock;
    private Long categoryId;
    private Long rootCategoryId;

    public List<Spec> getSpecs() {
        List<Spec> list = GenericAndJson.jsonToObject(this.specs, new TypeReference<List<Spec>>() {});
        return list;
    }

    public void setSpecs(List<Spec> list) {
        String s = GenericAndJson.objectToJson(list);
        this.specs = s;
    }

    //    @Convert(converter = MapAndJson.class)
//    private Map<String, Object> test;
}
