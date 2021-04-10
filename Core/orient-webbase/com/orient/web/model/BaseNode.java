package com.orient.web.model;

import java.io.Serializable;

/**
 * 节点基类
 *
 * @author enjoy
 * @creare 2016-05-17 12:27
 */
public class BaseNode implements Serializable {

    private String id;

    private String text = "空";

    //默认节点样式
    private String iconCls = "icon-data-node";

    private Boolean leaf = false;

    private Boolean expanded = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public BaseNode(String id, String text, String iconCls, Boolean leaf) {
        this.id = id;
        this.text = text;
        this.iconCls = iconCls;
        this.leaf = leaf;
    }

    public BaseNode(String id, String text, String iconCls, Boolean leaf, Boolean expanded) {
        this.id = id;
        this.text = text;
        this.iconCls = iconCls;
        this.leaf = leaf;
        this.expanded = expanded;
    }

    public BaseNode() {

    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
}

