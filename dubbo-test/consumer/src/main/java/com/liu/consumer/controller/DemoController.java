package com.liu.consumer.controller;

import com.liu.consumer.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @Autowired
    private ConsumerService consumerService;
    @RequestMapping("/id")
    public  String demo(@RequestParam("id")Integer id){
            return consumerService.demo(id);
    }
}
