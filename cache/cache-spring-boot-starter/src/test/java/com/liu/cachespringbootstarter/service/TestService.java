package com.liu.cachespringbootstarter.service;

import com.liu.cachespringbootstarter.annotation.CacheAble;
import com.liu.cachespringbootstarter.annotation.CacheDel;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @CacheAble(key = "#args[0]")
    public String stringTest(Integer id){
        return "string";
    }
    @CacheDel(key = "#args[0]")
    public void delString() {

    }
}
