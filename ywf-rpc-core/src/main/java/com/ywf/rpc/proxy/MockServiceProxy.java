package com.ywf.rpc.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class MockServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        log.info("mock invok{}", method.getName());
        return getReturnValue(returnType);
    }

    private Object getReturnValue(Class<?> returnType) {
        if (returnType.isPrimitive()) {
            switch(returnType.getName()){
                case "boolean":
                    return false;
                case "short":
                    return (short) 0;
                case "int":
                    return 0;
                case "long":
                    return 0L;
                default:
                    return null;  // 可以
            }
        }
        return null;
    }
}
