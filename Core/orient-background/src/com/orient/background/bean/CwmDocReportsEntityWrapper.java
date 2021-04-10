package com.orient.background.bean;

import com.orient.sysmodel.domain.doc.CwmDocReportsEntity;
import com.orient.utils.BeanUtils;

public class CwmDocReportsEntityWrapper extends CwmDocReportsEntity {

    //model display name
    private String modelId_display;

    public CwmDocReportsEntityWrapper(CwmDocReportsEntity cwmDocReportsEntity) {
        BeanUtils.copyProperties(this, cwmDocReportsEntity);
    }

    public String getModelId_display() {
        return modelId_display;
    }

    public void setModelId_display(String modelId_display) {
        this.modelId_display = modelId_display;
    }
}
