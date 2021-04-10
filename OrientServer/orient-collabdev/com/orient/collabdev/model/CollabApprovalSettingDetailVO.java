package com.orient.collabdev.model;

import com.orient.sysmodel.domain.collabdev.approval.CollabSettingsApprovalDetail;
import com.orient.sysmodel.domain.form.ModelFormViewEntity;
import com.orient.utils.BeanUtils;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-07 10:53 AM
 */
public class CollabApprovalSettingDetailVO implements Serializable {


    private String id;
    private String taskName;
    private String formId;
    private String customPath;
    private Long canAssignOther;
    private String optionSettings;
    private String formId_display;

    public static CollabApprovalSettingDetailVO buildFromDTO(CollabSettingsApprovalDetail detail, List<ModelFormViewEntity> modelFormViewEntities) {
        CollabApprovalSettingDetailVO retVal = new CollabApprovalSettingDetailVO();
        BeanUtils.copyProperties(retVal, detail);
        if (null != detail.getFormId()) {
            Predicate<ModelFormViewEntity> filter = modelFormViewEntity -> detail.getFormId().intValue() == modelFormViewEntity.getId();
            if (modelFormViewEntities.stream().filter(filter).count() > 0) {
                ModelFormViewEntity modelFormViewEntity = modelFormViewEntities.stream().filter(filter).findFirst().get();
                retVal.setFormId_display(modelFormViewEntity.getName());
            }
        }
        return retVal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getCustomPath() {
        return customPath;
    }

    public void setCustomPath(String customPath) {
        this.customPath = customPath;
    }

    public Long getCanAssignOther() {
        return canAssignOther;
    }

    public void setCanAssignOther(Long canAssignOther) {
        this.canAssignOther = canAssignOther;
    }

    public String getOptionSettings() {
        return optionSettings;
    }

    public void setOptionSettings(String optionSettings) {
        this.optionSettings = optionSettings;
    }

    public String getFormId_display() {
        return formId_display;
    }

    public void setFormId_display(String formId_display) {
        this.formId_display = formId_display;
    }
}
