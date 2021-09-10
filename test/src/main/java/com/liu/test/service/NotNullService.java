package com.liu.test.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class NotNullService {
    public String notNullTest(@Valid @NonNull Integer id) {
        System.out.println(id);
        return "success";
    }
}
