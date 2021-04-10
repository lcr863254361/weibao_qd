package com.orient.collabdev.business.common.planrelation.impl;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collab.model.GanttPlanDependency;
import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.collabdev.business.common.planrelation.IPlanRelationMng;
import com.orient.collabdev.business.version.DefaultCollabVersionMng;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.utils.CommonTools;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.web.base.BaseBusiness;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.collab.config.CollabConstants.PLAN_DEPENDENCY;
import static com.orient.collab.config.CollabConstants.PLAN_DEPENDENCY_HISTORY;
import static com.orient.collabdev.constant.CollabDevConstants.PLAN;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * @Description 项目进行中时，计划的前驱后继关系的新增和删除实现
 * @Author GNY
 * @Date 2018/8/8 10:48
 * @Version 1.0
 **/
@MngStatus(status = ManagerStatusEnum.PROCESSING)
@Component
public class ProcessingPlanRelationMng extends BaseBusiness implements IPlanRelationMng {

    @Autowired
    DefaultCollabVersionMng defaultCollabVersionMng;

    @Autowired
    ICollabNodeService collabNodeService;

    /**
     * 如果项目进行中，把当前的计划关系保存到历史表，把新增的关系插入关系表，项目升级版本，把关系表当前项目的版本字段也升级
     *
     * @param projectId
     * @param projectNodeVersion
     * @param dependencyList
     * @return
     */
    @Override
    public List<GanttPlanDependency> savePlanRelation(String projectId, String projectNodeId, Integer projectNodeVersion, List<GanttPlanDependency> dependencyList) {
        //1.升级项目版本
        CollabNode newCollabNode = defaultCollabVersionMng.increaseVersion(projectNodeId);
        Integer newProjectNodeVersion = newCollabNode.getVersion();  //获取项目版本升级后，项目节点的版本号
        IBusinessModel planRelationBM = businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY, COLLAB_SCHEMA_ID, Table);
        IBusinessModel planRelationHistoryBM = businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY_HISTORY, COLLAB_SCHEMA_ID, Table);
        //2.查询出该项目下的所有计划的前驱后继关系集合，把该集合保存到PLAN_R_HIS表,PLAN_R表中，一个项目下的所有计划关系的项目版本只可能有一个
        //所以下面的过滤条件只需要通过项目ID查询就可以了，如果再加入项目节点版本有问题
        planRelationBM.setReserve_filter("AND PRJ_ID_" + planRelationBM.getId() + " = '" + projectId + "'");
        List<Map> planRelationMapList = orientSqlEngine.getBmService().createModelQuery(planRelationBM).list();
        moveToHistoryPlanRelationTable(planRelationMapList, planRelationBM, planRelationHistoryBM);
        //3.把PLAN_R表中前驱后继关系的项目版本字段设置为最新项目版本
        planRelationMapList.forEach(planRelationMap -> {
            planRelationMap.put("PRJ_VERSION_" + planRelationBM.getId(), newProjectNodeVersion);
            orientSqlEngine.getBmService().updateModelData(planRelationBM, planRelationMap, CommonTools.Obj2String(planRelationMap.get("ID")));
        });
        //4.新增的前驱后继关系设置项目ID,以及设置最新项目版本，插入到PLAN_R表
        GanttPlanDependency newAddGanttPlanDependency = dependencyList.get(0);
        newAddGanttPlanDependency.setPrjVersion(newProjectNodeVersion.toString());
        newAddGanttPlanDependency.setPrjId(projectId);
        String newId = orientSqlEngine.getBmService().insertModelData(planRelationBM, newAddGanttPlanDependency, true);
        newAddGanttPlanDependency.setId(newId);
        return dependencyList;
    }

    /**
     * 如果项目进行中，则判断关联的两个计划是否没有开始进行，只有两个关联的计划都没有开始才能删除关联关系
     * 具体删除逻辑：a.把当前项目的计划关联关系保存一份到PLAN_R_HIS,PLAN_R删除这条记录，项目版本升级，PLAN_R里关于这个项目的记录的项目版本号升级到对应版本
     *
     * @param projectId
     * @param projectNodeVersion
     * @param dependencyList
     */
    @Override
    public void deletePlanRelation(String projectId, String projectNodeId, Integer projectNodeVersion, List<GanttPlanDependency> dependencyList) {
        //1.获取前驱后继关系中，两个计划的状态
        IBusinessModel planBM = businessModelService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, Table);
        IBusinessModel planRelationBM = businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY, COLLAB_SCHEMA_ID, Table);
        IBusinessModel planRelationHistoryBM = businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY_HISTORY, COLLAB_SCHEMA_ID, Table);
        GanttPlanDependency ganttPlanDependency = dependencyList.get(0); //前端只传一条记录过来
        String startPlanId = ganttPlanDependency.getStartPlanId();
        String finishPlanId = ganttPlanDependency.getFinishPlanId();
        planBM.setReserve_filter("AND ID = '" + startPlanId + "'");
        List<Map> startPlanMapList = orientSqlEngine.getBmService().createModelQuery(planBM).list();
        //前计划的状态值
        String startPlanStatus = CommonTools.Obj2String(startPlanMapList.get(0).get("STATUS_" + planBM.getId()));
        planBM.setReserve_filter("AND ID = '" + finishPlanId + "'");
        List<Map> finishPlanMapList = orientSqlEngine.getBmService().createModelQuery(planBM).list();
        //后计划的状态值
        String finishPlanStatus = CommonTools.Obj2String(finishPlanMapList.get(0).get("STATUS_" + planBM.getId()));
        //只有前计划和后计划都没有启动时，才允许删除前驱后继关系
        if (ManagerStatusEnum.UNSTART.toString().equals(startPlanStatus) && ManagerStatusEnum.UNSTART.toString().equals(finishPlanStatus)) {
            //升级项目版本
            CollabNode newCollabNode = defaultCollabVersionMng.increaseVersion(projectNodeId);
            Integer newProjectNodeVersion = newCollabNode.getVersion();  //获取项目版本升级后，项目节点的版本号
            //查询出该项目下的所有计划的前驱后继关系集合，把该集合保存到PLAN_R_HIS表
            planRelationBM.setReserve_filter("AND PRJ_ID_" + planRelationBM.getId() + " = '" + projectId + "'");
            List<Map> planRelationMapList = orientSqlEngine.getBmService().createModelQuery(planRelationBM).list();
            moveToHistoryPlanRelationTable(planRelationMapList, planRelationBM, planRelationHistoryBM);
            //把PLAN_R表中前驱后继关系的项目版本字段升级
            planRelationMapList.forEach(planRelationMap -> {
                planRelationMap.put("PRJ_VERSION_" + planRelationBM.getId(), newProjectNodeVersion);
                orientSqlEngine.getBmService().updateModelData(planRelationBM, planRelationMap, CommonTools.Obj2String(planRelationMap.get("ID")));
            });
            //删除前端传来的关系记录
            GanttPlanDependency toDeleteGanttPlanDependency = dependencyList.get(0);
            orientSqlEngine.getBmService().delete(planRelationBM, toDeleteGanttPlanDependency.getId());
        } else {
            throw new OrientBaseAjaxException("", "关联的工作包已启动或已完成，该依赖关系无法删除");
        }
    }

    private void moveToHistoryPlanRelationTable(List<Map> planRelationMapList, IBusinessModel planRelationBM, IBusinessModel planRelationHistoryBM) {
        planRelationMapList.forEach(planRelationMap -> {
                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put("PRJ_ID_" + planRelationHistoryBM.getId(), CommonTools.Obj2String(planRelationMap.get("PRJ_ID_" + planRelationBM.getId())));
                    dataMap.put("PRJ_VERSION_" + planRelationHistoryBM.getId(), CommonTools.Obj2String(planRelationMap.get("PRJ_VERSION_" + planRelationBM.getId())));
                    dataMap.put("START_PLAN_ID_" + planRelationHistoryBM.getId(), CommonTools.Obj2String(planRelationMap.get("START_PLAN_ID_" + planRelationBM.getId())));
                    dataMap.put("FINISH_PLAN_ID_" + planRelationHistoryBM.getId(), CommonTools.Obj2String(planRelationMap.get("FINISH_PLAN_ID_" + planRelationBM.getId())));
                    dataMap.put("TYPE_" + planRelationHistoryBM.getId(), CommonTools.Obj2String(planRelationMap.get("TYPE_" + planRelationBM.getId())));
                    orientSqlEngine.getBmService().insertModelData(planRelationHistoryBM, dataMap);
                }
        );
    }

}
