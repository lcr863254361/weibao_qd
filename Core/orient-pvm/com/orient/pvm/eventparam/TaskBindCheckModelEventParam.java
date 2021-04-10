package com.orient.pvm.eventparam;

import com.orient.sysmodel.domain.pvm.CheckModelDataTemplate;
import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * Created by mengbin on 16/7/30.
 * Purpose:
 * Detail:
 */
public class TaskBindCheckModelEventParam extends OrientEventParams {


    public String nodeId;
    /**
     * DS模型Id
     */
    public String checkModelId;

    private String checkType;

    private String html;

    private String htmlName;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 模型绑定的模版
     */
    public CheckModelDataTemplate checkModelDataTemplate;

    public TaskBindCheckModelEventParam() {

    }

    public TaskBindCheckModelEventParam(String nodeId, String checkModelId, String html, String htmlName, int status) {
        this.nodeId = nodeId;
        this.checkModelId = checkModelId;
        this.html = html;
        this.htmlName = htmlName;
        this.status = status;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getCheckModelId() {
        return checkModelId;
    }

    public void setCheckModelId(String checkModelId) {
        this.checkModelId = checkModelId;
    }

    public CheckModelDataTemplate getCheckModelDataTemplate() {
        return checkModelDataTemplate;
    }

    public void setCheckModelDataTemplate(CheckModelDataTemplate checkModelDataTemplate) {
        this.checkModelDataTemplate = checkModelDataTemplate;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHtmlName() {
        return htmlName;
    }

    public void setHtmlName(String htmlName) {
        this.htmlName = htmlName;
    }
}
