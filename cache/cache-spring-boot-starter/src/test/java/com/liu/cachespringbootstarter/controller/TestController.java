package com.liu.cachespringbootstarter.controller;

import com.liu.cachespringbootstarter.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private TestService testService;
    @GetMapping("/test")
    public String test(){
        return testService.stringTest(1);
    }

    @DeleteMapping("/del")
    public String del(){
        testService.delString();
        return "del success";
    }
}
