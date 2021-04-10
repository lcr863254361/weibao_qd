package com.orient.collabdev.business.common.planrelation.impl;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collab.model.GanttPlanDependency;
import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.collabdev.business.common.planrelation.IPlanRelationMng;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.utils.CommonTools;
import com.orient.web.base.BaseBusiness;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.collab.config.CollabConstants.PLAN_DEPENDENCY;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * @Description 项目没有启动时，计划的前驱后继关系的新增和删除实现
 * @Author GNY
 * @Date 2018/8/8 10:47
 * @Version 1.0
 **/
@MngStatus(status = ManagerStatusEnum.UNSTART)
@Component
public class UnStartPlanRelationMng extends BaseBusiness implements IPlanRelationMng {

    @Override
    public List<GanttPlanDependency> savePlanRelation(String projectId, String projectNodeId, Integer projectNodeVersion, List<GanttPlanDependency> dependencyList) {
        IBusinessModel dependencyBm = businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY, COLLAB_SCHEMA_ID, Table);
        for (GanttPlanDependency dependency : dependencyList) {
            dependency.setPrjId(projectId);
            dependency.setPrjVersion(projectNodeVersion + "");
            if (CommonTools.isNullString(dependency.getId())) {
                String newId = orientSqlEngine.getBmService().insertModelData(dependencyBm, dependency, true);
                dependency.setId(newId);
            } else {
                orientSqlEngine.getBmService().updateModelData(dependencyBm, dependency, dependency.getId(), true);
            }
        }
        return dependencyList;
    }

    @Override
    public void deletePlanRelation(String projectId, String projectNodeId, Integer projectNodeVersion, List<GanttPlanDependency> dependencyList) {
        IBusinessModel dependencyBm = businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY, COLLAB_SCHEMA_ID, Table);
        for (GanttPlanDependency dependency : dependencyList) {
            orientSqlEngine.getBmService().delete(dependencyBm, dependency.getId());
        }
    }

}
