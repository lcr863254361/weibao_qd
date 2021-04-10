package com.orient.collabdev.business.startup;

import com.aptx.utils.TimeUtil;
import com.orient.collab.business.projectCore.algorithm.GanttGraph;
import com.orient.collab.model.GanttPlanDependency;
import com.orient.collab.model.Plan;
import com.orient.collabdev.business.designing.PlanRelationBusiness;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.utils.CommonTools;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.web.base.BaseBusiness;
import com.orient.web.util.UserContextUtil;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.orient.collabdev.constant.CollabDevConstants.NODE_TYPE_PLAN;

/**
 * @Description
 * @Author GNY
 * @Date 2018/9/25 9:43
 * @Version 1.0
 **/
@Component
public class PlanStartBusiness extends BaseBusiness {

    public void startPlans(String projectNodeId) {
        List<Plan> toStartPlans = getToStartPlans(projectNodeId);
        for (Plan plan : toStartPlans) {
            //如果今天的日期大于等于该计划的计划启动日期，则启动该计划
            if (TimeUtil.toDate(plan.getPlannedStartDate()).getTime() <= TimeUtil.toDate(new Date()).getTime()) {
                //注意更新CB_PLAN_240表的一条记录时，切面会对应修改节点记录，以及升级版本
                plan.setActualStartDate(CommonTools.FormatDate(new Date(), "yyyy-MM-dd"));
                plan.setStatus(ManagerStatusEnum.PROCESSING.toString());
                orientSqlEngine.getTypeMappingBmService().update(plan);
            }
        }
    }

    /**
     * 项目进行中，如果创建了一个计划，该计划的启动日期刚好就是当天，则调用这个方法来启动计划
     * 这里更新计划的时候，不会走更新节点的切面，需要手动更新节点的状态
     * 备注：这种方式，历史表中不会有状态为0的计划节点记录，也不会导致父节点版本升级
     *
     * @param projectNodeId
     */
    public void startPlansNotUnderApecct(String projectNodeId) {
        List<Plan> toStartPlans = getToStartPlans(projectNodeId);
        for (Plan plan : toStartPlans) {
            //如果今天的日期大于等于该计划的计划启动日期，则启动该计划
            if (TimeUtil.toDate(plan.getPlannedStartDate()).getTime() <= TimeUtil.toDate(new Date()).getTime()) {
                //注意更新CB_PLAN_240表的一条记录时，切面会对应修改节点记录，以及升级版本
                plan.setActualStartDate(CommonTools.FormatDate(new Date(), "yyyy-MM-dd"));
                plan.setStatus(ManagerStatusEnum.PROCESSING.toString());
                orientSqlEngine.getTypeMappingBmService().update(plan);
                CollabNode planNode = collabNodeService.get(Restrictions.eq("type", NODE_TYPE_PLAN), Restrictions.eq("bmDataId", plan.getId()));
                planNode.setUpdateUser(UserContextUtil.getUserAllName());
                planNode.setUpdateTime(new Date());
                planNode.setStatus(ManagerStatusEnum.PROCESSING.toString());
                collabNodeService.update(planNode);
            }
        }
    }

    /**
     * 获取待启动的计划
     *
     * @param projectNodeId
     * @return
     */
    private List<Plan> getToStartPlans(String projectNodeId) {
        CollabNode projectNode = collabNodeService.getById(projectNodeId);
        List<Plan> plans = sonPlanBusiness.getPlans(projectNodeId);
        List<GanttPlanDependency> dependencies = planRelationBusiness.getPlanRelations(projectNode.getBmDataId(), projectNodeId, projectNode.getVersion());
        GanttGraph graph = new GanttGraph(plans, dependencies);
        if (graph.hasCycle()) {
            throw new OrientBaseAjaxException("", "甘特图存在环形依赖,请检查!");
        }
        graph.travelGraphAndGetToStartPlans();
        //根据前驱后继获取待启动计划
        return graph.getToStartPlans();
    }

    @Autowired
    PlanRelationBusiness planRelationBusiness;

    @Autowired
    SonPlanBusiness sonPlanBusiness;

    @Autowired
    ICollabNodeService collabNodeService;

}
