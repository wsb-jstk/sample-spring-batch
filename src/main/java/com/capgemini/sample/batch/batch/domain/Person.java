package com.capgemini.sample.batch.batch.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Builder
@ToString
@Getter
@RequiredArgsConstructor
public class Person {

    private final int id;
    private final String firstName;
    private final String lastName;

}
