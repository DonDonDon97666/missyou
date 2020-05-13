package com.lin.missyou.proxy.stat;

import com.lin.missyou.proxy.AFactory;
import com.lin.missyou.proxy.ManToolsFactory;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/5/1
 */
public class ProxyS implements ManToolsFactory {

    private AFactory aFactory;

    public void setaFactory(AFactory aFactory) {
        this.aFactory = aFactory;
    }

    @Override
    public void create() {
        doSomeThingBefore();
        aFactory.create();
        doSomeThingAfter();
    }

    private void doSomeThingBefore(){
        System.out.println("方法前增强");
    }

    private void doSomeThingAfter(){
        System.out.println("方法后增强");
    }
}
