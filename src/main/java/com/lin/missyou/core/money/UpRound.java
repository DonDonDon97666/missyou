package com.lin.missyou.core.money;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 〈一句话功能简述〉<br>
 * 〈向上取整〉
 *
 * @author ${吴延昭}
 * @create 2020/4/30
 */
@Component
public class UpRound implements IMoneyDiscount {
    @Override
    public BigDecimal discoumt(BigDecimal originalMoney, BigDecimal discount) {
        BigDecimal actualMoney = originalMoney.multiply(discount);
        BigDecimal finalMoney = actualMoney.setScale(2, RoundingMode.UP);
        return finalMoney;
    }
}
