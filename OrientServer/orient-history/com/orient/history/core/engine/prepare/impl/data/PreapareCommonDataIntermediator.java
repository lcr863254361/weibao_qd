package com.orient.history.core.engine.prepare.impl.data;

import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.engine.prepare.annotation.PrepareIntermediator;
import com.orient.history.core.engine.prepare.impl.AbstractPrepareIntermediator;
import com.orient.history.core.request.FrontViewRequest;
import com.orient.history.core.request.ModelDataRequest;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import com.orient.sysmodel.domain.collab.CollabDataTaskHis;
import com.orient.sysmodel.service.collab.ICollabDataTaskHisService;
import com.orient.utils.CommonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
@PrepareIntermediator(types = {"com.orient.history.core.util.HisTaskTypeConstants$$DATA_TASK"}, order = 1)
@Scope(value = "prototype")
public class PreapareCommonDataIntermediator extends AbstractPrepareIntermediator {

    @Autowired
    ICollabDataTaskHisService collabDataTaskHisService;

    @Override
    public void doPrepare(FrontViewRequest frontViewRequest) {
        super.doPrepare(frontViewRequest);
        String taskId = frontViewRequest.getTaskId();
        //增加任务基本信息
        CollabDataTaskHis collabDataTaskHis = collabDataTaskHisService.getById(taskId);
        ModelDataRequest modelDataRequest = new ModelDataRequest();
        modelDataRequest.setModelId(collabDataTaskHis.getTaskmodelid().toString());
        modelDataRequest.setDataIds(new ArrayList<String>() {{
            add(collabDataTaskHis.getDataid().toString());
        }});
        List<ModelDataRequest> modelDataRequests = HisTaskThreadLocalHolder.get(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_MODELDATA);
        if (CommonTools.isEmptyList(modelDataRequests)) {
            modelDataRequests = new ArrayList<ModelDataRequest>() {{
                add(modelDataRequest);
            }};
        } else {
            modelDataRequests.add(modelDataRequest);
        }
        HisTaskThreadLocalHolder.put(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_MODELDATA, modelDataRequests);
    }
}
