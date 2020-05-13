package com.lin.missyou.model;

import com.lin.missyou.dto.SkuInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/5/6
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderSku {
    private Long id;
    private Long spuId;
    private Integer count;
    private BigDecimal singlePrice;
    private BigDecimal finalPrice;
    private String img;
    private String title;
    private List<String> specValues;

    public OrderSku(Sku sku, SkuInfoDTO skuInfoDTO){
        this.id = sku.getId();
        this.spuId = sku.getSpuId();
        this.count = skuInfoDTO.getCount();
        this.singlePrice = sku.getActualPrice();
        this.finalPrice = sku.getActualPrice().multiply(new BigDecimal(this.count));
        this.img = sku.getImg();
        this.title = sku.getTitle();
        this.specValues = sku.getSpecValueList();
    }
}
