package com.orient.history.core.engine.prepare.impl;

import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.engine.prepare.annotation.PrepareIntermediator;
import com.orient.history.core.request.FrontViewRequest;
import com.orient.history.core.request.ModelDataRequest;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import com.orient.utils.CommonTools;
import org.springframework.context.annotation.Scope;

import java.util.List;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
@PrepareIntermediator(types = {"*"})
@Scope(value = "prototype")
public class CommonPrepareIntermediator extends AbstractPrepareIntermediator {

    @Override
    public void doPrepare(FrontViewRequest frontViewRequest) {
        super.doPrepare(frontViewRequest);
        String taskId = frontViewRequest.getTaskId();
        if (!CommonTools.isEmptyList(frontViewRequest.getModelDataRequestList())) {
            //合并模型数据 防止重复添加
            List<ModelDataRequest> modelDataRequestList = frontViewRequest.getModelDataRequestList();
            HisTaskThreadLocalHolder.put(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_MODELDATA, modelDataRequestList);
        }
        if (!CommonTools.isEmptyList(frontViewRequest.getSysDataRequests())) {
            HisTaskThreadLocalHolder.put(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_SYSDATA, frontViewRequest.getSysDataRequests());
        }
        if (!CommonTools.isEmptyMap(frontViewRequest.getExtraData())) {
            HisTaskThreadLocalHolder.put(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_EXTRADATA, frontViewRequest.getExtraData());
        }
    }
}
