package com.orient.component.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class ValidateComponentBean implements Serializable {

    //Component 输出数据 采用json格式描述
    private String jsonComponent;
    //组件所属任务 or 计划 or 项目节点ID
    private String belongNodeId;
    //如果所属模型为任务
    private String flowTaskId;
    //组件ID
    private Long componentId;

    public String getJsonComponent() {
        return jsonComponent;
    }

    public void setJsonComponent(String jsonComponent) {
        this.jsonComponent = jsonComponent;
    }

    public String getBelongNodeId() {
        return belongNodeId;
    }

    public void setBelongNodeId(String belongNodeId) {
        this.belongNodeId = belongNodeId;
    }

    public String getFlowTaskId() {
        return flowTaskId;
    }

    public void setFlowTaskId(String flowTaskId) {
        this.flowTaskId = flowTaskId;
    }

    public Long getComponentId() {
        return componentId;
    }

    public void setComponentId(Long componentId) {
        this.componentId = componentId;
    }
}
