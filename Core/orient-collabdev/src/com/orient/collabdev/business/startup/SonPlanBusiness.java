package com.orient.collabdev.business.startup;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collab.model.Plan;
import com.orient.sqlengine.util.BusinessDataConverter;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.web.base.BaseBusiness;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.collabdev.constant.CollabDevConstants.NODE_TYPE_PLAN;
import static com.orient.collabdev.constant.CollabDevConstants.PLAN;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * @Description
 * @Author GNY
 * @Date 2018/9/19 9:07
 * @Version 1.0
 **/
@Component
public class SonPlanBusiness extends BaseBusiness {

    /**
     * 获取一个项目下的所有计划，包括子计划
     *
     * @param projectNodeId
     * @return
     */
    public List<Plan> getPlans(String projectNodeId) {
        List<Plan> retVal = new ArrayList<>();
        List<CollabNode> planNodeList = collabNodeService.list(Restrictions.eq("pid", projectNodeId));
        IBusinessModel planBM = businessModelService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, Table);
        if (planNodeList.size() > 0) {
            String ids = planNodeList.stream()
                    .map(CollabNode::getBmDataId)
                    .collect(Collectors.joining(","));
            planBM.setReserve_filter("AND ID IN (" + ids + ")");
            List planMapList = orientSqlEngine.getBmService().createModelQuery(planBM).orderAsc("TO_NUMBER(ID)").list();
            retVal = BusinessDataConverter.convertMapListToBeanList(planBM, planMapList, Plan.class, true);
            for (Plan plan : retVal) {
                if (!isLeafPlan(plan)) {
                    addSonPlan(planBM, plan);
                }
            }
        }
        return retVal;
    }

    private void addSonPlan(IBusinessModel planBM, Plan parentPlan) {
        List<CollabNode> parentPlanCollabNodes = collabNodeService.list(Restrictions.eq("bmDataId", parentPlan.getId()));
        CollabNode parentPlanNode = parentPlanCollabNodes.get(0);
        List<CollabNode> childrenPlanCollabNodes = collabNodeService.list(Restrictions.eq("pid", parentPlanNode.getId()));
        if (childrenPlanCollabNodes.size() > 0) {
            String ids = childrenPlanCollabNodes.stream()
                    .map(CollabNode::getBmDataId)
                    .collect(Collectors.joining(","));
            planBM.setReserve_filter("AND ID IN (" + ids + ")");
            List planMapList = orientSqlEngine.getBmService().createModelQuery(planBM).orderAsc("TO_NUMBER(ID)").list();
            List<Plan> retVal = BusinessDataConverter.convertMapListToBeanList(planBM, planMapList, Plan.class, true);
            for (Plan plan : retVal) {
                if (!isLeafPlan(plan)) {
                    addSonPlan(planBM, plan);
                }
            }
        }
    }

    private boolean isLeafPlan(Plan plan) {
        List<CollabNode> planNodeList = collabNodeService.list(Restrictions.eq("bmDataId", plan.getId()), Restrictions.eq("type", NODE_TYPE_PLAN));
        //计划下可以有子计划和任务，所以判断是否有子计划需要把任务过滤掉
        List<CollabNode> childPlanNodeList = collabNodeService.list(Restrictions.eq("pid", planNodeList.get(0).getId()), Restrictions.eq("type", NODE_TYPE_PLAN));
        return childPlanNodeList.size() == 0;
    }

    @Autowired
    ICollabNodeService collabNodeService;

}
