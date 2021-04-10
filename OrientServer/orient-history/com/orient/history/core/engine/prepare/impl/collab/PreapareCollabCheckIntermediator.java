package com.orient.history.core.engine.prepare.impl.collab;

import com.alibaba.fastjson.JSONArray;
import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.engine.prepare.annotation.PrepareIntermediator;
import com.orient.history.core.engine.prepare.impl.AbstractPrepareIntermediator;
import com.orient.history.core.request.FrontViewRequest;
import com.orient.history.core.request.ModelDataRequest;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import com.orient.sysmodel.domain.pvm.TaskCheckModel;
import com.orient.sysmodel.domain.pvm.TaskCheckRelation;
import com.orient.sysmodel.service.pvm.ITaskCheckModelService;
import com.orient.sysmodel.service.pvm.ITaskCheckRelationService;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
@PrepareIntermediator(types = {"com.orient.history.core.util.HisTaskTypeConstants$$COLLAB_TASK"}, order = 30)
@Scope(value = "prototype")
public class PreapareCollabCheckIntermediator extends AbstractPrepareIntermediator {

    @Autowired
    ITaskCheckModelService taskCheckModelService;

    @Autowired
    ITaskCheckRelationService taskCheckRelationService;

    @Override
    public void doPrepare(FrontViewRequest frontViewRequest) {
        super.doPrepare(frontViewRequest);
        String taskId = frontViewRequest.getTaskId();
        Map<String, String> extraMap = frontViewRequest.getExtraData();
        if (!StringUtil.isEmpty(extraMap.get("checkModelIds"))) {
            String checkModelIdsJsonStr = extraMap.get("checkModelIds");
            List<Long> checkModelIds = JSONArray.parseArray(checkModelIdsJsonStr, Long.class);
            List<TaskCheckModel> taskCheckModels = taskCheckModelService.list(Restrictions.in("id", checkModelIds));
            //获取待存储的模型数据集合
            List<ModelDataRequest> modelDataRequests = HisTaskThreadLocalHolder.get(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_MODELDATA);
            modelDataRequests = CommonTools.isEmptyList(modelDataRequests) ? new ArrayList<>() : modelDataRequests;
            final List<ModelDataRequest> finalModelDataRequests = modelDataRequests;
            taskCheckModels.forEach(taskCheckModel -> {
                //获取模型描述
                Long checkModelId = taskCheckModel.getCheckmodelid();
                if (null != checkModelId) {
                    /*//获取绑定任务模型ID
                    Long taskModelId = taskCheckModel.getTaskmodelid();
                    //获取绑定任务ID
                    Long taskDataId = taskCheckModel.getTaskdataid();
                    List<TaskCheckRelation> taskCheckRelations = taskCheckRelationService.list(Restrictions.eq("checkmodelid", checkModelId), Restrictions.eq("taskmodelid", taskModelId), Restrictions.eq("taskdataid", taskDataId));
                    //获取模型绑定的数据信息
                    List<String> dataIds = taskCheckRelations.stream().map(taskCheckRelation -> taskCheckRelation.getCheckdataid().toString()).collect(Collectors.toList());
                    ModelDataRequest modelDataRequest = new ModelDataRequest();
                    modelDataRequest.setDataIds(dataIds);
                    modelDataRequest.setModelId(checkModelId.toString());
                    modelDataRequest.getExtraData().put("checkModelId", taskCheckModel.getId().toString());
                    finalModelDataRequests.add(modelDataRequest);*/
                }
            });
            //绑定至线程变量中
            HisTaskThreadLocalHolder.put(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_MODELDATA, modelDataRequests);
        }
    }
}
