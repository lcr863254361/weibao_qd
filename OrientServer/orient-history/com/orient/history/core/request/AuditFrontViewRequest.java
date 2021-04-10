package com.orient.history.core.request;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class AuditFrontViewRequest extends WorkFlowFrontViewRequest {

    //由于重新组织html成本高 故从前端准备 传入后端
    private Map<String, List<String>> modelFreemarkerHtml;

    private Map<String, String> opinions;

    public Map<String, String> getOpinions() {
        return opinions;
    }

    public void setOpinions(Map<String, String> opinions) {
        this.opinions = opinions;
    }

    public Map<String, List<String>> getModelFreemarkerHtml() {
        return modelFreemarkerHtml;
    }

    public void setModelFreemarkerHtml(Map<String, List<String>> modelFreemarkerHtml) {
        this.modelFreemarkerHtml = modelFreemarkerHtml;
    }
}
