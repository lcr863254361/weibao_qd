package com.orient.collabdev.controller.commit.event;

import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * @Description
 * @Author GNY
 * @Date 2018/9/20 15:54
 * @Version 1.0
 **/
public class CommitProjectParam extends OrientEventParams {

    private String projectNodeId;

    public CommitProjectParam() {
    }

    public CommitProjectParam(String projectNodeId) {
        this.projectNodeId = projectNodeId;
    }

    public String getProjectNodeId() {
        return projectNodeId;
    }

    public void setProjectNodeId(String projectNodeId) {
        this.projectNodeId = projectNodeId;
    }

}
