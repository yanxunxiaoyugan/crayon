package com.liu.jvmByteCode.ResovleByteCode.handler;

import com.liu.jvmByteCode.ResovleByteCode.type.ClassFile;

import java.nio.ByteBuffer;

/**
 * 解析器的顶层接口
 */
public interface BaseByteCodeHandler {
    /**
     * 获取解析器的order
     */
    int getOrder();

    /**
     * 从byteBUffer读取到classFile
     * @param byteBuffer
     * @param classFile
     * @throws Exception
     */
    void read(ByteBuffer byteBuffer, ClassFile classFile) throws Exception;
}
