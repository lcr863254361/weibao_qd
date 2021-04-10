package com.orient.collab.controller;

import com.orient.collab.business.DataFlowBusiness;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.*;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2016/8/27 0027.
 */
@Controller
@RequestMapping("/DataFlow")
public class DataFlowController extends BaseController {

    @Autowired
    DataFlowBusiness dataFlowBusiness;

    @RequestMapping("/initDataFlow")
    @ResponseBody
    public AjaxResponseData<DataFlowInfo> initDataFlow(String dataId, String modelName) {
        Class cls = CollabConstants.PLAN.endsWith(modelName) ? Plan.class : Task.class;
        List<DataFlowActivity> activities = dataFlowBusiness.getDataFlowActivitys(dataId, cls);
        List<DataFlowTransition> dataFlowTransitions = dataFlowBusiness.getDataFlowTranstitions(dataId, cls);
        DataFlowInfo dataFlowInfo = new DataFlowInfo(dataFlowTransitions, activities);
        AjaxResponseData responseData = new AjaxResponseData<>(dataFlowInfo);
        return responseData;
    }

    @RequestMapping("/saveDataFlow")
    @ResponseBody
    public AjaxResponseData saveDataFlow(@RequestBody DataFlowSyncBean dataFlowInfo){
        dataFlowBusiness.saveDataFlow(dataFlowInfo);
        AjaxResponseData responseData = new AjaxResponseData<>();
        responseData.setMsg("保存成功");
        return responseData;
    }
}
