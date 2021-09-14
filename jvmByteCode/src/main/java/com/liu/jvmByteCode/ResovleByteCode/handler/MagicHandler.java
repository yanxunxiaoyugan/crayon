package com.liu.jvmByteCode.ResovleByteCode.handler;

import com.liu.jvmByteCode.ResovleByteCode.type.ClassFile;
import com.liu.jvmByteCode.ResovleByteCode.type.U4;

import java.nio.ByteBuffer;

public class MagicHandler implements BaseByteCodeHandler {


    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void read(ByteBuffer byteBuffer, ClassFile classFile) throws Exception {
        U4 magic = new U4(byteBuffer.get(), byteBuffer.get(), byteBuffer.get(), byteBuffer.get());
        classFile.setMagic(magic);
    }
}
