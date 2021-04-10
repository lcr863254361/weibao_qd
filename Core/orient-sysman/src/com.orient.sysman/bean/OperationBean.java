package com.orient.sysman.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/28
 */
public class OperationBean implements Serializable {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
