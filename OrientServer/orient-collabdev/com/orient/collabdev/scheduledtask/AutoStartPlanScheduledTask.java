package com.orient.collabdev.scheduledtask;

import com.orient.collab.model.Plan;
import com.orient.collab.model.Project;
import com.orient.collabdev.business.designing.PlanRelationBusiness;
import com.orient.collabdev.business.startup.PlanStartBusiness;
import com.orient.collabdev.business.startup.SonPlanBusiness;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.utils.CommonTools;
import com.orient.web.base.BaseBusiness;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Description 定时任务：根据计划开始时间自动启动计划
 * @Author GNY
 * @Date 2018/9/18 20:33
 * @Version 1.0
 **/
@Component
public class AutoStartPlanScheduledTask extends BaseBusiness {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 判断计划是否有前驱：
     * 1.如果没有前驱，则在计划启动日期那天启动
     * 2.如果有前驱还要判断前驱计划是否已经完成，如果前驱计划已经完成，则启动当前计划，否则不启动
     */
    @Scheduled(cron = "0 0 1 * * *")
    public void autoStart() {
        logger.info("====================定时任务启动：根据计划开始日期启动项目=====================");
        List<CollabNode> toStartProjectNodeList = collabNodeService.list(Restrictions.eq("type", CollabDevConstants.NODE_TYPE_PRJ), Restrictions.eq("status", ManagerStatusEnum.UNSTART));
        if (!CommonTools.isEmptyList(toStartProjectNodeList)) {
            toStartProjectNodeList.forEach(toStartProjectNode -> {
                Project project = orientSqlEngine.getTypeMappingBmService().getById(Project.class, toStartProjectNode.getBmDataId());
                project.setActualStartDate(CommonTools.FormatDate(new Date(), "yyyy-MM-dd"));
                project.setStatus(ManagerStatusEnum.PROCESSING.toString());
                orientSqlEngine.getTypeMappingBmService().update(project);
            });
        }
        logger.info("====================定时任务启动：根据计划开始时间启动任务=====================");
        List<CollabNode> projectNodeList = collabNodeService.list(Restrictions.eq("type", CollabDevConstants.NODE_TYPE_PRJ), Restrictions.eq("status", ManagerStatusEnum.PROCESSING));
        if (!CommonTools.isEmptyList(projectNodeList)) {
            projectNodeList.forEach(projectNode -> planStartBusiness.startPlans(projectNode.getId()));
            /*IBusinessModel planRelationBM = businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY, COLLAB_SCHEMA_ID, Table);
            projectNodeList.forEach(projectNode -> {
                List<Plan> plans = sonPlanBusiness.getPlans(projectNode.getBmDataId());
                plans.forEach(plan -> {
                    String plannedStartDate = plan.getPlannedStartDate();
                    String status = plan.getStatus();
                    //如果计划开始日期小于等于今天,并且该计划没有启动
                    if (TimeUtil.toDate(plannedStartDate).getTime() <= TimeUtil.toDate(new Date()).getTime() && ManagerStatusEnum.UNSTART.toString().equals(status)) {
                        //判断该计划是否有前驱，如果没有前驱则启动
                        planRelationBM.setReserve_filter("AND PRJ_ID_" + planRelationBM.getId() + " = '" + projectNode.getBmDataId() + "'" +
                                " AND FINISH_PLAN_ID_" + planRelationBM.getId() + " = '" + plan.getId() + "'");
                        List<Map> planRelationList = orientSqlEngine.getBmService().createModelQuery(planRelationBM).list();
                        if (planRelationList.size() == 0) {
                            startUpPlan(plan);
                        } else { //如果有前驱计划，且前驱计划已经完成，则启动计划
                            Map map = planRelationList.get(0);
                            String prePlanId = CommonTools.Obj2String(map.get("START_PLAN_ID_" + planRelationBM.getId()));
                            CollabNode prePlanNode = collabNodeService.list(Restrictions.eq("type", NODE_TYPE_PLAN), Restrictions.eq("bmDataId", prePlanId)).get(0);
                            if (prePlanNode.getStatus().equals(ManagerStatusEnum.DONE.toString())) {
                                startUpPlan(plan);
                            }
                        }
                    }
                });
            });*/
        }
    }

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    SonPlanBusiness sonPlanBusiness;

    @Autowired
    PlanRelationBusiness planRelationBusiness;

    @Autowired
    PlanStartBusiness planStartBusiness;

}

