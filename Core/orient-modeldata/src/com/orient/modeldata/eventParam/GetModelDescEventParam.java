package com.orient.modeldata.eventParam;

import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEventParams;
import com.orient.web.modelDesc.model.OrientModelDesc;

/**
 * 获取模型描述事件传递参数
 *
 * @author enjoy
 * @creare 2016-04-01 14:46
 */
public class GetModelDescEventParam extends OrientEventParams {
    //输入
    private String modelId;

    private String templateId;

    private String isView;

    //输出
    private OrientModelDesc orientModelDesc;

    public GetModelDescEventParam(String modelId, String templateId, String isView) {
        super();
        this.modelId = modelId;
        this.templateId = templateId;
        this.isView = StringUtil.isEmpty(isView) ? "0" : isView;
    }

    public String getIsView() {
        return isView;
    }

    public void setIsView(String isView) {
        this.isView = isView;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public OrientModelDesc getOrientModelDesc() {
        return orientModelDesc;
    }

    public void setOrientModelDesc(OrientModelDesc orientModelDesc) {
        this.orientModelDesc = orientModelDesc;
    }
}
