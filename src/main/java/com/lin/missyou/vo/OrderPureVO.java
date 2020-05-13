package com.lin.missyou.vo;

import com.lin.missyou.model.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/5/6
 */
@Getter
@Setter
public class OrderPureVO extends Order {
    private Long period;

    public OrderPureVO(Order order, Long period) {
        BeanUtils.copyProperties(order, this);
        this.period = period;
    }
}
