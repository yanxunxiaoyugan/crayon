package com.liu.test.jdk8.dynamic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HelloProxy implements InvocationHandler {
    private Object proxied = null;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("enter proxy");
        return method.invoke(proxied,args);
    }

}
