package com.orient.history.core.engine.impl;

import com.orient.collab.model.Plan;
import com.orient.history.core.binddata.model.HisTaskInfo;
import com.orient.history.core.binddata.model.TaskBindData;
import com.orient.history.core.builder.director.BuildHisTaskBindDataDirector;
import com.orient.history.core.engine.AbstractHisTaskEngine;
import com.orient.history.core.util.HisTaskHelper;
import com.orient.sqlengine.api.ISqlEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
@Component
public class HisPlanTaskEngine extends AbstractHisTaskEngine {

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Override
    public HisTaskInfo doSaveHisTaskInfo(String taskId) {

        HisTaskInfo taskInfo = new HisTaskInfo();
        Plan plan = orientSqlEngine.getTypeMappingBmService().getById(Plan.class, taskId);
        //初始化基本信息
        HisTaskHelper.getInstance().initPlanTaskBaseInfo(plan, taskInfo);
        BuildHisTaskBindDataDirector buildHisTaskBindDataDirector = new BuildHisTaskBindDataDirector(taskId);
        List<TaskBindData> taskBindDatas = buildHisTaskBindDataDirector.doBuild(hisTaskBindDataBuilder);
        taskInfo.setTaskBindDataList(taskBindDatas);
        //保存至数据库
        return taskInfo;
    }
}
