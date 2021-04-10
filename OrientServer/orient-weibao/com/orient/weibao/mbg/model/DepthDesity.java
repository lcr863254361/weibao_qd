package com.orient.weibao.mbg.model;

import java.util.Date;

public class DepthDesity {
    private String id;

    private Date sysDateTime;

    private String sysUsername;

    private Long sysIsDelete;

    private String sysSecrecy;

    private String sysSchema;

    private String sysOperate;

    private String sysFlow;

    private Double cDesity3526;

    private Double cDepth3526;

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

    public Double getcDesity3526() {
        return cDesity3526;
    }

    public void setcDesity3526(Double cDesity3526) {
        this.cDesity3526 = cDesity3526;
    }

    public Double getcDepth3526() {
        return cDepth3526;
    }

    public void setcDepth3526(Double cDepth3526) {
        this.cDepth3526 = cDepth3526;
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
        sb.append(", cDesity3526=").append(cDesity3526);
        sb.append(", cDepth3526=").append(cDepth3526);
        sb.append("]");
        return sb.toString();
    }
}