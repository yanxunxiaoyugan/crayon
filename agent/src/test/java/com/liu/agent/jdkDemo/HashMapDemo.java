package com.liu.agent.jdkDemo;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

import java.util.HashMap;

public class HashMapDemo {
    public static void main(String[] args) {
//        HashMap<Integer, Integer> map = new HashMap();
//        map.put(1,1);
//        map.put(1,2);
//        map.put(17,2);

        final char[] chars = "".toCharArray();
        long objectSize = ObjectSizeCalculator.getObjectSize(chars);
        ObjectSizeCalculator.getObjectSize("");
        System.out.println(chars.length);
        System.out.println(objectSize);
        
    }
}

