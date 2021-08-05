package com.liu.cachespringbootstarter.serial;

/**
 * 序列化顶层接口
 */
public interface Serial {
    /**
     * 序列化
     */
    String serial(Object value);

    /**
     * 反序列化
     * @return
     */
    <T> T  deserial(String value,Class<T> clazz);
}
