package com.liu.jvmByteCode.ResovleByteCode.type;

import com.liu.jvmByteCode.utils.ByteBinaryTransfer;

public class CpInfo {
    private U1 tag;
    private CpInfo(U1 tag){
        this.tag = tag;
    }

    @Override
    public String toString(){
        return ByteBinaryTransfer.toHexString(tag.getBytes());
    }
}
