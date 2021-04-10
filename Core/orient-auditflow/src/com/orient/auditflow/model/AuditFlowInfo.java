package com.orient.auditflow.model;

import com.orient.auditflow.config.AuditFlowStatus;

import java.util.Date;

/**
 * AuditFlowInfo
 *
 * @author Seraph
 *         2016-08-24 上午11:34
 */
public class AuditFlowInfo {

    private AuditFlowStatus status;
    private String statusValue;
    private String piId;
    private String pdId;
    private Date startTime;
    private Date endTime;

    public AuditFlowInfo(){

    }

    public AuditFlowInfo(AuditFlowStatus status, String pdId, String piId, Date startTime){
        this.status = status;
        this.pdId = pdId;
        this.piId = piId;
        this.startTime = startTime;
    }

    public String getPiId() {
        return piId;
    }

    public void setPiId(String piId) {
        this.piId = piId;
    }

    public AuditFlowStatus getStatus() {
        return status;
    }

    public void setStatus(AuditFlowStatus status) {
        this.status = status;
    }

    public String getStatusValue() {
        return this.status.toString();
    }

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
