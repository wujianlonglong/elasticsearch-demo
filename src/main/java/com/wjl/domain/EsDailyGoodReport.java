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
 * Created by wujianlong on 2017/7/10.
 */
@Data
@Document(indexName = "goodreport", type = "goodtype", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class EsDailyGoodReport implements Serializable {

    @Id
    private String id;

    /**
     * 销售日期
     */
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date saleDate;

    /**
     * 商品编号
     */
    @Field(type=FieldType.keyword)
    private String goodId;

    /**
     * 平台号
     */
    @Field(type=FieldType.keyword)
    private String platformId;

    /**
     * 单价
     */
    @Field(type = FieldType.Double)
    private double unitPrice;

    /**
     * 商品名称
     */
    @Field(type=FieldType.keyword)
    private String goodName;

    /**
     * 组别
     */
    @Field(type = FieldType.keyword)
    private String groupId;

    /**
     * 销售数量
     */
    @Field(type = FieldType.Integer)
    private Integer totalNum;

    /**
     * 销售额
     */
    @Field(type = FieldType.Double)
    private double totalMoney;
}
