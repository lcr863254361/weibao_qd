package com.orient.sysman.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/29.
 */
public class DeptBean implements Serializable, Comparable<DeptBean> {
    private String id;
    private String text;

    private String pid;
    private String name;
    private String function;
    private String notes;

    private List<DeptBean> children = new ArrayList<>();

    private String iconCls;
    private Boolean expanded = true;
    private Boolean leaf = false;
    private Long order;

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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<DeptBean> getChildren() {
        return children;
    }

    public void setChildren(List<DeptBean> children) {
        this.children = children;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    @Override
    public int compareTo(DeptBean dept) {
        Long order1 = 0l;
        if(this.getOrder() != null) {
            order1 = this.getOrder();
        }
        Long order2 = 0l;
        if(dept!=null && dept.getOrder()!=null) {
            order2 = dept.getOrder();
        }
        return (int) (order1 - order2);
    }
}
