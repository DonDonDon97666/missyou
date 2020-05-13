package com.lin.missyou.logic;

import com.lin.missyou.bo.SkuOrderBO;
import com.lin.missyou.core.enumeration.CouponType;
import com.lin.missyou.core.money.IMoneyDiscount;
import com.lin.missyou.exception.http.ForbiddenException;
import com.lin.missyou.exception.http.ParameterException;
import com.lin.missyou.model.Category;
import com.lin.missyou.model.Coupon;
import com.lin.missyou.util.DateUtils;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/30
 */
@Setter
@Getter
public class CouponChecker {
    private Coupon coupon;
    private IMoneyDiscount iMoneyDiscount;

    public CouponChecker(Coupon coupon, IMoneyDiscount iMoneyDiscount) {
        this.coupon = coupon;
        this.iMoneyDiscount = iMoneyDiscount;
    }

    /*public void isOK() {
        isExpiredTime();
    }*/

    public void isExpiredTime() {
        LocalDateTime now = LocalDateTime.now();
        boolean inTimeLine = DateUtils.isInTimeLine(now, this.coupon.getStartTime(), this.coupon.getEndTime());
        if (!inTimeLine) {
            throw new ForbiddenException(50002);
        }
    }

    public void finalTotalPriceIsOk(BigDecimal orderFinalTotalPrice,
                                    BigDecimal serverTotalPrice) {
        BigDecimal serverFinalTotalPrice = null;
        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_MINUS:
            case NO_THRESHOLD_MINUS:
                serverFinalTotalPrice = serverTotalPrice.subtract(this.coupon.getMinus());
                if (serverFinalTotalPrice.compareTo(new BigDecimal("0")) <= 0) {
                    throw new ForbiddenException(50008);
                }
                break;
            case FULL_OFF:
                serverFinalTotalPrice = this.iMoneyDiscount.discoumt(serverTotalPrice, this.coupon.getRate());
                break;
            default:
                throw new ForbiddenException(50008);
        }
        int compare = serverFinalTotalPrice.compareTo(orderFinalTotalPrice);
        if (compare != 0) {
            throw new ForbiddenException(50003);
        }
    }

    /**
     * 计算优惠券是否符合分类条件
     *
     * @param skuOrderBOList
     * @param serverTotalPrice
     */
    public void canBeUsed(List<SkuOrderBO> skuOrderBOList, BigDecimal serverTotalPrice) {
        BigDecimal orderCategoryPrice;
        if (this.coupon.getWholeStore()) {
            orderCategoryPrice = serverTotalPrice;
        } else {
            List<Long> categoryIdList = this.coupon.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toList());
            orderCategoryPrice = this.getSumPriceByCategoryList(skuOrderBOList, categoryIdList);
        }
        this.couponCanBeUsed(orderCategoryPrice);
    }

    private void couponCanBeUsed(BigDecimal orderCategoryPrice) {
        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_OFF:
            case FULL_MINUS:
                int compare = this.coupon.getFullMoney().compareTo(orderCategoryPrice);
                if (compare > 0) {
                    throw new ParameterException(40008);
                }
                break;
            case NO_THRESHOLD_MINUS:
                break;
            default:
                throw new ParameterException(40009);
        }
    }

    private BigDecimal getSumPriceByCategoryList(List<SkuOrderBO> skuOrderBOList, List<Long> cidList) {
        return cidList.stream()
                .map(cid -> this.getSumPriceByCategory(skuOrderBOList, cid))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
    }

    private BigDecimal getSumPriceByCategory(List<SkuOrderBO> skuOrderBOList, Long cid) {
        return skuOrderBOList.stream()
                .filter(skuOrderBO -> skuOrderBO.getCategoryId().equals(cid))
                .map(SkuOrderBO::getTotalPrice)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0"));
    }
}
