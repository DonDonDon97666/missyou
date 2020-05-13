package com.lin.missyou.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/26
 */
@Entity
@Setter
@Getter
@Where(clause = "delete_time is null")
public class Coupon extends BaseEntity{

    @Id
    private Long id;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private Integer type;
    private Long activityId;
    private String remark;
    private Boolean wholeStore;


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "coupons")
    private List<Category> categories;
}
