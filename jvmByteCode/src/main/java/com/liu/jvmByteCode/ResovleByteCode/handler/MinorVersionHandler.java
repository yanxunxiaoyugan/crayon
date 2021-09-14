package com.liu.jvmByteCode.ResovleByteCode.handler;

import com.liu.jvmByteCode.ResovleByteCode.type.ClassFile;
import com.liu.jvmByteCode.ResovleByteCode.type.U2;

import java.nio.ByteBuffer;

public class MinorVersionHandler  implements BaseByteCodeHandler{
    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public void read(ByteBuffer byteBuffer, ClassFile classFile) throws Exception {
        U2 minorVersion = new U2(byteBuffer.get(),byteBuffer.get());
        classFile.setMinorVersion(minorVersion);
    }
}
