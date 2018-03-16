package com.wjl.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * Created by wujianlong on 2017/6/28.
 */
@Data
@Document(indexName ="tests",type="tests" ,indexStoreType="fs",shards=5,replicas=1,refreshInterval="-1")
public class Tutorial implements Serializable {
//    private Long id;


    private String name;//教程名称
}