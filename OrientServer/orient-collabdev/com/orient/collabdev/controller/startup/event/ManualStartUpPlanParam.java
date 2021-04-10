package com.orient.collabdev.controller.startup.event;

import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * @Description
 * @Author GNY
 * @Date 2018/9/17 14:04
 * @Version 1.0
 **/
public class ManualStartUpPlanParam extends OrientEventParams {

    private String planNodeId;

    public ManualStartUpPlanParam() {
    }

    public ManualStartUpPlanParam(String planNodeId) {
        this.planNodeId = planNodeId;
    }

    public String getPlanNodeId() {
        return planNodeId;
    }

    public void setPlanNodeId(String planNodeId) {
        this.planNodeId = planNodeId;
    }

}
