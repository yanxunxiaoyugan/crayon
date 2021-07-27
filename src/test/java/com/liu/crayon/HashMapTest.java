package com.liu.crayon;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class HashMapTest {
    @Test
    public void modCountTest(){
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1,0);
        map.put(16,0);
        map.put(1,2);
        map.put(1,3);
        map.put(17,0);
    }
}
