package com.liu.cachespringbootstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class CacheSpringBootStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheSpringBootStarterApplication.class, args);
    }

}
