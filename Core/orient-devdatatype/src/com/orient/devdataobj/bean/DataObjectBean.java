package com.orient.devdataobj.bean;

import com.orient.sysmodel.domain.taskdata.DataObjectEntity;

import java.util.List;

/**
 * Created by mengbin on 16/7/15.
 * Purpose:
 * Detail:
 */
public class DataObjectBean extends DataObjectEntity {

    private String createUser;
    private String modifiedUser;

    private String dataTypeShowName;    //数据类型的名称
    private String extendsTypeRealName; //扩展数据类型的基础名称

    private boolean leaf;
    private String parentId;
    private boolean checked = false;

    private String iconCls = "";

    private String upName;  //上游节点名称

    private List<DataObjectBean> children;

    public String getUpName() {
        return upName;
    }

    public void setUpName(String upName) {
        this.upName = upName;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
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

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<DataObjectBean> getChildren() {
        return children;
    }

    public void setChildren(List<DataObjectBean> children) {
        this.children = children;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
}
