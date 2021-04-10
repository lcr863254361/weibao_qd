package com.orient.sysmodel.domain.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
@Entity
@Table(name = "AUDIT_FLOW_MODEL_BIND")
public class AuditFlowModelBindEntity {
    private Long id;
    private String schemaId;
    private String modelId;
    private String flowName;
    private String flowVersion;
    private byte[] auditForm;
    private String userName;
    private Date lastUpdateDate;
    private String remark;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_AUDIT_FLOW_MODEL_BIND")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SCHEMA_ID")
    public String getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(String schemaId) {
        this.schemaId = schemaId;
    }

    @Basic
    @Column(name = "MODEL_NAME")
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Basic
    @Column(name = "FLOW_NAME")
    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    @Basic
    @Column(name = "FLOW_VERSION")
    public String getFlowVersion() {
        return flowVersion;
    }

    public void setFlowVersion(String flowVersion) {
        this.flowVersion = flowVersion;
    }

    @Basic
    @Column(name = "AUDIT_FORM")
    public byte[] getAuditForm() {
        return auditForm;
    }

    public void setAuditForm(byte[] auditForm) {
        this.auditForm = auditForm;
    }

    @Basic
    @Column(name = "USER_NAME")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Basic
    @Column(name = "LAST_UPDATE_DATE")
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuditFlowModelBindEntity that = (AuditFlowModelBindEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (schemaId != null ? !schemaId.equals(that.schemaId) : that.schemaId != null) return false;
        if (modelId != null ? !modelId.equals(that.modelId) : that.modelId != null) return false;
        if (flowName != null ? !flowName.equals(that.flowName) : that.flowName != null) return false;
        if (flowVersion != null ? !flowVersion.equals(that.flowVersion) : that.flowVersion != null) return false;
        if (!Arrays.equals(auditForm, that.auditForm)) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(that.lastUpdateDate) : that.lastUpdateDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (schemaId != null ? schemaId.hashCode() : 0);
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + (flowName != null ? flowName.hashCode() : 0);
        result = 31 * result + (flowVersion != null ? flowVersion.hashCode() : 0);
        result = 31 * result + (auditForm != null ? Arrays.hashCode(auditForm) : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        return result;
    }
}
