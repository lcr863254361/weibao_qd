package com.orient.collabdev.business.designing;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collab.event.ProjectTreeNodeCreatedEvent;
import com.orient.collab.event.ProjectTreeNodeCreatedEventParam;
import com.orient.collab.event.ProjectTreeNodeEditEvent;
import com.orient.collab.event.ProjectTreeNodeEditEventParam;
import com.orient.collab.model.*;
import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sqlengine.util.BusinessDataConverter;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.AjaxResponseData;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.collabdev.constant.CollabDevConstants.*;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/30 15:37
 * @Version 1.0
 **/
@Service
public class DesignPlanGanttBusiness extends StructureBusiness {

    @Autowired
    ICollabNodeService collabNodeService;

    public List<GanttPlan> createOrUpdatePlans(String parentNodeId, List<GanttPlan> plans) {
        Collection<User> users = roleEngine.getRoleModel(false).getUsers().values();
        IBusinessModel planBm = businessModelService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, Table);
        List<GanttPlan> retV = UtilFactory.newArrayList();
        for (GanttPlan plan : plans) {
            if (plan.getParentId() == null || "-1".equals(plan.getParentId())) {
                plan.setParentNodeId(parentNodeId);
            } else {
                plan.setParPlanId(plan.getParentId());
            }
            if (plan.isNewCreate()) {
                plan.setStatus(ManagerStatusEnum.UNSTART.toString());
                plan.setType(PLAN_TYPE_NORMAL);
                String resourceName = plan.getResourceName();
                if (!StringUtil.isEmpty(plan.getResourceName())) {
                    Predicate<User> userAllNameFilter = user -> resourceName.equals(user.getAllName());
                    if (users.stream().filter(userAllNameFilter).count() > 0) {
                        User user = users.stream().filter(userAllNameFilter).findFirst().get();
                        plan.setPrincipal(user.getId());
                    }
                }
                if (!CommonTools.isNullString(plan.getParPlanId())) {
                    Plan parPlan = orientSqlEngine.getTypeMappingBmService().getById(Plan.class, plan.getParPlanId());
                    parPlan.setType(PLAN_TYPE_GROUP);
                    orientSqlEngine.getTypeMappingBmService().update(parPlan);
                }
            } else {
                if (!CommonTools.isNullString(plan.getActualStartDate()) && !CommonTools.isNullString(plan.getActualEndDate())) {
                    if (plan.getActualStartDate().equals(plan.getActualEndDate())) {
                        plan.setType(PLAN_TYPE_MILESTONE);
                    }
                } else if (plan.isLeaf()) {
                    plan.setType(PLAN_TYPE_NORMAL);
                }
            }

            Map<String, String> dataMap = BusinessDataConverter.convertBeanToRealColMap(planBm, plan, true, true);

            if (CommonTools.isNullString(plan.getId())) {
                // dataMap.put("VERSION_" + planBm.getId(), "1"); //新增计划需要设置版本为1
                ProjectTreeNodeCreatedEventParam eventParam = new ProjectTreeNodeCreatedEventParam();
                eventParam.setModelId(planBm.getId());
                eventParam.setDataMap(dataMap);
                eventParam.setCreateData(true);
                OrientContextLoaderListener.Appwac.publishEvent(new ProjectTreeNodeCreatedEvent(this, eventParam));
                String newId = CommonTools.Obj2String(eventParam.getDataMap().get("ID"));
                plan.setId(newId);
            } else {
                orientSqlEngine.getBmService().updateModelData(planBm, dataMap, plan.getId());
            }
            plan.setNewCreate(false);
            plan.setIconCls("icon-collabDev-plan");
            plan.setParentNodeId(parentNodeId);
            retV.add(plan);
        }
        return retV;
    }

    public GanttAssignmentData getGanttAssignmentData() {
        GanttAssignmentData assignmentData = new GanttAssignmentData();
        List<GanttResource> resources = UtilFactory.newArrayList();
        Map<String, User> users = roleEngine.getRoleModel(false).getUsers();
        users.forEach((key, user) -> {
            if (!user.getId().equals("-1") && !user.getId().equals("-2") && !user.getId().equals("-3")) {
                GanttResource resource = new GanttResource();
                resource.setId(user.getId());
                resource.setName(user.getAllName());
                resources.add(resource);
            }
        });
        assignmentData.setResources(resources);
        return assignmentData;
    }

    public List<GanttPlan> getPlans(String parentNodeId, Integer projectNodeVersion) {
        if (projectNodeVersion == null) {
            CollabNode collabNode = collabNodeService.getById(parentNodeId);
            projectNodeVersion = collabNode.getVersion();
        }
        List<GanttPlan> retVal = new ArrayList<>();
        Map<String, User> users = roleEngine.getRoleModel(false).getUsers();
        IBusinessModel planBM = businessModelService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, Table);
        IBusinessModel planHistoryBM = businessModelService.getBusinessModelBySName(PLAN_HISTORY, COLLAB_SCHEMA_ID, Table);
        if (!isHistoryNode(parentNodeId, projectNodeVersion)) { //如果项目节点不是历史版本
            List<CollabNode> planCollabNodes = collabNodeService.list(Restrictions.eq("pid", parentNodeId));
            if (planCollabNodes.size() > 0) {
                String ids = planCollabNodes.stream()
                        .map(CollabNode::getBmDataId)
                        .collect(Collectors.joining(","));
                planBM.setReserve_filter(" AND ID IN (" + ids + ")");
                List planMapList = orientSqlEngine.getBmService().createModelQuery(planBM).orderAsc("TO_NUMBER(ID)").list();
                retVal = BusinessDataConverter.convertMapListToBeanList(planBM, planMapList, GanttPlan.class, true);
                for (GanttPlan plan : retVal) {
                    plan.setResourceName(users.get(plan.getPrincipal()).getAllName());
                    plan.setIconCls("icon-collabDev-plan");
                    plan.setParentNodeId(parentNodeId);
                    plan.setLeaf(isLeafPlan(plan));
                    if (!isLeafPlan(plan)) {
                        iteratorPlanSubTree(planBM, plan, users);
                    }
                }
            }
        } else {  //如果项目节点是历史版本
            IBusinessModel operationPlanBM;
            List<CollabDevNodeDTO> planNodeList = getSonNodes(parentNodeId, projectNodeVersion);
            //避免空指针错误
            if (planNodeList == null) {
                return retVal;
            } else {
                for (CollabDevNodeDTO planNode : planNodeList) {
                    String planNodeId = planNode.getId();
                    Integer planNodeVersion = planNode.getVersion();
                    String bmDataId = planNode.getBmDataId();
                    operationPlanBM = isHistoryNode(planNodeId, planNodeVersion) ? planHistoryBM : planBM;
                    operationPlanBM.setReserve_filter(" AND ID = '" + bmDataId + "'");
                    List planMapList = orientSqlEngine.getBmService().createModelQuery(planHistoryBM).list();
                    List<GanttPlan> ganttPlanList = BusinessDataConverter.convertMapListToBeanList(planHistoryBM, planMapList, GanttPlan.class, true);
                    GanttPlan ganttPlan = ganttPlanList.get(0);
                    ganttPlan.setResourceName(users.get(ganttPlan.getPrincipal()).getAllName());
                    ganttPlan.setIconCls("icon-collabDev-plan");
                    ganttPlan.setParentNodeId(parentNodeId);
                    //递归子计划
                    iteratorSubPlan(ganttPlan, planNodeId, planNodeVersion, planBM, planHistoryBM, users);
                    retVal.add(ganttPlan);
                }
            }
        }
        return retVal;
    }

    private void iteratorSubPlan(GanttPlan ganttPlan, String parentPlanNodeId, Integer parentPlanNodeVersion, IBusinessModel planBM, IBusinessModel planHistoryBM, Map<String, User> users) {
        List<GanttPlan> subGanttPlanList = new ArrayList<>();
        List<CollabDevNodeDTO> subPlanNodeList = getSonNodes(parentPlanNodeId, parentPlanNodeVersion);
        if (subPlanNodeList.size() == 0) {
            ganttPlan.setLeaf(true);
        } else {
            ganttPlan.setLeaf(false);
            IBusinessModel operationPlanBM;
            for (CollabDevNodeDTO subPlanNode : subPlanNodeList) {
                String planNodeId = subPlanNode.getId();
                Integer planNodeVersion = subPlanNode.getVersion();
                String bmDataId = subPlanNode.getBmDataId();
                operationPlanBM = isHistoryNode(planNodeId, planNodeVersion) ? planHistoryBM : planBM;
                operationPlanBM.setReserve_filter(" AND ID = '" + bmDataId + "'");
                List planMapList = orientSqlEngine.getBmService().createModelQuery(planHistoryBM).list();
                List<GanttPlan> ganttPlanList = BusinessDataConverter.convertMapListToBeanList(planHistoryBM, planMapList, GanttPlan.class, true);
                GanttPlan subGanttPlan = ganttPlanList.get(0);
                subGanttPlan.setResourceName(users.get(ganttPlan.getPrincipal()).getAllName());
                subGanttPlan.setIconCls("icon-collabDev-plan");
                subGanttPlan.setParentNodeId(parentPlanNodeId);
                iteratorSubPlan(ganttPlan, planNodeId, planNodeVersion, planBM, planHistoryBM, users);
                subGanttPlanList.add(subGanttPlan);
            }
            ganttPlan.setChildren(subGanttPlanList);
        }
    }

    private boolean isLeafPlan(GanttPlan ganttPlan) {
        List<CollabNode> planNodeList = collabNodeService.list(Restrictions.eq("bmDataId", ganttPlan.getId()), Restrictions.eq("type", CollabDevConstants.NODE_TYPE_PLAN));
        //计划下可以有子计划和任务，所以判断是否有子计划需要把任务过滤掉
        List<CollabNode> childPlanNodeList = collabNodeService.list(Restrictions.eq("pid", planNodeList.get(0).getId()), Restrictions.eq("type", CollabDevConstants.NODE_TYPE_PLAN));
        return childPlanNodeList.size() == 0;
    }

    private void iteratorPlanSubTree(IBusinessModel planBM, GanttPlan parentPlan, Map<String, User> users) {
        List<CollabNode> parentPlanCollabNodes = collabNodeService.list(Restrictions.eq("bmDataId", parentPlan.getId()));
        CollabNode parentPlanNode = parentPlanCollabNodes.get(0);
        List<CollabNode> childrenPlanCollabNodes = collabNodeService.list(Restrictions.eq("pid", parentPlanNode.getId()));
        if (childrenPlanCollabNodes.size() > 0) {
            String ids = childrenPlanCollabNodes.stream()
                    .map(CollabNode::getBmDataId)
                    .collect(Collectors.joining(","));
            planBM.setReserve_filter(" AND ID IN (" + ids + ")");
            List planMapList = orientSqlEngine.getBmService().createModelQuery(planBM).orderAsc("TO_NUMBER(ID)").list();
            List<GanttPlan> retVal = BusinessDataConverter.convertMapListToBeanList(planBM, planMapList, GanttPlan.class, true);
            for (GanttPlan plan : retVal) {
                parentPlan.setChildren(retVal);
                plan.setParentNodeId(parentPlanNode.getId());
                plan.setResourceName(users.get(plan.getPrincipal()).getAllName());
                plan.setIconCls("icon-collabDev-plan");
                plan.setLeaf(isLeafPlan(plan));
                if (!isLeafPlan(plan)) {
                    iteratorPlanSubTree(planBM, plan, users);
                }
            }
        }
    }

    public GanttResourceAssign saveResourceAssignment(GanttResourceAssign resourceAssign) {
        IBusinessModel planBm = businessModelService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, Table);
        Map<String, String> oriData = orientSqlEngine.getBmService().createModelQuery(planBm).findById(resourceAssign.getPlanId());
        String oriUserId = CommonTools.Obj2String(oriData.get("PRINCIPAL_" + planBm.getId()));
        Map<String, String> updateMap = UtilFactory.newHashMap();
        updateMap.put("PRINCIPAL_" + planBm.getId(), resourceAssign.getResourceId());
        oriData.put("PRINCIPAL_" + planBm.getId(), resourceAssign.getResourceId());
        orientSqlEngine.getBmService().updateModelData(planBm, updateMap, resourceAssign.getPlanId());
        ProjectTreeNodeEditEventParam eventParam = new ProjectTreeNodeEditEventParam();
        eventParam.setModelId(planBm.getId());
        eventParam.setDataMap(oriData);
        eventParam.setOriginalUserId(oriUserId);
        OrientContextLoaderListener.Appwac.publishEvent(new ProjectTreeNodeEditEvent(this, eventParam));
        return resourceAssign;
    }

    public AjaxResponseData<String> getPlanNodeId(String planId) {
        AjaxResponseData<String> retVal = new AjaxResponseData<>();
        List<CollabNode> planNodes = collabNodeService.list(Restrictions.eq("bmDataId", planId));
        if (planNodes.size() > 0) {
            retVal.setResults(planNodes.get(0).getId());
        }
        return retVal;
    }

}
