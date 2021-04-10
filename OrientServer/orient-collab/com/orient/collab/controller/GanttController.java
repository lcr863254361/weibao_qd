package com.orient.collab.controller;

import com.alibaba.fastjson.JSONArray;
import com.orient.collab.business.GanttBusiness;
import com.orient.collab.model.*;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * the controller of gantt
 *
 * @author Seraph
 *         2016-07-18 上午8:38
 */
@Controller
@RequestMapping("/gantt")
public class GanttController extends BaseController{

    @RequestMapping(value = "/plans/cascade", method = RequestMethod.GET)
    @ResponseBody
    public List<GanttPlan> getSubPlansCascade(String parModelName, String parDataId){
        return this.ganttBusiness.getSubPlansCascade(parModelName, parDataId);
    }

    @RequestMapping(value = "/plans", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public List<GanttPlan> createOrUpdatePlans(String rootModelName, String rootDataId, @ModelAttribute("data") String plansString, BindingResult result){
        List<GanttPlan> plans = JSONArray.parseArray(plansString, GanttPlan.class);
        List<GanttPlan> newPlans = this.ganttBusiness.createOrUpdatePlans(rootModelName, rootDataId, plans);
        return newPlans;
    }


    @RequestMapping(value = "/plans/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponseData deletePlans(@ModelAttribute("data") String toDeletePlans, BindingResult result){
        List<GanttPlan> plans = JSONArray.parseArray(toDeletePlans, GanttPlan.class);
        return this.ganttBusiness.deletePlans(plans);
    }

    @RequestMapping(value = "/dependencies/cascade", method = RequestMethod.GET)
    @ResponseBody
    public List<GanttPlanDependency> getGanttPlanDependencies(String rootModelName, String rootDataId, boolean baseLine){
        return this.ganttBusiness.getGanttPlanDependencies(rootModelName, rootDataId, baseLine);
    }

    @RequestMapping(value = "/dependencies", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public List<GanttPlanDependency> createOrUpdateDependencies(String rootModelName, String rootDataId, @ModelAttribute("data") String dependenciesString){
        List<GanttPlanDependency> dependencies = JSONArray.parseArray(dependenciesString, GanttPlanDependency.class);
        return this.ganttBusiness.createOrUpdateDependencies(rootModelName, rootDataId, dependencies);
    }

    @RequestMapping(value = "/dependencies/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponseData deleteDependencies(@ModelAttribute("data") String dependenciesString){
        List<GanttPlanDependency> dependencies = JSONArray.parseArray(dependenciesString, GanttPlanDependency.class);
        return this.ganttBusiness.deleteDependencies(dependencies);
    }

    @RequestMapping(value = "/resources")
    @ResponseBody
    public GanttAssignmentData getGanttAssignmentData(String rootModelName, String rootDataId){
        return this.ganttBusiness.getGanttAssignmentData(rootModelName, rootDataId);
    }

    @RequestMapping(value = "/resources/assignment", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public GanttResourceAssign saveResourceAssignment(@RequestBody GanttResourceAssign resourceAssign){
        return this.ganttBusiness.saveResourceAssignment(resourceAssign);
        //TODO::修改角色中的执行人.
    }

    @RequestMapping(value = "/baseLine/set", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponseData setBaseLine(String rootModelName, String rootDataId){
        return this.ganttBusiness.setBaseLine(rootModelName, rootDataId);
    }

    @RequestMapping(value = "/controlStatus", method = RequestMethod.POST)
    @ResponseBody
    public GanttControlStatus getGanttControlStatus(String rootModelName, String rootDataId){
        return this.ganttBusiness.getGanttControlStatus(rootModelName, rootDataId);
    }

    @RequestMapping(value = "/getModelIdByName", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseData<Map<String, String>> getModelIdByName(String[] modelNames){
        return new AjaxResponseData<>(ganttBusiness.getModelIdByName(modelNames));
    }

    @Autowired
    private GanttBusiness ganttBusiness;
}
