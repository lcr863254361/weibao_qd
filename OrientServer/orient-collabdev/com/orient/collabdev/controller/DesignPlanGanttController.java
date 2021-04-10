package com.orient.collabdev.controller;

import com.alibaba.fastjson.JSONArray;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collab.model.GanttAssignmentData;
import com.orient.collab.model.GanttPlan;
import com.orient.collab.model.GanttResourceAssign;
import com.orient.collabdev.business.designing.DesignPlanGanttBusiness;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.DeleteModelDataEvent;
import com.orient.modeldata.eventParam.DeleteModelDataEventParam;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.collab.config.CollabConstants.PLAN;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * @Description 甘特图相关
 * @Author GNY
 * @Date 2018/7/30 15:23
 * @Version 1.0
 **/
@Controller
@RequestMapping("/designPlanGantt")
public class DesignPlanGanttController extends BaseController {

    @Autowired
    DesignPlanGanttBusiness designPlanGanttBusiness;

    /**
     * 新增/更新计划
     *
     * @param parentNodeId
     * @param plansString
     * @return
     */
    @RequestMapping(value = "/createOrUpdate")
    @ResponseBody
    public List<GanttPlan> createOrUpdatePlans(String parentNodeId, @ModelAttribute("data") String plansString) {
        List<GanttPlan> plans = JSONArray.parseArray(plansString, GanttPlan.class);
        return designPlanGanttBusiness.createOrUpdatePlans(parentNodeId, plans);
    }

    /**
     * 查询计划
     * 1.如果是在设计面板，项目节版本通过后台查询出来，也就是最新的项目版本
     * 2.如果是在运行面板，前台会传项目版本过来
     *
     * @param parentNodeId
     * @param projectId
     * @param projectNodeVersion
     * @return
     */
    @RequestMapping(value = "/plans")
    @ResponseBody
    public List<GanttPlan> getPlans(String parentNodeId, String projectId, Integer projectNodeVersion) {
        return designPlanGanttBusiness.getPlans(parentNodeId, projectNodeVersion);
    }

    /**
     * 删除计划
     */
    @RequestMapping(value = "/deletePlans", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponseData deletePlans(@ModelAttribute("data") String toDeletePlans) {
        List<GanttPlan> plans = JSONArray.parseArray(toDeletePlans, GanttPlan.class);
        CommonResponseData retVal = new CommonResponseData();
        IBusinessModel planBM = designPlanGanttBusiness.businessModelService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, Table);
        String modelId = planBM.getId();
        Long[] toDelIds = new Long[plans.size()];
        for (int i = 0; i < plans.size(); i++) {
            toDelIds[i] = Long.parseLong(plans.get(i).getId());
        }
        DeleteModelDataEventParam deleteModelDataEventParam = new DeleteModelDataEventParam(modelId, toDelIds, "false");
        OrientContextLoaderListener.Appwac.publishEvent(new DeleteModelDataEvent(ModelDataController.class, deleteModelDataEventParam));
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 获取负责人列表
     *
     * @param projectNodeId
     * @return
     */
    @RequestMapping(value = "/resources")
    @ResponseBody
    public GanttAssignmentData getGanttAssignmentData(String projectNodeId) {
        return designPlanGanttBusiness.getGanttAssignmentData();
    }

    /**
     * 保存计划的负责人
     */
    @RequestMapping(value = "/resources/saveAssignment")
    @ResponseBody
    public GanttResourceAssign saveResourceAssignment(@RequestBody GanttResourceAssign resourceAssign) {
        return designPlanGanttBusiness.saveResourceAssignment(resourceAssign);
    }

    /**
     * 通过计划的id获取计划节点的id
     *
     * @param planId
     * @return
     */
    @RequestMapping(value = "/getPlanNodeId")
    @ResponseBody
    public AjaxResponseData<String> getPlanNodeId(String planId) {
        return designPlanGanttBusiness.getPlanNodeId(planId);
    }

}
