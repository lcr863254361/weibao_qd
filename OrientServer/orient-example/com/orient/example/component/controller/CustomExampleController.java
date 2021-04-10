package com.orient.example.component.controller;

import com.orient.collab.business.CollabPrjModelRelationBusiness;
import com.orient.collab.model.CollabFlowBindDataVO;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.example.component.business.TestBusiness;
import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.GetGridModelDescEvent;
import com.orient.modeldata.eventParam.GetModelDescEventParam;
import com.orient.utils.Log.LogThreadLocalHolder;
import com.orient.utils.StringUtil;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-20 9:10
 */
@Controller
@RequestMapping("/customExampleController")
public class CustomExampleController {

    @Autowired
    ModelDataBusiness modelDataBusiness;

    @Autowired
    CollabPrjModelRelationBusiness collabPrjModelRelationBusiness;

    @Autowired
    TestBusiness testBusiness;

    @RequestMapping("getCollabFLowBindModelDescAndData")
    @ResponseBody
    public AjaxResponseData<Map<String, Object>> getCollabFLowBindModelDescAndData(String flowTaskId, String taskModelId,
                                                                                   String taskId, String templateName) {
        //获取绑定的信息
        CollabFlowBindDataVO collabFlowBindData = collabPrjModelRelationBusiness.getCollabFlowBindData(flowTaskId, taskModelId, taskId, templateName);
        Map<String, Object> retVal = new HashMap<>();
        //获取模型描述
        GetModelDescEventParam getModelDescEventParam = new GetModelDescEventParam(collabFlowBindData.getModelId(), collabFlowBindData.getTemplateId(), null);
        GetGridModelDescEvent getModelDescEvent = new GetGridModelDescEvent(ModelDataController.class, getModelDescEventParam);
        OrientContextLoaderListener.Appwac.publishEvent(getModelDescEvent);
        //获取模型数据
        Map dataMap = modelDataBusiness.getModelDataByModelIdAndDataId(collabFlowBindData.getModelId(), collabFlowBindData.getDataId());
        retVal.put("orientModelDesc", getModelDescEventParam.getOrientModelDesc());
        retVal.put("modelData", dataMap);
        retVal.put("templateId", collabFlowBindData.getTemplateId());
        LogThreadLocalHolder.putParamerter("modelId", collabFlowBindData.getModelId());
        return new AjaxResponseData<>(retVal);
    }

    @RequestMapping("startTestPrj")
    @ResponseBody
    public CommonResponseData createPrjAndBindRelation(Long modelId, Long dataId) {
        String errorMsg = testBusiness.createPrjAndBindRelation(modelId, dataId);
        CommonResponseData retVal = new CommonResponseData();
        retVal.setMsg(StringUtil.isEmpty(errorMsg) ? "启动成功" : errorMsg);
        retVal.setSuccess(StringUtil.isEmpty(errorMsg));
        return retVal;
    }
    @RequestMapping("startTestPrjWithTemplate")
    @ResponseBody
    public CommonResponseData createPrjAndBindRelationWithTemplate(Long modelId, Long dataId) {
        String errorMsg = testBusiness.createPrjAndBindRelationWithTemplate(modelId, dataId);
        CommonResponseData retVal = new CommonResponseData();
        retVal.setMsg(StringUtil.isEmpty(errorMsg) ? "启动成功" : errorMsg);
        retVal.setSuccess(StringUtil.isEmpty(errorMsg));
        return retVal;
    }

}
