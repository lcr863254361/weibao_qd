package com.orient.collabdev.business.common.noderefopera.impl;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collabdev.business.common.annotation.NodeRefOperate;
import com.orient.collabdev.business.common.noderefopera.NodeRefOperateInterface;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.utils.CommonTools;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.collab.config.CollabConstants.PLAN_DEPENDENCY;
import static com.orient.collab.config.CollabConstants.PLAN_DEPENDENCY_HISTORY;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * @Description  删除计划前驱后继关系
 * @Author GNY
 * @Date 2018/8/30 10:58
 * @Version 1.0
 **/
@NodeRefOperate
public class PlanRelationRef extends AbstractNodeRefOperate implements NodeRefOperateInterface {

    @Override
    public boolean deleteNodeRefData(Boolean isUnstarted, CollabNode... nodes) {
        if (isUnstarted) {
            for (CollabNode collabNode : nodes) {
                if ((CollabDevConstants.NODE_TYPE_PRJ.equalsIgnoreCase(collabNode.getType()))) {
                    deletePlanRelations(collabNode.getBmDataId());
                }
            }
        } else {
            for (CollabNode collabNode : nodes) {
                if ((CollabDevConstants.NODE_TYPE_PRJ.equalsIgnoreCase(collabNode.getType()))) {
                    moveToHistoryPlanRelationTable(collabNode.getBmDataId());
                }
            }
        }
        return true;
    }

    private void moveToHistoryPlanRelationTable(String projectId) {
        IBusinessModel planRelationBM = businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY, COLLAB_SCHEMA_ID, Table);
        IBusinessModel planRelationHistoryBM = businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY_HISTORY, COLLAB_SCHEMA_ID, Table);
        planRelationBM.setReserve_filter("AND PRJ_ID_" + planRelationBM.getId() + " = '" + projectId + "'");
        List<Map<String, String>> planRelationMapList = orientSqlEngine.getBmService().createModelQuery(planRelationBM).list();
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
        String toDelIds = planRelationMapList.stream().map(dataMap -> dataMap.get("ID")).collect(Collectors.joining(","));
        if (!CommonTools.isNullString(toDelIds)) {
            orientSqlEngine.getBmService().delete(planRelationBM, toDelIds);
        }
    }

    private void deletePlanRelations(String projectId) {
        IBusinessModel planRelationBM = businessModelService.getBusinessModelBySName(PLAN_DEPENDENCY, COLLAB_SCHEMA_ID, Table);
        planRelationBM.setReserve_filter("AND PRJ_ID_" + planRelationBM.getId() + " = '" + projectId + "'");
        List<Map<String, String>> toDelData = orientSqlEngine.getBmService().createModelQuery(planRelationBM).list();
        String toDelIds = toDelData.stream().map(dataMap -> dataMap.get("ID")).collect(Collectors.joining(","));
        if (!CommonTools.isNullString(toDelIds)) {
            orientSqlEngine.getBmService().delete(planRelationBM, toDelIds);
        }
    }

    @Autowired
    IBusinessModelService businessModelService;

    @Autowired
    ISqlEngine orientSqlEngine;

}
