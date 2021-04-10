package com.orient.weibao.mbg.model;

import java.util.Date;

public class InformLog {
    private String id;

    private Date sysDateTime;

    private String sysUsername;

    private Long sysIsDelete;

    private String sysSecrecy;

    private String sysSchema;

    private String sysOperate;

    private String sysFlow;

    private String cTableId3566;

    private String cState3566;

    private String cUploadTime3566;

    private String cTaskId3566;

    private String cUploadPerson3566;

    private String cType3566;

    private String cTaskName3566;

    private String cTableName3566;
    //判断检查表是否异常
    private String cIsException3566;

    public String getcIsException3566() {
        return cIsException3566;
    }

    public void setcIsException3566(String cIsException3566) {
        this.cIsException3566 = cIsException3566;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getSysDateTime() {
        return sysDateTime;
    }

    public void setSysDateTime(Date sysDateTime) {
        this.sysDateTime = sysDateTime;
    }

    public String getSysUsername() {
        return sysUsername;
    }

    public void setSysUsername(String sysUsername) {
        this.sysUsername = sysUsername == null ? null : sysUsername.trim();
    }

    public Long getSysIsDelete() {
        return sysIsDelete;
    }

    public void setSysIsDelete(Long sysIsDelete) {
        this.sysIsDelete = sysIsDelete;
    }

    public String getSysSecrecy() {
        return sysSecrecy;
    }

    public void setSysSecrecy(String sysSecrecy) {
        this.sysSecrecy = sysSecrecy == null ? null : sysSecrecy.trim();
    }

    public String getSysSchema() {
        return sysSchema;
    }

    public void setSysSchema(String sysSchema) {
        this.sysSchema = sysSchema == null ? null : sysSchema.trim();
    }

    public String getSysOperate() {
        return sysOperate;
    }

    public void setSysOperate(String sysOperate) {
        this.sysOperate = sysOperate == null ? null : sysOperate.trim();
    }

    public String getSysFlow() {
        return sysFlow;
    }

    public void setSysFlow(String sysFlow) {
        this.sysFlow = sysFlow == null ? null : sysFlow.trim();
    }

    public String getcTableId3566() {
        return cTableId3566;
    }

    public void setcTableId3566(String cTableId3566) {
        this.cTableId3566 = cTableId3566 == null ? null : cTableId3566.trim();
    }

    public String getcState3566() {
        return cState3566;
    }

    public void setcState3566(String cState3566) {
        this.cState3566 = cState3566 == null ? null : cState3566.trim();
    }

    public String getcUploadTime3566() {
        return cUploadTime3566;
    }

    public void setcUploadTime3566(String cUploadTime3566) {
        this.cUploadTime3566 = cUploadTime3566 == null ? null : cUploadTime3566.trim();
    }

    public String getcTaskId3566() {
        return cTaskId3566;
    }

    public void setcTaskId3566(String cTaskId3566) {
        this.cTaskId3566 = cTaskId3566 == null ? null : cTaskId3566.trim();
    }

    public String getcUploadPerson3566() {
        return cUploadPerson3566;
    }

    public void setcUploadPerson3566(String cUploadPerson3566) {
        this.cUploadPerson3566 = cUploadPerson3566 == null ? null : cUploadPerson3566.trim();
    }

    public String getcType3566() {
        return cType3566;
    }

    public void setcType3566(String cType3566) {
        this.cType3566 = cType3566 == null ? null : cType3566.trim();
    }

    public String getcTaskName3566() {
        return cTaskName3566;
    }

    public void setcTaskName3566(String cTaskName3566) {
        this.cTaskName3566 = cTaskName3566 == null ? null : cTaskName3566.trim();
    }

    public String getcTableName3566() {
        return cTableName3566;
    }

    public void setcTableName3566(String cTableName3566) {
        this.cTableName3566 = cTableName3566 == null ? null : cTableName3566.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sysDateTime=").append(sysDateTime);
        sb.append(", sysUsername=").append(sysUsername);
        sb.append(", sysIsDelete=").append(sysIsDelete);
        sb.append(", sysSecrecy=").append(sysSecrecy);
        sb.append(", sysSchema=").append(sysSchema);
        sb.append(", sysOperate=").append(sysOperate);
        sb.append(", sysFlow=").append(sysFlow);
        sb.append(", cTableId3566=").append(cTableId3566);
        sb.append(", cState3566=").append(cState3566);
        sb.append(", cUploadTime3566=").append(cUploadTime3566);
        sb.append(", cTaskId3566=").append(cTaskId3566);
        sb.append(", cUploadPerson3566=").append(cUploadPerson3566);
        sb.append(", cType3566=").append(cType3566);
        sb.append(", cTaskName3566=").append(cTaskName3566);
        sb.append(", cTableName3566=").append(cTableName3566);
        sb.append("]");
        return sb.toString();
    }
}