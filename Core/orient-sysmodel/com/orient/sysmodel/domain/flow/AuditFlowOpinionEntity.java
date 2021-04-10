package com.orient.sysmodel.domain.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
@Entity
@Table(name = "JBPM4_OPINION")
public class AuditFlowOpinionEntity {
    private Long id;
    private String flowid;
    private String activity;
    private Date handletime;
    private String handleuser;
    private String handlestatus;
    private String label;
    private String value;
    private String flowTaskId;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_JBPM4_OPINION")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "FLOWID")
    public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid;
    }

    @Basic
    @Column(name = "FLOWTASKID")
    public String getFlowTaskId() {
        return flowTaskId;
    }

    public void setFlowTaskId(String flowTaskId) {
        this.flowTaskId = flowTaskId;
    }

    @Basic
    @Column(name = "ACTIVITY")
    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Basic
    @Column(name = "HANDLETIME")
    public Date getHandletime() {
        return handletime;
    }

    public void setHandletime(Date handletime) {
        this.handletime = handletime;
    }

    @Basic
    @Column(name = "HANDLEUSER")
    public String getHandleuser() {
        return handleuser;
    }

    public void setHandleuser(String handleuser) {
        this.handleuser = handleuser;
    }

    @Basic
    @Column(name = "HANDLESTATUS")
    public String getHandlestatus() {
        return handlestatus;
    }

    public void setHandlestatus(String handlestatus) {
        this.handlestatus = handlestatus;
    }

    @Basic
    @Column(name = "LABEL")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Basic
    @Column(name = "VALUE")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuditFlowOpinionEntity that = (AuditFlowOpinionEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (flowid != null ? !flowid.equals(that.flowid) : that.flowid != null) return false;
        if (activity != null ? !activity.equals(that.activity) : that.activity != null) return false;
        if (handletime != null ? !handletime.equals(that.handletime) : that.handletime != null) return false;
        if (handleuser != null ? !handleuser.equals(that.handleuser) : that.handleuser != null) return false;
        if (handlestatus != null ? !handlestatus.equals(that.handlestatus) : that.handlestatus != null) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (flowid != null ? flowid.hashCode() : 0);
        result = 31 * result + (activity != null ? activity.hashCode() : 0);
        result = 31 * result + (handletime != null ? handletime.hashCode() : 0);
        result = 31 * result + (handleuser != null ? handleuser.hashCode() : 0);
        result = 31 * result + (handlestatus != null ? handlestatus.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
