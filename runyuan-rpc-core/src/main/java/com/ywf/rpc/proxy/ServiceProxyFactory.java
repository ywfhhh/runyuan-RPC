package com.ywf.rpc.proxy;

import com.ywf.rpc.RpcApplication;

import java.lang.reflect.Proxy;

public class ServiceProxyFactory {
    public static <T> T getMockProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MockServiceProxy());
    }

    public static <T> T getProxy(Class<T> clazz) {
        if (RpcApplication.getRpcConfig().isMock()) return getMockProxy(clazz);

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                new ServiceProxy() {
                });
    }


}
