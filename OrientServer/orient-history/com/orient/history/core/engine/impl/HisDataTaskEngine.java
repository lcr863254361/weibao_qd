package com.orient.history.core.engine.impl;

import com.orient.collab.model.Task;
import com.orient.history.core.binddata.model.HisTaskInfo;
import com.orient.history.core.binddata.model.TaskBindData;
import com.orient.history.core.builder.director.BuildHisTaskBindDataDirector;
import com.orient.history.core.engine.AbstractHisTaskEngine;
import com.orient.history.core.util.HisTaskHelper;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.collab.CollabDataTaskHis;
import com.orient.sysmodel.service.collab.ICollabDataTaskHisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
@Component
public class HisDataTaskEngine extends AbstractHisTaskEngine {

    @Autowired
    ICollabDataTaskHisService collabDataTaskHisService;

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Override
    public HisTaskInfo doSaveHisTaskInfo(String taskId) {
        HisTaskInfo taskInfo = new HisTaskInfo();
        CollabDataTaskHis collabDataTaskHis = collabDataTaskHisService.getById(taskId);
        String dataId = collabDataTaskHis.getDataid().toString();
        Task task = orientSqlEngine.getTypeMappingBmService().getById(Task.class, dataId);
        //初始化基本信息
        HisTaskHelper.getInstance().initDataTaskBaseInfo(task, taskInfo, collabDataTaskHis);
        BuildHisTaskBindDataDirector buildHisTaskBindDataDirector = new BuildHisTaskBindDataDirector(taskId);
        List<TaskBindData> taskBindDatas = buildHisTaskBindDataDirector.doBuild(hisTaskBindDataBuilder);
        taskInfo.setTaskBindDataList(taskBindDatas);
        //保存至数据库
        return taskInfo;
    }
}
