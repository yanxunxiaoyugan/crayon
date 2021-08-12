package com.liu.cachespringbootstarter.paser;

import com.liu.cachespringbootstarter.annotation.CacheAble;
import com.liu.cachespringbootstarter.annotation.Magic;
import com.liu.cachespringbootstarter.dto.CacheKeyDto;
import org.aopalliance.intercept.MethodInvocation;

/**
 * ognl表达式解析器
 */
public class OgnlPaser implements Ipaser {
    @Override
    public CacheKeyDto getCacheAbleKey(MethodInvocation methodInvocation, CacheAble cacheAble) {
        return null;
    }

    @Override
    public CacheKeyDto getCacheMagicKey(MethodInvocation methodInvocation, Magic magic,Object returnValue) {
        return null;
    }
}
