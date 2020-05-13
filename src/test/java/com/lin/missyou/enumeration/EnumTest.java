package com.lin.missyou.enumeration;

import com.lin.missyou.core.enumeration.LoginType;
import org.junit.Test;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/23
 */
public class EnumTest {

    @Test
    public void test(){
        LoginType userWx = LoginType.USER_WX;
        System.out.println(userWx.name() + " - " + userWx.ordinal());
        System.out.println(userWx.getValue() + " - " + userWx.getDescription());
    }
}
