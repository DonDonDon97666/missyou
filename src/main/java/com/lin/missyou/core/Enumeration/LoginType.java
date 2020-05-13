package com.lin.missyou.core.enumeration;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author DELL
 * @create 2020/4/23
 */
public enum LoginType {
    USER_WX(0, "微信登录"),
    USER_EMAIL(1, "邮箱登录");

    private Integer value;
    private String description;

    LoginType(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }}
