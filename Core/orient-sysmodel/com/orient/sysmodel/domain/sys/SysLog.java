package com.orient.sysmodel.domain.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-07-02 11:25
 */
@Entity
@Table(name = "CWM_SYS_LOG")
public class SysLog {
    private Long id;
    private String opTypeId;
    private String opUserId;
    private String opIpAddress;
    private Date opDate;
    private String opTarget;
    private String opRemark;
    private String opResult;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CWM_SYS_LOG")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "OP_TYPE_ID", nullable = true, length = 100)
    public String getOpTypeId() {
        return opTypeId;
    }

    public void setOpTypeId(String opTypeId) {
        this.opTypeId = opTypeId;
    }

    @Basic
    @Column(name = "OP_USER_ID", nullable = true, length = 20)
    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    @Basic
    @Column(name = "OP_IP_ADDRESS", nullable = true, length = 15)
    public String getOpIpAddress() {
        return opIpAddress;
    }

    public void setOpIpAddress(String opIpAddress) {
        this.opIpAddress = opIpAddress;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Basic
    @Column(name = "OP_DATE", nullable = true)
    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    @Basic
    @Column(name = "OP_TARGET", nullable = true, length = 200)
    public String getOpTarget() {
        return opTarget;
    }

    public void setOpTarget(String opTarget) {
        this.opTarget = opTarget;
    }

    @Basic
    @Column(name = "OP_REMARK", nullable = true, length = 500)
    public String getOpRemark() {
        return opRemark;
    }

    public void setOpRemark(String opRemark) {
        this.opRemark = opRemark;
    }

    @Basic
    @Column(name = "OP_RESULT", nullable = true, length = 20)
    public String getOpResult() {
        return opResult;
    }

    public void setOpResult(String opResult) {
        this.opResult = opResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysLog sysLog = (SysLog) o;

        if (id != null ? !id.equals(sysLog.id) : sysLog.id != null) return false;
        if (opTypeId != null ? !opTypeId.equals(sysLog.opTypeId) : sysLog.opTypeId != null) return false;
        if (opUserId != null ? !opUserId.equals(sysLog.opUserId) : sysLog.opUserId != null) return false;
        if (opIpAddress != null ? !opIpAddress.equals(sysLog.opIpAddress) : sysLog.opIpAddress != null) return false;
        if (opDate != null ? !opDate.equals(sysLog.opDate) : sysLog.opDate != null) return false;
        if (opTarget != null ? !opTarget.equals(sysLog.opTarget) : sysLog.opTarget != null) return false;
        if (opRemark != null ? !opRemark.equals(sysLog.opRemark) : sysLog.opRemark != null) return false;
        if (opResult != null ? !opResult.equals(sysLog.opResult) : sysLog.opResult != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (opTypeId != null ? opTypeId.hashCode() : 0);
        result = 31 * result + (opUserId != null ? opUserId.hashCode() : 0);
        result = 31 * result + (opIpAddress != null ? opIpAddress.hashCode() : 0);
        result = 31 * result + (opDate != null ? opDate.hashCode() : 0);
        result = 31 * result + (opTarget != null ? opTarget.hashCode() : 0);
        result = 31 * result + (opRemark != null ? opRemark.hashCode() : 0);
        result = 31 * result + (opResult != null ? opResult.hashCode() : 0);
        return result;
    }
}
