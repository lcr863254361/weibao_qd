package com.orient.weibao.bean;

/**
 * @author js_liuyangchao@163.com
 * @create 2019-03-11 14:54
 * @desc
 **/
public class Fields {

    private String name;
    private String type = "string";
    private String description;

    public Fields(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
