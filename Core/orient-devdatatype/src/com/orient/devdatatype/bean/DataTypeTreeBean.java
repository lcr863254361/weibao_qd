package com.orient.devdatatype.bean;



/**
 * Created by mengbin on 16/7/26.
 * Purpose:
 * Detail:
 */
public class DataTypeTreeBean {

    private  String dataTypeId;
    private  String iconCls;
    private  boolean leaf =true;
    private  Short    isref;
    private String  dataType;
    private String text;

    private String dataTypeShowName;    //数据类型的名称
    private String extendsTypeRealName; //扩展数据类型的基础名称

    public String getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(String dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public Short getIsref() {
        return isref;
    }

    public void setIsref(Short isref) {
        this.isref = isref;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDataTypeShowName() {
        return dataTypeShowName;
    }

    public void setDataTypeShowName(String dataTypeShowName) {
        this.dataTypeShowName = dataTypeShowName;
    }

    public String getExtendsTypeRealName() {
        return extendsTypeRealName;
    }

    public void setExtendsTypeRealName(String extendsTypeRealName) {
        this.extendsTypeRealName = extendsTypeRealName;
    }
}
