package com.liu.crayon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.liu.crayon.dao")
public class CrayonApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrayonApplication.class, args);
    }

}
