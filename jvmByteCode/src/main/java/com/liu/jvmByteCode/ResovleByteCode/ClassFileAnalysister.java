package com.liu.jvmByteCode.ResovleByteCode;

import com.liu.jvmByteCode.ResovleByteCode.handler.BaseByteCodeHandler;
import com.liu.jvmByteCode.ResovleByteCode.handler.MagicHandler;
import com.liu.jvmByteCode.ResovleByteCode.handler.MajorVersion;
import com.liu.jvmByteCode.ResovleByteCode.handler.MinorVersionHandler;
import com.liu.jvmByteCode.ResovleByteCode.type.ClassFile;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClassFileAnalysister {
    /**
     * 正好16个handler
     */
    private final static List<BaseByteCodeHandler> handlers = new ArrayList<>(16);
    static{
        handlers.add(new MagicHandler());
        handlers.add(new MajorVersion());
        handlers.add(new MinorVersionHandler());
        handlers.sort((Comparator.comparingInt(BaseByteCodeHandler::getOrder)));
    }
    public void parse(ByteBuffer byteBuffer) throws Exception {
        ClassFile classFile = new ClassFile();
        for(BaseByteCodeHandler handler : handlers){
            handler.read(byteBuffer,classFile);
        }
        System.out.println(classFile);
    }
}
