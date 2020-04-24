package com.lin.missyou.dto;

import com.lin.missyou.dto.validators.PasswordEqual;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/10
 */

@Builder
@Getter
@PasswordEqual
public class PersonDTO {
    @Length(min = 1, max = 10, message = "name长度不合适.")
    private String name;
    private Integer age;

    @Valid
    private SchoolDTO schoolDTO;

    private String password1;
    private String password2;

}
