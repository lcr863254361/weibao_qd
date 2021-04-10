package com.orient.history.core.engine.prepare.impl.collab;

import com.orient.flow.business.FlowTaskBusiness;
import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.engine.prepare.annotation.PrepareIntermediator;
import com.orient.history.core.engine.prepare.impl.AbstractPrepareIntermediator;
import com.orient.history.core.request.FrontViewRequest;
import com.orient.history.core.request.WorkFlowFrontViewRequest;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import org.jbpm.pvm.internal.history.model.HistoryTaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
@PrepareIntermediator(types = {"com.orient.history.core.util.HisTaskTypeConstants$$COLLAB_TASK"}, order = 10)
@Scope(value = "prototype")
public class PreapareCommonCollabIntermediator extends AbstractPrepareIntermediator {

    @Autowired
    FlowTaskBusiness flowTaskBusiness;

    @Override
    public void doPrepare(FrontViewRequest frontViewRequest) {
        super.doPrepare(frontViewRequest);
        WorkFlowFrontViewRequest workFlowFrontViewRequest = (WorkFlowFrontViewRequest)frontViewRequest;
        String taskId = frontViewRequest.getTaskId();
        //获取任务描述
        HistoryTaskImpl task = (HistoryTaskImpl) flowTaskBusiness.getHistoryTaskById(taskId);
        HisTaskThreadLocalHolder.put(taskId + HisTaskConstants.TASK_DESC_KEY, task);
        //控制流信息
        HisTaskThreadLocalHolder.put(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_CONTROLFLOWDATA, workFlowFrontViewRequest.getPiId());

    }
}
