package com.orient.history.core.engine;

import com.orient.history.core.IHisTaskEngine;
import com.orient.history.core.binddata.model.HisTaskInfo;
import com.orient.history.core.builder.IHisTaskBindDataBuilder;
import com.orient.history.core.engine.prepare.IPrepareIntermediator;
import com.orient.history.core.handlerchain.ConstructPreparerChain;
import com.orient.history.core.request.FrontViewRequest;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public abstract class AbstractHisTaskEngine implements IHisTaskEngine {

    @Autowired
    protected IHisTaskBindDataBuilder hisTaskBindDataBuilder;

    @Autowired
    protected ConstructPreparerChain constructPreparerChain;

    public HisTaskInfo saveHisTaskInfo(String taskId) {
        prepareIntermediateData(taskId);
        return doSaveHisTaskInfo(taskId);
    }

    public void prepareIntermediateData(String taskId) {
        FrontViewRequest frontViewRequest = HisTaskThreadLocalHolder.get(taskId + HisTaskConstants.FRONT_REQUEST_KEY);
        //构建准备器
        IPrepareIntermediator prepareIntermediator = constructPreparerChain.getPrepareChain(frontViewRequest.getTaskType());
        //数据预处理
        prepareIntermediator.doPrepare(frontViewRequest);
        //卸载前端数据
        HisTaskThreadLocalHolder.removeKey(taskId + HisTaskConstants.FRONT_REQUEST_KEY);
    }

    public abstract HisTaskInfo doSaveHisTaskInfo(String taskId);

}
