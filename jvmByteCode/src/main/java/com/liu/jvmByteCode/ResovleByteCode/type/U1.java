package com.liu.jvmByteCode.ResovleByteCode.type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class U1 {
    byte[] bytes = new byte[1];

    public U1(byte b1){
        bytes[0] = b1;
    }
}
