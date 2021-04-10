package com.orient.history.core.engine.impl;

import com.orient.flow.business.FlowTaskBusiness;
import com.orient.history.core.binddata.model.HisTaskInfo;
import com.orient.history.core.binddata.model.TaskBindData;
import com.orient.history.core.builder.director.BuildHisTaskBindDataDirector;
import com.orient.history.core.engine.AbstractHisTaskEngine;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskHelper;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import com.orient.history.core.util.HisTaskTypeConstants;
import org.jbpm.pvm.internal.history.model.HistoryTaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
@Component
public class HisAuditTaskEngine extends AbstractHisTaskEngine {

    @Autowired
    FlowTaskBusiness flowTaskBusiness;

    @Override
    public HisTaskInfo doSaveHisTaskInfo(String taskId) {
        HisTaskInfo taskInfo = new HisTaskInfo();
        HistoryTaskImpl task = HisTaskThreadLocalHolder.get(taskId + HisTaskConstants.TASK_DESC_KEY);
        //初始化基本信息
        String taskName = flowTaskBusiness.getHistoryActivityName(task.getId());
        HisTaskHelper.getInstance().initWorkFlowTaskBaseInfo(task, taskInfo, HisTaskTypeConstants.AUDIT_TASK, taskName);
        BuildHisTaskBindDataDirector buildHisTaskBindDataDirector = new BuildHisTaskBindDataDirector(taskId);
        List<TaskBindData> taskBindDatas = buildHisTaskBindDataDirector.doBuild(hisTaskBindDataBuilder);
        taskInfo.setTaskBindDataList(taskBindDatas);
        //保存至数据库
        return taskInfo;
    }
}
