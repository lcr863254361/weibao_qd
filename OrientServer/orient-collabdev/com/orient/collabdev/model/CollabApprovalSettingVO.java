package com.orient.collabdev.model;

import com.orient.sysmodel.domain.collabdev.approval.CollabSettingsApproval;
import com.orient.utils.BeanUtils;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-07 10:53 AM
 */
public class CollabApprovalSettingVO implements Serializable {

    public static CollabApprovalSettingVO buildFromDTO(CollabSettingsApproval collabSettingsApproval) {
        CollabApprovalSettingVO collabApprovalSettingVO = new CollabApprovalSettingVO();
        BeanUtils.copyProperties(collabApprovalSettingVO, collabSettingsApproval);
        //枚举转化
        return collabApprovalSettingVO;
    }

    public CollabSettingsApproval converToDTO() {
        CollabSettingsApproval collabSettingsApproval = new CollabSettingsApproval();
        BeanUtils.copyProperties(collabSettingsApproval, this);
        return collabSettingsApproval;
    }

    private String id;
    private String approvalType;
    private String settingsTarget;
    private String triggerType;
    private String pdName;
    private String pdVersion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }

    public String getSettingsTarget() {
        return settingsTarget;
    }

    public void setSettingsTarget(String settingsTarget) {
        this.settingsTarget = settingsTarget;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getPdName() {
        return pdName;
    }

    public void setPdName(String pdName) {
        this.pdName = pdName;
    }

    public String getPdVersion() {
        return pdVersion;
    }

    public void setPdVersion(String pdVersion) {
        this.pdVersion = pdVersion;
    }
}
