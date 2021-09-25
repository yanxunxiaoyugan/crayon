package com.liu.jvmByteCode.ResovleByteCode.handler.constantInfo;

import com.liu.jvmByteCode.ResovleByteCode.type.U1;
import com.liu.jvmByteCode.ResovleByteCode.type.U2;
import lombok.Getter;
import lombok.Setter;

import javax.websocket.server.ServerEndpoint;

@Getter
@Setter
public class ConstantUtf8Info {
    private U1 tag;
    private U2 length;
    private U1[] bytes;
}
