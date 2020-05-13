package com.lin.missyou.dto;

import com.lin.missyou.core.enumeration.LoginType;
import com.lin.missyou.dto.validators.TokenPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/23
 */
@Getter
@Setter
public class TokenGetDTO {

    @NotBlank(message = "account不许为空")
    private String account;

    @TokenPassword(max = 30, message = "{token.password}")
    private String password;

    private LoginType type;
}
