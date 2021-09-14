package com.liu.jvmByteCode.ResovleByteCode.handler;

import com.liu.jvmByteCode.ResovleByteCode.type.ClassFile;
import com.liu.jvmByteCode.ResovleByteCode.type.U2;

import java.nio.ByteBuffer;

public class MajorVersion  implements BaseByteCodeHandler{
    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public void read(ByteBuffer byteBuffer, ClassFile classFile) throws Exception {
        U2 majorVersion = new U2(byteBuffer.get(),byteBuffer.get());
        classFile.setMajorVersion(majorVersion);
    }
}
