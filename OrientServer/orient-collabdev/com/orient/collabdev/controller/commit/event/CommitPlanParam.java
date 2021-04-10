package com.orient.collabdev.controller.commit.event;

import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * @Description
 * @Author GNY
 * @Date 2018/9/25 9:08
 * @Version 1.0
 **/
public class CommitPlanParam extends OrientEventParams {

    private String planNodeId;

    public CommitPlanParam() {
    }

    public CommitPlanParam(String planNodeId) {
        this.planNodeId = planNodeId;
    }

    public String getPlanNodeId() {
        return planNodeId;
    }

    public void setPlanNodeId(String planNodeId) {
        this.planNodeId = planNodeId;
    }

}
