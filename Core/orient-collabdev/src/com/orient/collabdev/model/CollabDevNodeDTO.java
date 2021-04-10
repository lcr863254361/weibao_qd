package com.orient.collabdev.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 树节点的DATA TRANSFER OBJECT
 *
 * @author panduanduan
 * @create 2018-07-27 8:57 AM
 */
public class CollabDevNodeDTO implements Serializable {

    private String id;

    private String name;

    private String type;

    private Integer version;

    private Integer nodeOrder;

    private String status;

    private Integer isRoot;

    private Boolean isHistory = false;

    private String bmDataId;

    private String updateUser;

    private Date updateTime;

    private String rootId;

    private Integer rootVersion;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getNodeOrder() {
        return nodeOrder;
    }

    public void setNodeOrder(Integer nodeOrder) {
        this.nodeOrder = nodeOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(Integer isRoot) {
        this.isRoot = isRoot;
    }

    public Boolean getHistory() {
        return isHistory;
    }

    public void setHistory(Boolean history) {
        isHistory = history;
    }

    public String getBmDataId() {
        return bmDataId;
    }

    public void setBmDataId(String bmDataId) {
        this.bmDataId = bmDataId;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public Integer getRootVersion() {
        return rootVersion;
    }

    public void setRootVersion(Integer rootVersion) {
        this.rootVersion = rootVersion;
    }

}
