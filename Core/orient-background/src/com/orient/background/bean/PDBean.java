package com.orient.background.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class PDBean implements Serializable {

    private String id;

    private String name;

    private String version;

    private String type;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
