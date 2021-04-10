package com.orient.sysmodel.domain.collabdev.approval;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description 数据质量-数据审批设置详细表
 * @Author GNY
 * @Date 2018/7/27 9:33
 * @Version 1.0
 **/
@Entity
@Table(name = "CB_SETTINGS_APPROVAL_DETAIL")
public class CollabSettingsApprovalDetail {

    private String id;
    private String taskName;
    private Long formId;
    private String customPath;
    private Long canAssignOther;
    private String optionSettings;
    private CollabSettingsApproval belongSetting;


    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CB_APPROVAL_DETAIL")})
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Basic
    @Column(name = "TASK_NAME")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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
    @Column(name = "OPTION_SETTINGS")
    public String getOptionSettings() {
        return optionSettings;
    }

    public void setOptionSettings(String optionSettings) {
        this.optionSettings = optionSettings;
    }


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CB_SETTINGS_APPROVAL_ID")
    public CollabSettingsApproval getBelongSetting() {
        return belongSetting;
    }

    public void setBelongSetting(CollabSettingsApproval belongSetting) {
        this.belongSetting = belongSetting;
    }
}
