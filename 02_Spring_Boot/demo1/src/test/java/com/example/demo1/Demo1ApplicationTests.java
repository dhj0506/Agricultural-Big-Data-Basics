package com.example.demo1;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Demo1ApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testLog() {
        log.trace("这是 info 级别");
        log.debug("这是 debug 级别");
        log.info("这是 info 级别");
        log.warn("这是 warn 级别");
        log.error("这是 error 级别");
    }

}
