package com.lin.missyou.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/5/1
 */
public class ProxyS<T> implements InvocationHandler {

    private T obj;

    public void setObj(T obj) {
        this.obj = obj;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        doSomeThingBefore();
        T invoke = (T) method.invoke(obj, args);
        doSomeThingAfter();
        return invoke;
    }

    @SuppressWarnings("unchecked")
    public T getObj(){
        return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),this);
    }

    private void doSomeThingBefore(){
        System.out.println("方法前增强");
    }

    private void doSomeThingAfter(){
        System.out.println("方法后增强");
    }
}
