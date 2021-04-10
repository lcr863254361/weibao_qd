package com.orient.sysmodel.domain.collabdev.approval;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * @Description 数据质量-数据审批设置表
 * @Author GNY
 * @Date 2018/7/27 9:26
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_SETTINGS_APPROVAL")
public class CollabSettingsApproval {

    private String id;
    private String approvalType;
    private String settingsTarget;
    private String triggerType;
    private String pdName;
    private String pdVersion;

    private List<CollabSettingsApprovalDetail> details;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_SETTINGS_APPROVAL")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "APPROVAL_TYPE")
    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }

    @Basic
    @Column(name = "CB_SETTINGS_TARGET")
    public String getSettingsTarget() {
        return settingsTarget;
    }

    public void setSettingsTarget(String settingsTarget) {
        this.settingsTarget = settingsTarget;
    }

    @Basic
    @Column(name = "TRIGGER_TYPE")
    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    @Basic
    @Column(name = "PD_NAME")
    public String getPdName() {
        return pdName;
    }

    public void setPdName(String pdName) {
        this.pdName = pdName;
    }

    @Basic
    @Column(name = "PD_VERSION")
    public String getPdVersion() {
        return pdVersion;
    }

    public void setPdVersion(String pdVersion) {
        this.pdVersion = pdVersion;
    }


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "belongSetting", cascade = CascadeType.REMOVE)
    @org.hibernate.annotations.OrderBy(clause = "to_number(ID) asc")
    public List<CollabSettingsApprovalDetail> getDetails() {
        return details;
    }

    public void setDetails(List<CollabSettingsApprovalDetail> details) {
        this.details = details;
    }
}
