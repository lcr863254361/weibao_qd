package com.orient.collab.business;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Task;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sqlengine.extend.ModelDataOperate;
import com.orient.sqlengine.extend.annotation.ModelOperateExtend;
import com.orient.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/24 0024.
 */
@ModelOperateExtend(modelNames = {"CB_TASK"}, schemaName = "协同模型")
public class CBTaskOperator implements ModelDataOperate {

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Override
    public void beforeAdd(IBusinessModel bm, Map<String, String> dataMap) {
        //计划开始时间 与 计划结束时间 拷贝至父节点
        String schemaId = CollabConstants.COLLAB_SCHEMA_ID;
        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);
        String parentTaskId = CollabConstants.TASK + "_" + schemaId + "_ID";
        String parentPlanId = CollabConstants.PLAN + "_" + schemaId + "_ID";
        String startPrefix = "PLANNED_START_DATE_";
        String endPrefix = "PLANNED_END_DATE_";
        if(StringUtil.isEmpty(dataMap.get(startPrefix + taskModelId)) && StringUtil.isEmpty(dataMap.get(endPrefix + taskModelId))){
            if (!StringUtil.isEmpty(dataMap.get(parentTaskId))) {
                Task parentTask = orientSqlEngine.getTypeMappingBmService().getById(Task.class,dataMap.get(parentTaskId));
                dataMap.put(startPrefix + taskModelId,parentTask.getPlannedStartDate());
                dataMap.put(endPrefix + taskModelId,parentTask.getPlannedEndDate());
            } else if (!StringUtil.isEmpty(dataMap.get(parentPlanId))) {
                Plan parentPlan = orientSqlEngine.getTypeMappingBmService().getById(Plan.class,dataMap.get(parentPlanId));
                dataMap.put(startPrefix + taskModelId,parentPlan.getPlannedStartDate());
                dataMap.put(endPrefix + taskModelId,parentPlan.getPlannedEndDate());
            }
        }
    }

    @Override
    public void afterAdd(IBusinessModel bm, Map<String, String> dataMap, String id) {

    }

    @Override
    public void beforeDelete(IBusinessModel bm, String dataIds) {
    }

    @Override
    public void afterDelete(IBusinessModel bm, String dataIds) {
    }

    @Override
    public void beforeDeleteCascade(IBusinessModel bm, String dataIds) {
    }

    @Override
    public void afterDeleteCascade(IBusinessModel bm, String dataIds) {
    }

    @Override
    public void beforeUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId) {
    }

    @Override
    public void afterUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId, Boolean result) {
    }
}
