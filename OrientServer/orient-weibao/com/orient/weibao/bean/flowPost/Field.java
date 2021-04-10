package com.orient.weibao.bean.flowPost;

import java.io.Serializable;

public class Field implements Serializable {

    private String type;
    private String name;

    public Field() {
    }

    public Field(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
