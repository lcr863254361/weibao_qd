package com.orient.weibao.controller;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.BusinessModelServiceImpl;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.event.UpdateModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.CommonTools;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.weibao.business.InformMgrBusiness;
import com.orient.weibao.business.SparePartsMgrBusiness;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.dto.DivingTaskNameWithInformLog;
import com.orient.weibao.mbg.model.DivingPlanTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-05-28 15:29
 */
@Controller
@RequestMapping("/informMgr")
public class InformMgrController extends BaseController{

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    @Autowired
    InformMgrBusiness informMgrBusiness;
    @Autowired
    BusinessModelServiceImpl businessModelService;
    @Autowired
    ISqlEngine orientSqlEngine;
    @Autowired
    SparePartsMgrBusiness sparePartsMgrBusiness;

    /**
     * 新增通知信息到数据库中
     * @param modelId
     * @param formData
     * @return
     */
    @RequestMapping("saveInformMgrData")
    @ResponseBody
    public AjaxResponseData<String> saveInformMgrData(String modelId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            SimpleDateFormat publishDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dataMap.put("C_PUBLISH_TIME_"+modelId,publishDate.format(new Date()));
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            eventParam.setCreateData(true);
            OrientContextLoaderListener.Appwac.publishEvent(new SaveModelDataEvent(ModelDataController.class, eventParam));
            retVal.setMsg("保存成功");
            return retVal;
        }
    }

    /**
     * 修改通知管理部门数据
     * @param modelId
     * @param formData
     * @return
     */
    @RequestMapping("updateInformMgrData")
    @ResponseBody
    public AjaxResponseData<String> updateInformMgrData(String modelId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        IBusinessModel hisInformBM= businessModelService.getBusinessModelBySName(PropertyConstant.HIS_INFORM_MGR,schemaId,EnumInter.BusinessModelEnum.Table);
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
           String informId=(String)dataMap.get("ID");
            String departMent=(String)dataMap.get("C_DEPARTMENT_"+modelId);
            hisInformBM.setReserve_filter("AND T_INFORM_MGR_"+schemaId+"_ID='"+informId+"'");
            List<Map> hisInformList=orientSqlEngine.getBmService().createModelQuery(hisInformBM).list();
            if (hisInformList.size()>0){
                for (Map hisMap:hisInformList){
                    String hisInformId=(String)hisMap.get("ID");
                    hisMap.put("C_NAME_"+hisInformBM.getId(),departMent);
                    orientSqlEngine.getBmService().updateModelData(hisInformBM,hisMap,hisInformId);
                }
            }
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            OrientContextLoaderListener.Appwac.publishEvent(new UpdateModelDataEvent(ModelDataController.class, eventParam));

            retVal.setSuccess(true);
            retVal.setMsg("保存成功");
            return retVal;
        }
    }

    /**
     * 修改通知内容
     * @param informId
     * @param informContent
     * @param newInformContent
     * @return
     */
    @RequestMapping("updateInformContent")
    @ResponseBody
    public AjaxResponseData  updateInformContent(String informId,String informContent,String newInformContent){
        return  informMgrBusiness.updateInformContent(informId, informContent, newInformContent);
    }

    /**
     * 保存通知状态
     * @param checkStateName
     */
    @RequestMapping("saveInformState")
    @ResponseBody
    public void  saveInformState(String checkStateName){
       informMgrBusiness.saveInformState(checkStateName);
    }

    @RequestMapping("getInformState")
    @ResponseBody
    public AjaxResponseData  getInformState(){
       return informMgrBusiness.getInformState();
    }

    @RequestMapping("notice")
    @ResponseBody
    public AjaxResponseData getNotice(){
        List<Map<String, Object>> infoLogList = informMgrBusiness.getNoticeInfo();
        AjaxResponseData ajaxResponseData = new AjaxResponseData(infoLogList);
        return ajaxResponseData;
    }
    @RequestMapping("currentHangduanNotice")
    @ResponseBody
    public AjaxResponseData currentHangduanNotice(boolean isOnlyShowPlan){
        List<DivingTaskNameWithInformLog> infoLogList = informMgrBusiness.getNoticeCurrentHangduanInfo(isOnlyShowPlan);
        AjaxResponseData ajaxResponseData = new AjaxResponseData(infoLogList);
        return ajaxResponseData;
    }

    @RequestMapping("getCurrentHangduanFlowPost")
    @ResponseBody
    public AjaxResponseData getCurrentHangduanFlowPost(){
        Map flowPostMap = informMgrBusiness.getCurrentHangduanFlowPost();
        AjaxResponseData ajaxResponseData = new AjaxResponseData(flowPostMap);
        return ajaxResponseData;
    }

    /**
     * 在通知3中当检查单元格是故障类型时以此方式进行展示
     * @param request
     * @param troubleId
     * @return
     */
    @RequestMapping("informGetTroubleCellDetail")
    public ModelAndView informGetTroubleCellDetail(HttpServletRequest request,String troubleId) {
        ModelAndView modelAndView = new ModelAndView();
        Map map =informMgrBusiness.informGetTroubleCellDetail(troubleId);
        if (map != null) {
            request.setAttribute("detailContent", map.get("detailContent"));
            request.setAttribute("voiceUrl", map.get("voiceUrl"));
            request.setAttribute("imageUrls",map.get("imageUrls"));
        }
        //map集合转为json对象字符串
//        modelAndView.addObject("info",com.alibaba.fastjson.JSONObject.toJSON(map));
        String viewName = "/app/javascript/orientjs/extjs/CurrentTaskMgr/CheckTableCellTroubleRecordView.jsp";
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}

