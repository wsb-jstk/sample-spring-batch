package com.capgemini.sample.batch.batch.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CommonNameRow {

    private int id;
    private String firstName;
    private String surname;
    private String adjustment;
    private String cleanName;
    private BigDecimal estimate;
    private BigDecimal finalEstimate;

}
