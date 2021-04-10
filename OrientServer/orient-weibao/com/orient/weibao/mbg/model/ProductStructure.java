package com.orient.weibao.mbg.model;

import java.util.Date;

public class ProductStructure {
    private String id;

    private Date sysDateTime;

    private String sysUsername;

    private Long sysIsDelete;

    private String sysSecrecy;

    private String sysSchema;

    private String sysOperate;

    private String sysFlow;

    private String cName3214;

    private String cPid3214;

    private String tCheckRow480Id;

    private String cType3214;

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

    public String getcName3214() {
        return cName3214;
    }

    public void setcName3214(String cName3214) {
        this.cName3214 = cName3214 == null ? null : cName3214.trim();
    }

    public String getcPid3214() {
        return cPid3214;
    }

    public void setcPid3214(String cPid3214) {
        this.cPid3214 = cPid3214 == null ? null : cPid3214.trim();
    }

    public String gettCheckRow480Id() {
        return tCheckRow480Id;
    }

    public void settCheckRow480Id(String tCheckRow480Id) {
        this.tCheckRow480Id = tCheckRow480Id == null ? null : tCheckRow480Id.trim();
    }

    public String getcType3214() {
        return cType3214;
    }

    public void setcType3214(String cType3214) {
        this.cType3214 = cType3214 == null ? null : cType3214.trim();
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
        sb.append(", cName3214=").append(cName3214);
        sb.append(", cPid3214=").append(cPid3214);
        sb.append(", tCheckRow480Id=").append(tCheckRow480Id);
        sb.append(", cType3214=").append(cType3214);
        sb.append("]");
        return sb.toString();
    }
}