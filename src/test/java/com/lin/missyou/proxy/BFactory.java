package com.lin.missyou.proxy;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/5/1
 */
public class BFactory implements ManToolsFactory {
    @Override
    public void create() {
        System.out.println("B公司的实现方法");
    }
}
