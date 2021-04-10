package com.orient.devdataobj.event.listener;

import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Task;
import com.orient.devdataobj.business.DataObjectBusiness;
import com.orient.devdataobj.event.GetDataObjEvent;
import com.orient.devdataobj.event.param.GetDataObjParam;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventCheck;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by mengbin on 16/8/6.
 * Purpose:
 * Detail:
 */
@Component
public class GetParentBindDataObjListener extends OrientEventListener {


    @Autowired
    private DataObjectBusiness dataObjectBusiness;


    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    protected IBusinessModelService businessModelService;

    @Override
    @OrientEventCheck()
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {

        //该监听器能够处理对应事件及其子类事件
        return GetDataObjEvent.class == aClass || GetDataObjEvent.class.isAssignableFrom(aClass);

    }


    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (this.isAbord(applicationEvent)) {
            return;
        }

        GetDataObjParam param = (GetDataObjParam) ((OrientEvent) applicationEvent).getParams();
        if (param.globalFlag == 2) {
            return;
        }
        //todo 需要重新修改下面的代码
       /* String planModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Plan.class);
        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);
        if (param.modelId.equals(planModelId)) {

            Plan orientPlan = orientSqlEngine.getTypeMappingBmService().getById(Plan.class, param.dataId);
            String parentDataId = orientPlan.getParPlanId();
            if (!StringUtil.isEmpty(parentDataId)) {
                List<DataObjectEntity> objs = dataObjectBusiness.getAllCurrentDataObject(param.modelId, parentDataId, param.globalFlag, true);
                List<DataObjectEntity> savedObjs = (List<DataObjectEntity>) param.getFlowParams("DataObjEcntitys");
                savedObjs.addAll(objs);
                param.setFlowParams("DataObjEcntitys", savedObjs);
            }

        } else {

            Task orientTask = orientSqlEngine.getTypeMappingBmService().getById(Task.class, param.dataId);
            String parentTaskId = orientTask.getParTaskId();
            String parentPlanId = orientTask.getParPlanId();
            if (StringUtil.isEmpty(parentTaskId)) {
                // 如果父任务为空,则获取所属计划的数据
                List<DataObjectEntity> objs = dataObjectBusiness.getAllCurrentDataObject(planModelId, parentPlanId, param.globalFlag, true);
                List<DataObjectEntity> savedObjs = (List<DataObjectEntity>) param.getFlowParams("DataObjEcntitys");
                savedObjs.addAll(objs);
                param.setFlowParams("DataObjEcntitys", savedObjs);
            } else {
                List<DataObjectEntity> objs = dataObjectBusiness.getAllCurrentDataObject(taskModelId, parentTaskId, param.globalFlag, true);
                List<DataObjectEntity> savedObjs = (List<DataObjectEntity>) param.getFlowParams("DataObjEcntitys");
                savedObjs.addAll(objs);
                param.setFlowParams("DataObjEcntitys", savedObjs);
            }
        }*/
    }

}

