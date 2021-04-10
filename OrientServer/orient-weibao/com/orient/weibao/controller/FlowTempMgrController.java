package com.orient.weibao.controller;

import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.weibao.business.FlowTempMgrBusiness;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-05-23 14:19
 */
@Controller
@RequestMapping("/FlowTempMgr")
public class FlowTempMgrController extends BaseController{

    @Autowired
    FlowTempMgrBusiness flowTempMgrBusiness;

    /**
     * 获取流程模板类型列表
     * @return
     */
    @RequestMapping("queryFlowTempTypeList")
    @ResponseBody
    public JSONObject queryFlowTempTypeList() {
        List<Map> flowTempTypeList = flowTempMgrBusiness.queryFlowTempTypeList();
        JSONArray jsonArray = JSONArray.fromObject(flowTempTypeList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("results", jsonArray);
        return jsonObject;
    }

    /**
     * 添加流程模板类型数据
     * @param modelId
     * @param formData
     * @return
     */
    @RequestMapping("addFlowTempTypeData")
    @ResponseBody
    public AjaxResponseData addFlowTempTypeData(String modelId, String formData) {
        return flowTempMgrBusiness.addFlowTempTypeData(modelId, formData);
    }

    /**
     * 修改流程模板类型数据
     * @param modelId
     * @param flowTempTypeId
     * @param formData
     * @return
     */
    @RequestMapping("updateFlowTempTypeData")
    @ResponseBody
    public AjaxResponseData updateFlowTempTypeData(String modelId, String flowTempTypeId, String formData) {
        return flowTempMgrBusiness.updateFlowTempTypeData(modelId, flowTempTypeId, formData);
    }

    /**
     * 删除流程模板类型数据
     * @param flowTempTypeId
     * @return
     */
    @RequestMapping("delFlowTempTypeById")
    @ResponseBody
    public AjaxResponseData delFlowTempTypeById(String flowTempTypeId) {
        AjaxResponseData retVal = new AjaxResponseData();
        flowTempMgrBusiness.delFlowTempTypeById(flowTempTypeId);
        retVal.setSuccess(true);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
