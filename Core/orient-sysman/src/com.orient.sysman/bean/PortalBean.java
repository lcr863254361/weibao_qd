package com.orient.sysman.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/21 0021.
 */
public class PortalBean implements Serializable{

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
