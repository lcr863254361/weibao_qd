package com.orient.collabdev.business.common.planrelation.impl;

import com.orient.collab.model.GanttPlanDependency;
import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.collabdev.business.common.planrelation.IPlanRelationMng;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description 项目结束时，计划的前驱后继关系的新增和删除实现，不允许修改，直接抛异常
 * @Author GNY
 * @Date 2018/8/8 10:49
 * @Version 1.0
 **/
@MngStatus(status = ManagerStatusEnum.DONE)
@Component
public class DonePlanRelationMng implements IPlanRelationMng {

    @Override
    public List<GanttPlanDependency> savePlanRelation(String projectId, String projectNodeId, Integer projectNodeVersion, List<GanttPlanDependency> dependencyList) {
        throw new OrientBaseAjaxException("", "已完成项目无法新增和修改工作包之间的依赖关系");
    }

    @Override
    public void deletePlanRelation(String projectId, String projectNodeId, Integer projectNodeVersion, List<GanttPlanDependency> dependencyList) {
        throw new OrientBaseAjaxException("", "已完成项目无法删除工作包之间的依赖关系");
    }

}
