package com.liu.jvmByteCode.ResovleByteCode.type;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class U1 {
    byte[] bytes = new byte[1];

    public U1(byte b1){
        bytes[0] = b1;
    }
}
