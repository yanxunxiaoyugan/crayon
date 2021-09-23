package com.liu.jvmByteCode.constant;

public interface CpInfoConstant {
    enum CpInfoTag{
        Constant_class_info(7),
        Constant_fieldref_info(9),
        //符号引用
        Constant_methodref_info(10),
        Constant_interfaceMethodref_info(11),
        Constant_string_info(8),
        Constant_float_info(4),
        Constant_integer_info(3),
        Constant_long_info(5),
        Constant_double_info(6),
        //字段与字段类型或方法与方法类型的符号引用
        Constant_nameAndType_info(12),
        Constant_utf8_info(1),
        Constant_methodHandle_info(15),
        Constant_methodType_info(16),
        Constant_invokeDynamic_info(18),
        ;
        int code ;
        CpInfoTag(int code){
            this.code = code;
        }
    }
}
