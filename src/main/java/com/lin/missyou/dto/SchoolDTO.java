package com.lin.missyou.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/11
 */
@Getter
@Setter
public class SchoolDTO {

    @Length(min = 2)
    private String schoolName;
}
