package com.orient.dsrestful.domain.share;

import java.io.Serializable;

/**
 * Created by GNY on 2018/3/27
 */
public class ShareTable implements Serializable {

    private String id;
    private String name;
    private String displayName;

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
