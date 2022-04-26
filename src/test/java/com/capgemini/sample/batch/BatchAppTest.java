package com.capgemini.sample.batch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BatchAppTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testContext() {
        assertNotNull(this.applicationContext);
    }

}
