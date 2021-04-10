package com.orient.collabdev.business.designing;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collab.model.GanttPlanDependency;
import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.collabdev.business.common.planrelation.IPlanRelationMng;
import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sqlengine.util.BusinessDataConverter;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.utils.CommonTools;
import com.orient.web.base.CommonResponseData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.collab.config.CollabConstants.PLAN_DEPENDENCY;
import static com.orient.collab.config.CollabConstants.PLAN_DEPENDENCY_HISTORY;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * @Description
 * @Author GNY
 * @Date 2018/8/8 9:25
 * @Version 1.0
 **/
@Service
public class PlanRelationBusiness extends StructureBusiness {

    public List<GanttPlanDependency> savePlanRelation(String projectId, String projectNodeId, List<GanttPlanDependency> dependencyList) {
        CollabNode collabNode = collabNodeService.getById(projectNodeId);
        Integer projectNodeVersion = collabNode.getVersion();
        IPlanRelationMng planRelationMng = getPlanRelationMng(projectNodeId, collabNode.getVersion());
        return planRelationMng.savePlanRelation(projectId, projectNodeId, projectNodeVersion, dependencyList);
    }

   /* public List<GanttPlanDependency> getPlanRelations(String projectId, String projectNodeId, Integer projectNodeVersion) {
        //如果是在设计面板，前台不传projectNodeVersion，需要自己获取
        if (projectNodeVersion == null) {
            CollabNode collabNode = collabNodeService.getById(projectNodeId);
            projectNodeVersion = collabNode.getVersion();
        }
        IBusinessModel planRelationBM = businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY, COLLAB_SCHEMA_ID, Table);
        IBusinessModel planRelationHistoryBM = businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY_HISTORY, COLLAB_SCHEMA_ID, Table);
        //先从PLAN_R_HIS表通过项目id和待查询项目版本号查
        planRelationHistoryBM.setReserve_filter("AND PRJ_ID_" + planRelationHistoryBM.getId() + " = '" + projectId + "'" +
                "AND PRJ_VERSION_" + planRelationHistoryBM.getId() + " = " + projectNodeVersion);
        List planRelationHistoryList = orientSqlEngine.getBmService().createModelQuery(planRelationHistoryBM).list();
        if (planRelationHistoryList.size() > 0) {
            List<GanttPlanDependency> planDependencyList = BusinessDataConverter.convertMapListToBeanList(planRelationHistoryBM, planRelationHistoryList, GanttPlanDependency.class, true);
            return planDependencyList;
        } else { //如果PLAN_R_HIS查不到记录，则从PLAN_R表通过项目id和待查询项目版本号查
            planRelationBM.setReserve_filter("AND PRJ_ID_" + planRelationBM.getId() + " = '" + projectId + "'" +
                    "AND PRJ_VERSION_" + planRelationBM.getId() + " = " + projectNodeVersion);
            List planRelationList = orientSqlEngine.getBmService().createModelQuery(planRelationBM).list();
            if (planRelationList.size() > 0) {
                List<GanttPlanDependency> planDependencyList = BusinessDataConverter.convertMapListToBeanList(planRelationBM, planRelationList, GanttPlanDependency.class, true);
                return planDependencyList;
            } else {//如果还是查不到，则获取最接近当前项目版本号的版本号，通过项目id和最接近的项目版本号来查
                String selectionSql = "SELECT * FROM " + planRelationBM.getName() + "_" + COLLAB_SCHEMA_ID + " WHERE PRJ_ID_" + planRelationBM.getId() + " = '" + projectId + "' ORDER BY PRJ_VERSION_" + planRelationBM.getId() + " DESC";
                List<Map<String, Object>> list = metaDaoFactory.getJdbcTemplate().queryForList(selectionSql);
                if (list.size() > 0) {
                    Integer secondMaxVersion = Integer.parseInt(CommonTools.Obj2String(list.get(0).get("PRJ_VERSION_" + planRelationBM.getId())));
                    planRelationBM.setReserve_filter("AND PRJ_ID_" + planRelationBM.getId() + " = '" + projectId + "'" +
                            "AND PRJ_VERSION_" + planRelationBM.getId() + " = " + secondMaxVersion);
                    List secondPlanRelationList = orientSqlEngine.getBmService().createModelQuery(planRelationBM).list();
                    return BusinessDataConverter.convertMapListToBeanList(planRelationBM, secondPlanRelationList, GanttPlanDependency.class, true);
                } else {
                    return new ArrayList<>();
                }
            }
        }
    }*/

