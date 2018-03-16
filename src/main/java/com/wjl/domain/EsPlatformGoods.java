package com.wjl.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;


/**
 * Created by cs on 2016/7/6.
 * 商品对象
 */
@Data
@Document(indexName = "platgoods", type = "platgoodstype", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class EsPlatformGoods implements Serializable {
    @Id
    private Integer id;

    //平台号
    @Field(type = FieldType.keyword)
    private String platformId;

    //三江管理编码
    @Field(type = FieldType.keyword)
    private String sjGoodsCode;

    //商品名称
    private String sjGoodsName;

    //外部商品编码
    @Field(type = FieldType.keyword)
    private String platformGoodsCode;

    //三江管理编码+外部商品编码+商品名称
    private String sjCodeAndwbCodeAndName;


    //商品状态：上架(1)、下架(2)、删除(0)
    @Field(type = FieldType.Integer)
    private Integer status;

    //特殊品标记：是(1)、否(0)
    @Field(type = FieldType.Integer)
    private Integer specFlag;


}
