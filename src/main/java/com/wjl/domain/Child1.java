package com.wjl.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Parent;

@Data
@Document(indexName = "pppccc",type="ccc1", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class Child1 {

    @Parent(type="ppp")
    private String parentId;

    @Id
    private Integer id;

    @Field(type= FieldType.text)
    private String childName;

    @Field(type=FieldType.Integer)
    private Integer age;
}
