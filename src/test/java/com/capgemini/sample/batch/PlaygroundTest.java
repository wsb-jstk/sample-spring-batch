package com.capgemini.sample.batch;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowStep;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Template for tests
 */
@JdbcTest
@SpringBatchTest
@ContextConfiguration(classes = PlaygroundTest.TestConfig.class)
class PlaygroundTest {

    @Test
    void testSomething() {
        // given
        // when
        // then
        assertTrue(true);
    }

    @TestConfiguration
    @EnableBatchProcessing
    static class TestConfig {

        @Autowired
        public JobBuilderFactory jobBuilderFactory;
        @Autowired
        public StepBuilderFactory stepBuilderFactory;

        @Bean
        public Job dummyJob() {
            return this.jobBuilderFactory.get("importUserJob")//
                                         .incrementer(new RunIdIncrementer())//
                                         .listener(new JobExecutionListenerSupport())// empty listener
                                         .flow(new FlowStep()) // empty step
                                         .end()//
                                         .build();
        }

    }

}
