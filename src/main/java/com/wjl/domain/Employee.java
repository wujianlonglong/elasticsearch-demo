package com.wjl.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;


@Data
@Document(indexName = "company")
public class Employee {

    private String name;

    private String dob;

    private String hobby;
}
