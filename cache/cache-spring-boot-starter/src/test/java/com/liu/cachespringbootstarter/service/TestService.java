package com.liu.cachespringbootstarter.service;

import com.liu.cachespringbootstarter.annotation.CacheAble;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @CacheAble(key = "#args[0]")
    public String stringTest(Integer id){
        return "string";
    }
}
