package com.wjl.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * Created by wujianlong on 2017/4/14.
 */
@Data
@Document(indexName = "report", type = "storereport", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class EsDailyStoreReport {
    /**
     * 销售日期
     */
    private Date saleDate;

    /**
     * 门店号
     */
    private String storeId;

    /**
     * 平台号
     */
    private String platformId;

    /**
     * 有效订单销售量
     */
    @Field(type = FieldType.Integer, store = true)
    private Integer totalOrder;

    /**
     * 销售额
     */
    @Field(type = FieldType.Double, store = true)
    private Double totalSales;

    /**
     * 销售商品总件数
     */
    @Field(type = FieldType.Integer, store = true)
    private Integer totalGoods;

    /**
     * 门店号和名称
     */
    @Field(type=FieldType.Object,store=true)
    private String storeIdAndName;
}
