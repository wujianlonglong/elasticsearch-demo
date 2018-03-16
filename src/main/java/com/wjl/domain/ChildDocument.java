package com.wjl.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ChildDocument {

    @Id
    private Long id;


    private String city;
}
