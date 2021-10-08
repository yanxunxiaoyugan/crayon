package com.liu.test.jdk8;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {
    public static void main(String[] args) {

        HashMap<Integer, Integer> hashMap = new HashMap<>(16);
        hashMap.put(1,1);
        ConcurrentHashMap<Integer,Integer> concurrentHashMap = new ConcurrentHashMap<>(16);
        concurrentHashMap.put(12,12);
        concurrentHashMap.put(12,21);
        concurrentHashMap.put(44,21);
        System.out.println(concurrentHashMap);
    }

}
