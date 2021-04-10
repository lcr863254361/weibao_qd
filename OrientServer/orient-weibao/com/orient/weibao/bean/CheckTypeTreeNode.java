package com.orient.weibao.bean;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2018-12-14 13:54
 */
public class CheckTypeTreeNode {

    private String id;
    private String dataId;
    private String text;
    private boolean expanded=false;
    private boolean leaf=false;
    private String iconCls;
    private String icon;
    private String order;
    private String type;
    private String qtip;
    private String checkTypeId;
    //模板类型
    private String tempType;
    //是否重复上传
    private String isRepeatUpload;

    public String getIsRepeatUpload() {
        return isRepeatUpload;
    }

    public void setIsRepeatUpload(String isRepeatUpload) {
        this.isRepeatUpload = isRepeatUpload;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQtip() {
        return qtip;
    }

    public void setQtip(String qtip) {
        this.qtip = qtip;
    }

    public String getCheckTypeId() {
        return checkTypeId;
    }

    public void setCheckTypeId(String checkTypeId) {
        this.checkTypeId = checkTypeId;
    }

    public String getTempType() {
        return tempType;
    }

    public void setTempType(String tempType) {
        this.tempType = tempType;
    }
}
