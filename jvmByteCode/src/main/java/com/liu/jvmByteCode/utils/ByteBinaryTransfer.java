package com.liu.jvmByteCode.utils;

import com.liu.jvmByteCode.constant.BinaryTransferConstant;

/**
 * byte转换器
 */
public class ByteBinaryTransfer {

    private static char[] hexChar = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public  static int toInt(byte[] value){
        if(value.length > BinaryTransferConstant.MAX_BYTEARRAY_TO_INT_LENGTH){
            throw new RuntimeException("byte数组过长，无法转换成int类型");
        }
        int offset = 8;
        int res = 0;
        for(int i = 0 ; i < value.length; i++){
            res = (res << offset) | (value[i] & 0xff);
        }
        return res;
    }

    /**
     * 把byte数组转换成String类型
     * @param value
     * @return
     */
    public static String  toHexString(byte[] value){
        StringBuilder hexStr = new StringBuilder();
        for (int i = value.length - 1; i >= 0; i--) {
            int v = value[i] & 0xff;
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
}
