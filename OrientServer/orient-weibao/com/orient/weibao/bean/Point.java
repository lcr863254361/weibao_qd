package com.orient.weibao.bean;

import java.io.Serializable;

/**
 * 地图上的点
 * @author js_liuyangchao@163.com
 * @create 2019-03-05 15:25
 * @desc
 **/
public class Point implements Serializable {
    private String id;
    private String name;
    private String value;
    private String flowId;          //航段ID

    public Point(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Point(String id, String name, String value, String flowId) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.flowId = flowId;
    }

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }
}
