package com.liu.cachespringbootstarter.wrapper;

import java.time.LocalDateTime;

public class CacheWrapper<T> {
    /**
     * 缓存的对象
     */
    private  T cacheObejct;

    /**
     * 上次加载时间
     */
    private LocalDateTime lastLoadTime;
}
