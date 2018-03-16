package com.wjl.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "pppccc", type = "ppp", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class Parents {

    @Id
    private Integer id;

    @Field(type= FieldType.text)
    private String name;

    @Field(type=FieldType.Integer)
    private Integer age;

}
