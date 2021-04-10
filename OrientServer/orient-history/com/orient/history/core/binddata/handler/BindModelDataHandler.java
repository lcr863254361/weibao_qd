package com.orient.history.core.binddata.handler;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.history.core.annotation.HisTaskHandler;
import com.orient.history.core.binddata.model.BindModelData;
import com.orient.history.core.binddata.model.TaskBindData;
import com.orient.history.core.request.ModelDataRequest;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.GetGridModelDescEvent;
import com.orient.modeldata.eventParam.GetModelDescEventParam;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.modelDesc.model.OrientModelDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
@HisTaskHandler(types = {IBindDataHandler.BIND_TYPE_MODELDATA})
@Scope(value = "prototype")
public class BindModelDataHandler extends AbstractBindDataHandler {

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    protected IBusinessModelService businessModelService;


    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void constructBindData(String taskId, List<TaskBindData> taskBindDatas) {
        super.constructBindData(taskId, taskBindDatas);
        List<ModelDataRequest> modelDataRequests = HisTaskThreadLocalHolder.get(taskId + HisTaskConstants.TASK_BIND_MID + IBindDataHandler.BIND_TYPE_MODELDATA);
        if (!CommonTools.isEmptyList(modelDataRequests)) {
            modelDataRequests.forEach(modelDataRequest -> {
                String modelId = modelDataRequest.getModelId();
                if (StringUtil.isNumberic(modelId)) {
                    IBusinessModel businessModel = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
                    GetModelDescEventParam getModelDescEventParam = new GetModelDescEventParam(modelId, modelDataRequest.getTemplateId(), "");
                    GetGridModelDescEvent getModelDescEvent = new GetGridModelDescEvent(ModelDataController.class, getModelDescEventParam);
                    applicationContext.publishEvent(getModelDescEvent);
                    OrientModelDesc orientModelDesc = getModelDescEventParam.getOrientModelDesc();
                    String modelDescStr = null == orientModelDesc ? "" : net.sf.json.JSONObject.fromObject(orientModelDesc).toString();
                    BindModelData bindModelData = new BindModelData();
                    bindModelData.setModelId(modelDataRequest.getModelId());
                    bindModelData.setModelDesc(modelDescStr);
                    if (!CommonTools.isEmptyList(modelDataRequest.getDataIds())) {
                        businessModel.appendCustomerFilter(new CustomerFilter("ID", EnumInter.SqlOperation.In, CommonTools.list2String(modelDataRequest.getDataIds())));
                        List<Map> dataList = orientSqlEngine.getBmService().createModelQuery(businessModel).list();
                        businessModelService.dataChangeModel(orientSqlEngine, businessModel, dataList, false);
                        modelDataRequest.getDataList().addAll(dataList);
                    }
                    bindModelData.setExtraData(modelDataRequest.getExtraData());
                    bindModelData.setModelDataList(modelDataRequest.getDataList());
                    taskBindDatas.add(bindModelData);
                }
            });
        }
    }
}
