package com.liu.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public TestEntity test(){
        return new TestEntity(12,"Asdf");
    }

}
