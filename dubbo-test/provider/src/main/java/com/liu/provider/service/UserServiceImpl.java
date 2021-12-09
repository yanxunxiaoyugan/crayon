package com.liu.provider.service;

import com.liu.protocol.UserSerivce;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class UserServiceImpl implements UserSerivce {
    @Override
    public Integer getUserId(Integer id) {
        return id;
    }
}
