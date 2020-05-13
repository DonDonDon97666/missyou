package com.lin.missyou.core.money;

import java.math.BigDecimal;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author DELL
 * @create 2020/4/30
 */
public interface IMoneyDiscount {
    BigDecimal discoumt(BigDecimal originalMoney, BigDecimal discount);
}
