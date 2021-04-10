package com.orient.collabdev.model;

import java.io.Serializable;

/**
 * @Description
 * @Author GNY
 * @Date 2018/8/11 10:08
 * @Version 1.0
 **/
public class ResultBind implements Serializable{

    private String nodeId;
    private boolean hasDevData;
    private boolean hasModelData;
    private boolean hasPVMData;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public boolean isHasDevData() {
        return hasDevData;
    }

    public void setHasDevData(boolean hasDevData) {
        this.hasDevData = hasDevData;
    }

    public boolean isHasModelData() {
        return hasModelData;
    }

    public void setHasModelData(boolean hasModelData) {
        this.hasModelData = hasModelData;
    }

    public boolean isHasPVMData() {
        return hasPVMData;
    }

    public void setHasPVMData(boolean hasPVMData) {
        this.hasPVMData = hasPVMData;
    }

}
