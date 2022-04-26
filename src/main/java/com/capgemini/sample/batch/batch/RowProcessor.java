package com.capgemini.sample.batch.batch;

import com.capgemini.sample.batch.batch.domain.CommonNameRow;
import com.capgemini.sample.batch.batch.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class RowProcessor implements ItemProcessor<CommonNameRow, Person> {

    @Override
    public Person process(CommonNameRow item) {
        final Person person = Person.builder()
                                    .id(item.getId())
                                    .firstName(item.getFirstName())
                                    .lastName(item.getSurname())
                                    .build();
        log.info("Processed: {}", person);
        return person;
    }

}
