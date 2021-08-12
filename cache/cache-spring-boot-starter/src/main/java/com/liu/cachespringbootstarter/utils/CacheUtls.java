package com.liu.cachespringbootstarter.utils;

import com.liu.cachespringbootstarter.annotation.CacheAble;
import com.liu.cachespringbootstarter.annotation.Magic;
import com.liu.cachespringbootstarter.dto.CacheKeyDto;

import java.lang.reflect.Method;
import java.util.Collection;

public class CacheUtls {
    public static CacheKeyDto getCacheKey() {
        return null;
    }

    /**
     * 判断CacheAble注解是否是magic模式
     *
     * @param cacheAble
     * @param method
     * @return
     */
    public static boolean isMagic(CacheAble cacheAble, Method method) {
        //如果返回值是个数组或者collection 返回true，否则返回false
        Magic magic = cacheAble.magic();
        Class<?>[] parameterTypes = method.getParameterTypes();
        int iterableArgsIndex = magic.iterableArgsIndex();
        if (parameterTypes != null && parameterTypes.length != 0) {
            if (iterableArgsIndex < 0) {
                throw new RuntimeException();
            }
            if (iterableArgsIndex >= parameterTypes.length) {
                throw new RuntimeException();
            }
            Class<?> parameterType = parameterTypes[iterableArgsIndex];
            if (parameterType.isArray() || Collection.class.isAssignableFrom(parameterType)) {
                return true;
            }
            throw new RuntimeException();
        }
        Class<?> returnType = method.getReturnType();
        if (returnType.isArray() || Collection.class.isAssignableFrom(returnType)) {
            return true;
        }
        return false;
    }
}
