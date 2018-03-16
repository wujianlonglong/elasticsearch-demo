package com.wjl.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "orderindex",type="ordermain", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class Order {

    @Id
    private Long orderId;

    @Field(type=FieldType.Integer)
    private Integer shopId;

    @Field(type= FieldType.text)
    private String buyName;
}
