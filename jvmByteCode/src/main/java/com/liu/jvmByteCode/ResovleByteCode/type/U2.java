package com.liu.jvmByteCode.ResovleByteCode.type;

import com.liu.jvmByteCode.utils.BinaryTransfer;
import com.liu.jvmByteCode.utils.ByteBinaryTransfer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class U2 {
    byte[] bytes = new byte[2];
    
    public U2(byte b1, byte b2){
        bytes[0] = b1;
        bytes[1] = b2;
    }

    public int toInt(){
        System.out.println(this.toString());
        return (int)((bytes[0] & 0xff) << 8 | (bytes[1] & 0xff));
    }

    public String toHexString() {
        char[] hexChar = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder hexStr = new StringBuilder();
        for (int i = 1; i >= 0; i--) {
            int v = bytes[i] & 0xff;
            while (v > 0) {
                int c = v % 16;
                v = v >>> 4;
                hexStr.insert(0, hexChar[c]);
            }
            if (((hexStr.length() & 0x01) == 1)) {
                hexStr.insert(0, '0');
            }
        }
        return "0x" + hexStr.toString();
    }

    @Override
    public String toString() {
        return String.valueOf(ByteBinaryTransfer.toInt(bytes));
    }
}
