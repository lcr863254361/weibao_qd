package com.orient.history.core.builder.director;

import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.binddata.model.TaskBindData;
import com.orient.history.core.builder.IHisTaskBindDataBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/4 0004.
 */
public class BuildHisTaskBindDataDirector implements IBuildHisTaskBindDataDirector {

    private String taskId;

    public BuildHisTaskBindDataDirector(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public List<TaskBindData> doBuild(IHisTaskBindDataBuilder builder) {
        List<TaskBindData> taskBindDatas = new ArrayList<>();
        IBindDataHandler bindDataHandler = builder.getHandlerChain(taskId);
        builder.constructBindData(bindDataHandler, taskId, taskBindDatas);
        return taskBindDatas;
    }
}
