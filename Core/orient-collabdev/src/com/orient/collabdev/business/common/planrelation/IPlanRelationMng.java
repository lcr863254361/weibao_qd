package com.orient.collabdev.business.common.planrelation;

import com.orient.collab.model.GanttPlanDependency;

import java.util.List;

/**
 * @Description
 * @Author GNY
 * @Date 2018/8/8 10:38
 * @Version 1.0
 **/
public interface IPlanRelationMng {

    List<GanttPlanDependency> savePlanRelation(String projectId, String projectNodeId, Integer projectNodeVersion, List<GanttPlanDependency> dependencyList);

    void deletePlanRelation(String projectId, String projectNodeId, Integer projectNodeVersion, List<GanttPlanDependency> dependencyList);

}
