package com.wjl.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


import java.io.Serializable;
import java.util.List;

@Data
@Document(indexName = "nestindex", type = "nesttype", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class NestTest implements Serializable {

    @Id
    private Long id;

    @Field(type= FieldType.text)
    private String userName;

    @Field(type= FieldType.Nested)
    private List<GameUser> gameUserList;
}
