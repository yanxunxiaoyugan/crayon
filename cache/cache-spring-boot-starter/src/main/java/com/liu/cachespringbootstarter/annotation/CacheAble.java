package com.liu.cachespringbootstarter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 缓存的注解
 * @author liu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheAble {
    /**
     * 过期时间
     * @return
     */
    int expire() default 300;

    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 缓存的key
     * @return
     */
    String key();

    /**
     * 是否缓存的判断条件
     * @return
     */
    String condition() default "";

    /**
     * 是否启用自动加载
     * @return
     */
    boolean autoLoad() default false;

    /**
     * el表达式  解析缓存的key
     * @return
     */
    String subKeySpEL() default "";


}
