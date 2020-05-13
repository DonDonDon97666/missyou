package com.lin.missyou.logic;

import com.lin.missyou.bo.SkuOrderBO;
import com.lin.missyou.dto.OrderDTO;
import com.lin.missyou.dto.SkuInfoDTO;
import com.lin.missyou.exception.http.ParameterException;
import com.lin.missyou.model.OrderSku;
import com.lin.missyou.model.Sku;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈校验订单，并返回订单model的各个数据项〉
 *
 * @author ${吴延昭}
 * @create 2020/4/30
 */
@Getter
@Setter
public class OrderChecker {
    private OrderDTO orderDTO;
    private List<Sku> serverSkuList;
    private CouponChecker couponChecker;
    private Integer maxSkuLimit;

    @Getter
    private List<OrderSku> orderSkuList = new ArrayList<>();

    public OrderChecker(OrderDTO orderDTO, List<Sku> serverSkuList, CouponChecker couponChecker, Integer maxSkuLimit) {
        this.orderDTO = orderDTO;
        this.serverSkuList = serverSkuList;
        this.couponChecker = couponChecker;
        this.maxSkuLimit = maxSkuLimit;
    }

    public String getLeaderImg() {
        return  this.serverSkuList.get(0).getImg();
    }

    public String getLeaderTitle() {
        return this.serverSkuList.get(0).getTitle();
    }

    public Integer getTotalCount() {
        return this.orderDTO.getSkuInfoList()
                .stream()
                .map(SkuInfoDTO::getCount)
                .reduce(Integer::sum)
                .orElse(0);
    }

    /**
     * 校验项
     * 1、orderTotalPrice   serverTotalPrice
     * 2、下架
     * 3、售罄
     * 4、超出库存
     * 5、最大购买数量
     * 6、优惠券
     * <p>
     * 其中，2、3、4项只是预检测，因为可能在订单入库的一瞬间，存在商品下架、售罄、超出库存情况。
     */
    public void isOK() {

        BigDecimal serverTotalPrice = new BigDecimal("0");
        List<SkuOrderBO> skuOrderBOList = new ArrayList<>();

        this.skuNotOnSale(this.serverSkuList.size(), this.orderDTO.getSkuInfoList().size());

        for (int i = 0; i < this.serverSkuList.size(); i++) {
            Sku sku = this.serverSkuList.get(i);
            SkuInfoDTO skuInfoDTO = this.orderDTO.getSkuInfoList().stream()
                    .filter(skuInfoDTO1 -> skuInfoDTO1.getId().equals(sku.getId()))
                    .findFirst()
                    .orElseThrow(() -> new ParameterException(9999));

            this.containsSoldOutSku(sku);
            this.beyondSkuStock(sku, skuInfoDTO);
            this.beyondMaxSkuLimit(skuInfoDTO);

            serverTotalPrice = serverTotalPrice.add(this.calculateSkuOrderPrice(sku, skuInfoDTO));
            skuOrderBOList.add(new SkuOrderBO(sku, skuInfoDTO));
            this.orderSkuList.add(new OrderSku(sku, skuInfoDTO));
        }

        this.totalPriceIsOK(orderDTO.getTotalPrice(), serverTotalPrice);

        if (couponChecker != null) {
            this.couponChecker.isExpiredTime();
            this.couponChecker.canBeUsed(skuOrderBOList, serverTotalPrice);
            this.couponChecker.finalTotalPriceIsOk(orderDTO.getFinalTotalPrice(), serverTotalPrice);
        }
    }

    private void totalPriceIsOK(BigDecimal totalPrice, BigDecimal serverTotalPrice) {
        if (totalPrice.compareTo(serverTotalPrice) != 0) {
            throw new ParameterException(50010);
        }
    }

    private BigDecimal calculateSkuOrderPrice(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() <= 0) {
            throw new ParameterException(50009);
        }
        return sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount()));
    }

    //最大数量
    private void beyondMaxSkuLimit(SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() > this.maxSkuLimit) {
            throw new ParameterException(50007);
        }
    }

    //超出库存
    private void beyondSkuStock(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (sku.getStock() < skuInfoDTO.getCount()) {
            throw new ParameterException(50006);
        }
    }

    //售罄商品
    private void containsSoldOutSku(Sku sku) {
        if (sku.getStock() == 0) {
            throw new ParameterException(50005);
        }
    }

    //下架商品
    private void skuNotOnSale(int count1, int count2) {
        if (count1 != count2) {
            throw new ParameterException(50004);
        }
    }
}
