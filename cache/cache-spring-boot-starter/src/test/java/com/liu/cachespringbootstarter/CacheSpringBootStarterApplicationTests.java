package com.liu.cachespringbootstarter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBootApplication
class CacheSpringBootStarterApplicationTests {
    public static void main(String[] args) {
        SpringApplication.run(CacheSpringBootStarterApplicationTests.class, args);
    }
    @Test
    void contextLoads() {
    }

}
