package com.wjl.domain;

import lombok.Data;

@Data
public class CopyOne extends Aligins{
    private String name;

    private String age;

    private Relation relation;

    public CopyOne(){
        relation=new Relation();
    }
}
