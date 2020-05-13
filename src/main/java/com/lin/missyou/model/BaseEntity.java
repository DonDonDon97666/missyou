package com.lin.missyou.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/15
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @JsonIgnore
    @Column(insertable = false, updatable = false)
    protected Date createTime;
    @JsonIgnore
    @Column(insertable = false, updatable = false)
    protected Date updateTime;
    @JsonIgnore
    protected Date deleteTime;
}
