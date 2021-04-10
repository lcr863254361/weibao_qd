package com.orient.devdatatype.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengbin on 16/7/26.
 * Purpose:
 * Detail:
 */
public class RankTypeTreeBean {

    private  String text;
    private  String tag;
    private  boolean leaf = false;
    private  boolean expanded;
    private  String iconCls;

    private List<DataTypeTreeBean> results = new ArrayList<>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public List<DataTypeTreeBean> getResults() {
        return results;
    }

    public void setResults(List<DataTypeTreeBean> results) {
        this.results = results;
    }

    public String getIconCls() {return iconCls;}

    public void setIconCls(String iconCls) {this.iconCls = iconCls;}
}
