package com.lin.missyou.proxy.dynamic;

import com.lin.missyou.proxy.BFactory;
import com.lin.missyou.proxy.ManToolsFactory;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/5/1
 */
public class Test {

    @org.junit.Test
    public void test1(){
        ProxyS<ManToolsFactory> proxyS = new ProxyS<>();
        proxyS.setObj(new BFactory());
        ManToolsFactory obj = proxyS.getObj();
        obj.create();
    }
}
