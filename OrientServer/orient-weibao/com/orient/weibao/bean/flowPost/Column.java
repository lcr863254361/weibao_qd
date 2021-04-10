package com.orient.weibao.bean.flowPost;

import java.io.Serializable;

public class Column implements Serializable {

    private String header;
    private String dataIndex;
    private String postId;
    private int flex;
    private String xtype;
    private String width;
    private String meType;
    private String align = "center";
    //列的实际字段名称
    private String columnActualField;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex;
    }

    public int getFlex() {
        return flex;
    }

    public void setFlex(int flex) {
        this.flex = flex;
    }

    public String getXtype() {
        return xtype;
    }

    public void setXtype(String xtype) {
        this.xtype = xtype;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getMeType() {
        return meType;
    }
    public void setMeType(String meType) {
        this.meType = meType;
    }

    public String getColumnActualField() {
        return columnActualField;
    }

    public void setColumnActualField(String columnActualField) {
        this.columnActualField = columnActualField;
    }
}
