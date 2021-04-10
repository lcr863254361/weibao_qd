package com.orient.collabdev.controller.startup.event;

import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * @Description
 * @Author GNY
 * @Date 2018/9/17 14:04
 * @Version 1.0
 **/
public class StartUpProjectParam extends OrientEventParams {

    private String projectNodeId;

    public StartUpProjectParam() {
    }

    public StartUpProjectParam(String projectNodeId) {
        this.projectNodeId = projectNodeId;
    }

    public String getProjectNodeId() {
        return projectNodeId;
    }

    public void setProjectNodeId(String projectNodeId) {
        this.projectNodeId = projectNodeId;
    }

}
