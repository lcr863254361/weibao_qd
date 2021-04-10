package com.orient.web.base;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/24 0024
 */
public class ExtSorter implements Serializable {

    public ExtSorter() {
    }

    private String property;

    private String direction;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

}
