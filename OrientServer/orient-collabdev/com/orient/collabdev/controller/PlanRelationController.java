package com.orient.collabdev.controller;

import com.alibaba.fastjson.JSONArray;
import com.orient.collab.model.GanttPlanDependency;
import com.orient.collabdev.business.designing.PlanRelationBusiness;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description 计划的前驱后继关系
 * @Author GNY
 * @Date 2018/8/8 9:24
 * @Version 1.0
 **/
@Controller
@RequestMapping("/planRelation")
public class PlanRelationController extends BaseController {

    @Autowired
    PlanRelationBusiness planRelationBusiness;

    /**
     * 保存计划的前驱后继关系
     * 判断当前项目的状态：
     * 1.如果没有开始则只在PLAN_R里插入数据
     * 2.如果项目进行中，把当前的计划关系保存到历史表，把新增的关系插入关系表，项目升级版本，把关系表当前项目的版本也升1
     * 3.如果项目结束，则抛出异常，不允许插入
     *
     * @param projectId
     * @param dependenciesString
     * @return
     */
    @RequestMapping(value = "/savePlanRelation")
    @ResponseBody
    public List<GanttPlanDependency> savePlanRelation(String projectId, String projectNodeId, @ModelAttribute("data") String dependenciesString) {
        List<GanttPlanDependency> dependencyList = JSONArray.parseArray(dependenciesString, GanttPlanDependency.class);
        return planRelationBusiness.savePlanRelation(projectId, projectNodeId, dependencyList);
    }

    /**
     * 获取一个项目下所有计划的前驱后继关系：
     * 判断是否是历史版本的项目：
     * 1.如果是当前版本则从PLAN_R表查询
     * 2.如果是历史版本的项目则从PLAN_R_HIS表查询
     *
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/getPlanRelation")
    @ResponseBody
    public List<GanttPlanDependency> getPlanRelation(String projectId, String projectNodeId, Integer projectNodeVersion) {
        return planRelationBusiness.getPlanRelations(projectId, projectNodeId, projectNodeVersion);
    }

    /**
     * 删除前驱后继关系
     * 判断当前项目状态：
     * 1.项目没有启动则直接删除
     * 2.项目如果启动，则判断关联的两个计划是否没有开始进行，只有两个关联的计划都没有开始才能删除关联关系
     * 具体删除逻辑：a.把当前项目的计划关联关系保存一份到PLAN_R_HIS,PLAN_R删除这条记录，项目版本升级1，PLAN_R里关于这个项目的记录的项目版本号也升级1
     * 3.如果项目结束了，抛出异常，不允许删
     *
     * @param dependenciesString
     * @return
     */
    @RequestMapping(value = "/deletePlanRelation")
    @ResponseBody
    public CommonResponseData deletePlanRelation(String projectId, String projectNodeId, Integer projectNodeVersion, @ModelAttribute("data") String dependenciesString) {
        List<GanttPlanDependency> dependencies = JSONArray.parseArray(dependenciesString, GanttPlanDependency.class);
        return planRelationBusiness.deletePlanRelation(projectId, projectNodeId, projectNodeVersion, dependencies);
    }

}
