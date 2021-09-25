package com.liu.jvmByteCode.ResovleByteCode.handler.constantInfo;

import java.nio.ByteBuffer;

public interface ConstantInfoHandler {
    void read(ByteBuffer codeBuffer) throws Exception;
}
