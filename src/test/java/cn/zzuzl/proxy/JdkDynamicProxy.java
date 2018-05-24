package cn.zzuzl.proxy;

import cn.zzuzl.QueryService;
import cn.zzuzl.QueryServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDynamicProxy implements InvocationHandler {
    private Object target = null;

    public static void main(String[] args) {
        QueryService queryService = new QueryServiceImpl();
        JdkDynamicProxy proxy = new JdkDynamicProxy();
        QueryService queryProxy = (QueryService) proxy.bind(queryService);
        queryProxy.query();
    }

    public Object bind(Object target) {
        this.target = target;

        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("start...");
        Object result = method.invoke(target, args);
        System.out.println("end...");
        return result;
    }
}
