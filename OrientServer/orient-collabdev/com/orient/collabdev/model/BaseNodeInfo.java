package com.orient.collabdev.model;

import java.io.Serializable;

/**
 * @Description
 * @Author GNY
 * @Date 2018/10/23 9:39
 * @Version 1.0
 **/
public class BaseNodeInfo implements Serializable {

    private String id;
    private String projectName;
    private String type;
    private String status;
    private int version;
    private String name;
    private String techStatus;
    private boolean leaf;
    private String iconCls;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTechStatus() {
        return techStatus;
    }

    public void setTechStatus(String techStatus) {
        this.techStatus = techStatus;
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

}
