package com.capgemini.sample.batch.batch;

import com.capgemini.sample.batch.batch.domain.CommonNameRow;
import com.capgemini.sample.batch.batch.domain.Person;
import com.capgemini.sample.batch.batch.listeners.JobCompletionListener;
import com.capgemini.sample.batch.batch.listeners.MyChunkListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchProcessingConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    @Value("classpath:sample.csv")
    private final Resource resource;

    @Bean
    RowProcessor processor() {
        return new RowProcessor();
    }

    @Bean
    FlatFileItemReader<CommonNameRow> reader() {
        return new FlatFileItemReaderBuilder<CommonNameRow>().name("personItemReader")
                                                             .resource(resource)
                                                             .delimited()
                                                             .delimiter(",")
                                                             .names(new String[]{"id", "FirstName", "Surname", "Adjustment", "cleanName", "Estimate",
                                                                     "finalEstimate"})
                                                             .linesToSkip(2)
                                                             .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                                                                 setTargetType(CommonNameRow.class);
                                                             }})
                                                             .build();
    }

    @Bean
    JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()//
                                                       .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                                                       .sql("INSERT INTO people(person_id, first_name, last_name) VALUES (:id, :firstName, :lastName)")
                                                       .dataSource(dataSource)
                                                       .build();
    }

    @Bean
    Step step(JdbcBatchItemWriter<Person> writer, MyChunkListener myChunkListener) {
        return stepBuilderFactory.get("step1")
                                 .<CommonNameRow, Person>chunk(10)
                                 .reader(reader())
                                 .processor(processor())
                                 .writer(writer)
                                 .listener(myChunkListener)
                                 .build();
    }

    @Bean
    Job importJob(Step step, JobCompletionListener jobCompletionListener) {
        return jobBuilderFactory.get("importPersonJob")
                                .incrementer(new RunIdIncrementer())
                                .listener(jobCompletionListener)
                                .flow(step)
                                .end()
                                .build();
    }

}
