package com.orient.sysmodel.domain.flow;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
@Entity
@Table(name = "AUDIT_FLOW_TASK_SETTING")
public class AuditFlowTaskSettingEntity {
    private String taskName;
    private Long id;
    private Long formId;
    private String customPath;
    private Long canAssignOther;
    private Long belongAuditBind;

    @Basic
    @Column(name = "TASK_NAME")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_AUDIT_FLOW_TASK_SETTING")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "FORM_ID")
    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    @Basic
    @Column(name = "CUSTOM_PATH")
    public String getCustomPath() {
        return customPath;
    }

    public void setCustomPath(String customPath) {
        this.customPath = customPath;
    }

    @Basic
    @Column(name = "CAN_ASSIGN_OTHER")
    public Long getCanAssignOther() {
        return canAssignOther;
    }

    public void setCanAssignOther(Long canAssignOther) {
        this.canAssignOther = canAssignOther;
    }

    @Basic
    @Column(name = "BELONG_AUDIT_BIND")
    public Long getBelongAuditBind() {
        return belongAuditBind;
    }

    public void setBelongAuditBind(Long belongAuditBind) {
        this.belongAuditBind = belongAuditBind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuditFlowTaskSettingEntity that = (AuditFlowTaskSettingEntity) o;

        if (taskName != null ? !taskName.equals(that.taskName) : that.taskName != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (formId != null ? !formId.equals(that.formId) : that.formId != null) return false;
        if (customPath != null ? !customPath.equals(that.customPath) : that.customPath != null) return false;
        if (canAssignOther != null ? !canAssignOther.equals(that.canAssignOther) : that.canAssignOther != null)
            return false;
        if (belongAuditBind != null ? !belongAuditBind.equals(that.belongAuditBind) : that.belongAuditBind != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taskName != null ? taskName.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (formId != null ? formId.hashCode() : 0);
        result = 31 * result + (customPath != null ? customPath.hashCode() : 0);
        result = 31 * result + (canAssignOther != null ? canAssignOther.hashCode() : 0);
        result = 31 * result + (belongAuditBind != null ? belongAuditBind.hashCode() : 0);
        return result;
    }
}
