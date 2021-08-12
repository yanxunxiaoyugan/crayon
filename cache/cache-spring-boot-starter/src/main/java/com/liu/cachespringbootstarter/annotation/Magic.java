package com.liu.cachespringbootstarter.annotation;

import java.lang.annotation.*;

/**
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface Magic {
    String key();

    /**
     * 需要分割的参数索引
     * @return
     */
    int iterableArgsIndex() default 0;
    boolean returnNull() default false;
}
