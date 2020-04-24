package com.lin.missyou.MessageFormat;

import java.text.MessageFormat;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/24
 */
public class Test {

    @org.junit.Test
    public void test1(){
        String s1 = "wx20b5dff2577572a7";
        String s2 = "0ecee4a4bd093b3cca28722652eb1bdb";
        String s3 = "https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization";
        String s4 = "123";
        String format = MessageFormat.format(s3, s1, s2, s4);
        System.out.println(format);
    }
}
