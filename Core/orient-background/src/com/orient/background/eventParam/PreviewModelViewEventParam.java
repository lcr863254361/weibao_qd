package com.orient.background.eventParam;

import com.orient.sysmodel.domain.form.ModelFormViewEntity;
import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * Created by enjoy on 2016/3/23 0023.
 */
public class PreviewModelViewEventParam extends OrientEventParams {

    //事件输入
    private ModelFormViewEntity formValue;
    private Integer reGenTemplate;
    private String dataId;
    //事件输出
    private String outHtml;

    public ModelFormViewEntity getFormValue() {
        return formValue;
    }

    public void setFormValue(ModelFormViewEntity formValue) {
        this.formValue = formValue;
    }

    public Integer getReGenTemplate() {
        return reGenTemplate;
    }

    public void setReGenTemplate(Integer reGenTemplate) {
        this.reGenTemplate = reGenTemplate;
    }

    public String getOutHtml() {
        return outHtml;
    }

    public void setOutHtml(String outHtml) {
        this.outHtml = outHtml;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }
}
