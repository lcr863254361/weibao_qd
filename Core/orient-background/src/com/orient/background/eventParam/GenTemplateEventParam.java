package com.orient.background.eventParam;

import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * Created by enjoy on 2016/3/21 0021.
 */
public class GenTemplateEventParam extends OrientEventParams {

    private Long modelId;

    private Long templateId;

    private String html;

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
