package com.liu.jvmByteCode.ResovleByteCode.type;

import com.liu.jvmByteCode.utils.ByteBinaryTransfer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class U4 {

    byte[] bytes = new byte[4];

    public U4(byte b1, byte b2,byte b3,byte b4){
        bytes[0] = b1;
        bytes[1] = b2;
        bytes[2] = b3;
        bytes[3] = b4;
    }
    @Override
    public String toString() {
        return ByteBinaryTransfer.toHexString(bytes);
    }
}
