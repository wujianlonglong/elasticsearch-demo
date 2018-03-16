package com.wjl.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wujianlong on 2017/6/28.
 */
@Data
@Document(indexName="projectname",type="article",indexStoreType="fs",shards=5,replicas=1,refreshInterval="-1")
public class Article implements Serializable {

    @Id
    private String id;

    /**标题*/
    private String title;

    /**摘要*/
    private String abstracts;
    /**内容*/
    private String content;
    /**发表时间*/
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date postTime;
    /**点击率*/
    private Long clickCount;
    /**作者*/
    private Author author;
    /**所属教程*/
    private Tutorial tutorial;

}