package com.lin.missyou.proxy.stat;

import com.lin.missyou.proxy.AFactory;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/5/1
 */
public class Test {

    @org.junit.Test
    public void test(){
        ProxyS proxyS = new ProxyS();
        proxyS.setaFactory(new AFactory());
        proxyS.create();
    }
}
