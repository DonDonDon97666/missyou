package com.lin.missyou.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Getter
@Setter
@Where(clause = "delete_time is null and online = 1")
public class Activity extends BaseEntity{
    @Id
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String remark;
    private Boolean online;
    private String entranceImg;
    private String internalTopImg;
    private String name;

    @OneToMany
    @JoinColumn(name = "activityId")
    private List<Coupon> coupons;
}
