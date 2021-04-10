package com.orient.background.bean;

import com.orient.sysmodel.domain.flow.AuditFlowTaskSettingEntity;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class AuditFlowTaskSettingEntityWrapper extends AuditFlowTaskSettingEntity {

    private String formId_display;

    public String getFormId_display() {
        return formId_display;
    }

    public void setFormId_display(String formId_display) {
        this.formId_display = formId_display;
    }
}
