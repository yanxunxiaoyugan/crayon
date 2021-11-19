package com.liu.consumer.service;

import com.liu.protocol.UserSerivce;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    @DubboReference
    private UserSerivce userSerivce;
}
