package com.liu.jvmByteCode.ResovleByteCode.type;

import lombok.Data;

@Data
public class ClassFile {
    /**
     * 魔数
     */
    private U4 magic;
    private U2 minorVersion;
    private U2 majorVersion;
    /**
     * 常量池数量
     */
    private U2 constantPoolCount;
    private CpInfo[] ConstantPool;

    private U2 AccessFlags;
    private U2 thisClass;
    private U2 superClass;
    private U2 interfacesCount;
    private U2 interfaces;
    private U2 fieldCount;
    private FieldInfo[] fields;
    private U2 methodCount;
    private MethodInfo[] methods;
    private U2 attributesCount;
    private AttributeInfo[] attributeInfos;





}
