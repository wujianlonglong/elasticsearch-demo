package com.wjl.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "orderindex",type = "orderproduct", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class OrderProduct {
    @Id
    private Integer id;

    @Field(type= FieldType.text)
    private String productName;

    @Field(type= FieldType.text)
    private String productCode;

    @Field(type= FieldType.Double)
    private Double price;
}
