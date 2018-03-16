package com.wjl.domain;

import lombok.Data;
import org.elasticsearch.common.recycler.Recycler;

@Data
public class CopyTwo extends Aligins{

    private String name;

    private Integer age;

    private Relation relation;

    public CopyTwo(){
        relation=new Relation();
    }
}
