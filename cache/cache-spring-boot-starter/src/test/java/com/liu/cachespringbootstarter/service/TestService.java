package com.liu.cachespringbootstarter.service;

import com.liu.cachespringbootstarter.annotation.CacheAble;
import com.liu.cachespringbootstarter.annotation.CacheDel;
import com.liu.cachespringbootstarter.annotation.Magic;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    @CacheAble(key = "'#args[0]'+#retVal")
    public String stringTest(Integer id){
        return "string";
    }

    @CacheAble(key = "",magic = @Magic(key = "'magic_'+#retVal"))
    public List<String> magicTest(){
        List<String> res = new ArrayList<>();
        res.add("1");
        res.add("2");
        return res;
    }
    @CacheDel(key = "#args[0]")
    public void delString() {

    }
}
