package com.wjl.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "nestindex")
public class GameUser {

    @Field(type= FieldType.text)
    private String gameName;

    @Field(type= FieldType.text)
    private String profession;

    @Field(type= FieldType.Integer)
    private Integer gameLevel;

}
