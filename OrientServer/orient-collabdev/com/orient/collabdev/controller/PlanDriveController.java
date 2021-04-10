package com.orient.collabdev.controller;

import com.orient.collabdev.controller.commit.event.CommitPlanEvent;
import com.orient.collabdev.controller.commit.event.CommitPlanParam;
import com.orient.collabdev.controller.startup.event.ManualStartUpPlanEvent;
import com.orient.collabdev.controller.startup.event.ManualStartUpPlanParam;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 计划的提交
 * @Author GNY
 * @Date 2018/9/25 9:06
 * @Version 1.0
 **/
@Controller
@RequestMapping("/planDrive")
public class PlanDriveController extends BaseController {

    /**
     * 计划提交
     *
     * @param planNodeId 计划节点id
     * @return
     */
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @RequestMapping("/submit")
    @ResponseBody
    public CommonResponseData submitPlan(String planNodeId) {
        CommonResponseData retVal = new CommonResponseData();
        CommitPlanParam param = new CommitPlanParam(planNodeId);
        CommitPlanEvent event = new CommitPlanEvent(this, param);
        OrientContextLoaderListener.Appwac.publishEvent(event);
        retVal.setMsg("工作包提交成功");
        return retVal;
    }

    /**
     * 强制启动计划
     *
     * @param planNodeId 计划节点id
     * @return
     */
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @RequestMapping("/startup")
    @ResponseBody
    public CommonResponseData startPlan(String planNodeId) {
        CommonResponseData retVal = new CommonResponseData();
        ManualStartUpPlanParam param = new ManualStartUpPlanParam(planNodeId);
        ManualStartUpPlanEvent event = new ManualStartUpPlanEvent(this, param);
        OrientContextLoaderListener.Appwac.publishEvent(event);
        retVal.setMsg("强制启动工作包成功");
        return retVal;
    }

}
