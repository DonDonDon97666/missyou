package com.lin.missyou.core.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 〈一句话功能简述〉<br>
 * 〈银行家算法〉
 *
 * @author ${吴延昭}
 * @create 2020/4/30
 */
public class HalfEvenRound implements IMoneyDiscount{
    @Override
    public BigDecimal discoumt(BigDecimal originalMoney, BigDecimal discount) {
        BigDecimal actualMoney = originalMoney.multiply(discount);
        BigDecimal finalMoney = actualMoney.setScale(2, RoundingMode.HALF_EVEN);
        return finalMoney;
    }
}
