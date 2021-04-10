package com.orient.collab.model;

import com.orient.utils.StringUtil;

import java.io.Serializable;

/**
 * the collab flow information
 *
 * @author Seraph
 *         2016-08-09 上午10:44
 */
public class CollabFlowInfo implements Serializable{

    private String pdId;
    private String piId;
    private String flowStatus;

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public String getPiId() {
        return piId;
    }

    public void setPiId(String piId) {
        this.piId = piId;
    }

    public String getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(String flowStatus) {
        this.flowStatus = flowStatus;
    }

    public Boolean isEmpty(){
        return StringUtil.isEmpty(pdId)&&StringUtil.isEmpty(piId)&&StringUtil.isEmpty(flowStatus);
    }
}