    public List<GanttPlanDependency> getPlanRelations(String projectId, String projectNodeId, Integer projectNodeVersion) {
        //如果是在设计面板，前台不传projectNodeVersion，需要后台获取
        if (projectNodeVersion == null) {
            CollabNode collabNode = collabNodeService.getById(projectNodeId);
            projectNodeVersion = collabNode.getVersion();
        }
        IBusinessModel planRelationBM = businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY, COLLAB_SCHEMA_ID, Table);
        IBusinessModel planRelationHistoryBM = businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY_HISTORY, COLLAB_SCHEMA_ID, Table);
        //在CB_PLAN_R_240表中，通过projectId获取到最大的项目版本
        Integer maxProjectVersion = getMaxVersionFromPlanR(planRelationBM, projectId);
        if (maxProjectVersion != null) {
            if (projectNodeVersion >= maxProjectVersion) {
                //只通过项目id查询即可
                planRelationBM.setReserve_filter("AND PRJ_ID_" + planRelationBM.getId() + " = '" + projectId + "'");
                List planRelationList = orientSqlEngine.getBmService().createModelQuery(planRelationBM).list();
                if (planRelationList.size() > 0) {
                    List<GanttPlanDependency> planDependencyList = BusinessDataConverter.convertMapListToBeanList(planRelationBM, planRelationList, GanttPlanDependency.class, true);
                    return planDependencyList;
                }
            } else {
                Integer secondMaxVersion = getSecondMaxVersionFromPlanHistoryR(planRelationHistoryBM, projectId, projectNodeVersion);
                planRelationBM.setReserve_filter("AND PRJ_ID_" + planRelationBM.getId() + " = '" + projectId + "'" +
                        "AND PRJ_VERSION_" + planRelationBM.getId() + " = " + secondMaxVersion);
                List list = orientSqlEngine.getBmService().createModelQuery(planRelationBM).list();
                if (!CommonTools.isEmptyList(list)) {
                    return BusinessDataConverter.convertMapListToBeanList(planRelationBM, list, GanttPlanDependency.class, true);
                } else {
                    return null;
                }

            }
        }
        return null;
    }

    private Integer getMaxVersionFromPlanR(IBusinessModel planRelationBM, String projectId) {
        planRelationBM.setReserve_filter("AND PRJ_ID_" + planRelationBM.getId() + " = '" + projectId + "'");
        List<Map> list = orientSqlEngine.getBmService().createModelQuery(planRelationBM).list();
        if (!CommonTools.isEmptyList(list)) {
            Map map = list.get(0);
            return Integer.valueOf(CommonTools.Obj2String(map.get("PRJ_VERSION_" + planRelationBM.getId())));
        }
        return null;
    }

    private Integer getSecondMaxVersionFromPlanHistoryR(IBusinessModel planRelationHistoryBM, String projectId, Integer toCompareProjectVersion) {
        planRelationHistoryBM.setReserve_filter("AND PRJ_ID_" + planRelationHistoryBM.getId() + " = '" + projectId + "'" +
                "AND PRJ_VERSION_" + planRelationHistoryBM.getId() + " <= " + toCompareProjectVersion);
        List<Map> list = orientSqlEngine.getBmService().createModelQuery(planRelationHistoryBM).orderAsc(" PRJ_VERSION_" + planRelationHistoryBM.getId()).list();
        if (!CommonTools.isEmptyList(list)) {
            Map map = list.get(0);
            return Integer.valueOf(CommonTools.Obj2String(map.get("PRJ_VERSION_" + planRelationHistoryBM.getId())));
        }
        return null;
    }

    public CommonResponseData deletePlanRelation(String projectId, String projectNodeId, Integer projectNodeVersion, List<GanttPlanDependency> dependencyList) {
        CommonResponseData retVal = new CommonResponseData();
        if (projectNodeVersion == null) {
            CollabNode collabNode = collabNodeService.getById(projectNodeId);
            projectNodeVersion = collabNode.getVersion();
        }
        IPlanRelationMng planRelationMng = getPlanRelationMng(projectNodeId, projectNodeVersion);
        planRelationMng.deletePlanRelation(projectId, projectNodeId, projectNodeVersion, dependencyList);
        retVal.setSuccess(true);
        retVal.setMsg("删除成功");
        return retVal;
    }

    private IPlanRelationMng getPlanRelationMng(String projectNodeId, Integer projectNodeVersion) {
        IPlanRelationMng retVal = null;
        CollabDevNodeDTO projectNode = getRootNode(projectNodeId, projectNodeVersion);
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(com.orient.collabdev.business.common.planrelation.IPlanRelationMng.class);
        for (String beanName : beanNames) {
            IPlanRelationMng planRelationMng = OrientContextLoaderListener.Appwac.getBean(beanName, IPlanRelationMng.class);
            Class operateClass = planRelationMng.getClass();
            MngStatus classAnnotation = (MngStatus) operateClass.getAnnotation(MngStatus.class);
            ManagerStatusEnum annotationStatus = classAnnotation.status();
            if (annotationStatus == ManagerStatusEnum.fromString(projectNode.getStatus())) {
                retVal = planRelationMng;
            }
        }
        return retVal;
    }

}
