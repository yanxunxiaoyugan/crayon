package com.liu.cachespringbootstarter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 删除缓存的注解
 * @author liu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheDel {
    String key();

    long expire() default 60;

    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
