package com.liu.test.springtest;


import com.liu.test.TestApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
public class AsyncTestTest {
    @Autowired
    private AsyncTest asyncTest;
    @Test
    public void async1() {
        asyncTest.async1();
    }

    @Test
    public void async2() {
        asyncTest.async2();
    }
}