package com.orient.collabdev.controller;

import com.orient.collabdev.controller.commit.event.CommitProjectEvent;
import com.orient.collabdev.controller.commit.event.CommitProjectParam;
import com.orient.collabdev.controller.startup.event.StartUpProjectEvent;
import com.orient.collabdev.controller.startup.event.StartUpProjectParam;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 项目启动和提交的相关代码
 * @Author GNY
 * @Date 2018/9/17 10:51
 * @Version 1.0
 **/
@Controller
@RequestMapping("/projectDrive")
public class ProjectDriveController extends BaseController {

    /**
     * 项目启动
     *
     * @param projectNodeId 项目节点id
     * @return
     */
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @RequestMapping("/start")
    @ResponseBody
    public CommonResponseData startProject(String projectNodeId) {
        CommonResponseData retVal = new CommonResponseData();
        StartUpProjectParam param = new StartUpProjectParam(projectNodeId);
        StartUpProjectEvent event = new StartUpProjectEvent(this, param);
        OrientContextLoaderListener.Appwac.publishEvent(event);
        retVal.setMsg("项目启动成功");
        return retVal;
    }

    /**
     * 项目提交：判断条件是项目下所有的计划的状态都是已完成
     *
     * @param projectNodeId 项目节点id
     * @return
     */
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    @RequestMapping("/submit")
    @ResponseBody
    public CommonResponseData submitProject(String projectNodeId) {
        CommonResponseData retVal = new CommonResponseData();
        CommitProjectParam param = new CommitProjectParam(projectNodeId);
        CommitProjectEvent event = new CommitProjectEvent(this, param);
        OrientContextLoaderListener.Appwac.publishEvent(event);
        retVal.setMsg("项目提交成功");
        return retVal;
    }

}
