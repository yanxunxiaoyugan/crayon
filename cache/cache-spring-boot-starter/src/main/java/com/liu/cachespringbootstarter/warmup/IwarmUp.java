package com.liu.cachespringbootstarter.warmup;

/**
 * 预热接口 实现此接口并把实现类注册为bean可进行预热
 */
public interface IwarmUp {
    void warmUp();
}
