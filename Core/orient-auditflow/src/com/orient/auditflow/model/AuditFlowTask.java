package com.orient.auditflow.model;

/**
 * represent a audit flow's task
 * although an audit flow may bind many datas, they all have the same audit type
 *
 * @author Seraph
 *         2016-08-01 下午2:55
 */
public class AuditFlowTask {

    private String flowTaskId;
    private String piId;
    private String name;
    private String pdName;
    private String pdId;
    private String auditType;
    private String auditTypeDescription;
    private boolean groupTask;
    private String id;

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

    public String getFlowTaskId() {
        return flowTaskId;
    }

    public void setFlowTaskId(String flowTaskId) {
        this.flowTaskId = flowTaskId;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public String getAuditTypeDescription() {
        return auditTypeDescription;
    }

    public void setAuditTypeDescription(String auditTypeDescription) {
        this.auditTypeDescription = auditTypeDescription;
    }

    public String getPiId() {
        return piId;
    }

    public void setPiId(String piId) {
        this.piId = piId;
    }

    public String getPdName() {
        return pdName;
    }

    public void setPdName(String pdName) {
        this.pdName = pdName;
    }

    public boolean isGroupTask() {
        return groupTask;
    }

    public void setGroupTask(boolean groupTask) {
        this.groupTask = groupTask;
    }

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }
}
