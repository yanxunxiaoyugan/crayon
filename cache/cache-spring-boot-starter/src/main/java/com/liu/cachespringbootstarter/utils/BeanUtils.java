package com.liu.cachespringbootstarter.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class BeanUtils {
    static ObjectMapper objectMapper = new ObjectMapper();;
    /**
     * 判断对象是否为基本数据类型
     * @param obj
     * @return
     */
    private static boolean isPrimitive(Object obj){
        return obj.getClass().isPrimitive() || obj instanceof Number || obj instanceof Date
                || obj instanceof LocalDateTime || obj instanceof LocalDate;
    }

    /**
     * 把对象转换成字符串
     * @param o
     * @return
     */
    public static  String toString(Object o){
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "null";
    }

    /**
     * 深度拷贝
     * @param obj
     * @return
     */
    public static  Object deepClone(Object obj){
        if(obj == null){
            return null;
        }
        try {
            // 将对象写到流里
            ByteArrayOutputStream bo=new ByteArrayOutputStream();
            ObjectOutputStream oo=new ObjectOutputStream(bo);
            oo.writeObject(obj);
            // 从流里读出来
            ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi=new ObjectInputStream(bi);
            return(oi.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
