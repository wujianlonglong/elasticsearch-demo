package com.wjl.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "parentindex",type="parenttype", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class ParentDocument {

    @Id
    private Long id;


    @Field(type= FieldType.text)
    private String country;

}
