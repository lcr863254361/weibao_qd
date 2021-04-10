package com.orient.collab.business.strategy;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collab.business.projectCore.cmd.CommandService;
import com.orient.collab.business.projectCore.cmd.concrete.GetAllSubPlansCmd;
import com.orient.collab.business.projectCore.cmd.concrete.GetChildTaskIdsCascade;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.*;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.business.ProcessDefinitionBusiness;
import com.orient.flow.business.ProcessInstanceBusiness;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.collab.CollabFunction;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.sysmodel.domain.user.UserDAO;
import com.orient.sysmodel.service.collab.impl.CollabFunctionService;
import com.orient.sysmodel.service.collab.impl.CollabRoleService;
import com.orient.utils.CommonTools;
import com.orient.utils.Pair;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.CommonResponseData;
import org.hibernate.criterion.Restrictions;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;

import java.util.*;
import java.util.stream.Collectors;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.collab.config.CollabConstants.*;
import static com.orient.sysmodel.domain.collab.CollabFunction.BELONGED_MODEL;
import static com.orient.sysmodel.domain.collab.CollabRole.NODE_ID;
import static com.orient.sysmodel.domain.collab.CollabRole.MODEL_NAME;

/**
 * node strategy
 *
 * @author Seraph
 *         2016-07-06 下午2:29
 */
public enum ProjectTreeNodeStrategy {

    DirNode(DIRECTORY) {
        @Override
        public List<ProjectTreeNode> getNextLayerNodes(FunctionModule functionModule, String curDataId) {
            List<ProjectTreeNode> retV = UtilFactory.newArrayList();

            if (curDataId.equals("-1")) {
                IBusinessModel dirBm = bmService.getBusinessModelBySName(this.toString(), COLLAB_SCHEMA_ID, Table);

                dirBm.setReserve_filter(" AND ID = '-1'");
                List<Map<String, Object>> oriDatas = sqlEngine.getBmService().createModelQuery(dirBm).list();
                if (oriDatas.size() == 0) {
                    Map<String, String> dataMap = UtilFactory.newHashMap();
                    dataMap.put("ID", "-1");
                    dataMap.put("NAME_" + dirBm.getId(), "根目录");
                    sqlEngine.getBmService().insertModelData(dirBm, dataMap);
                    return retV;
                }
            }

            retV.addAll(getSubNodesOfType(this.toString(), curDataId, DIRECTORY));
            retV.addAll(getSubNodesOfType(this.toString(), curDataId, PROJECT));
            retV.addAll(getSubNodesOfType(this.toString(), curDataId, PLAN));
            return retV;
        }

        @Override
        public TreeDeleteResult deleteNode(String curDataId) {
            TreeDeleteResult retVal = new TreeDeleteResult();
            StringBuilder toDeleteDirIds = new StringBuilder(curDataId).append(",");
            if (CollabConstants.COLLAB_DEBUG_MODEL) {
                //强制删除
                //get son dirs
                getSonDirsWithSelf(curDataId, toDeleteDirIds);
                List<String> allDirs = new ArrayList<>();
                List<String> allSonPrjs = new ArrayList<>();
                List<String> allSonPlans = new ArrayList<>();
                List<String> allSonTasks = new ArrayList<>();
                String[] toDelDirArr = toDeleteDirIds.toString().split(",");
                for (String toDelDir : toDelDirArr) {
                    if (!StringUtil.isEmpty(toDelDir)) {
                        allDirs.add(toDelDir);
                        retVal.addTreeDeleteTarget(CollabConstants.DIRECTORY, toDelDir);
                        List<ProjectTreeNode> prjSubNodes = getSubNodesOfType(this.toString(), toDelDir, PROJECT);
                        prjSubNodes.forEach(projectTreeNode -> {
                            Project project = sqlEngine.getTypeMappingBmService().getById(Project.class, projectTreeNode.getDataId());
                            allSonPrjs.add(projectTreeNode.getDataId());
                            try {
                                List<Plan> subPlans = commandService.execute(new GetAllSubPlansCmd(project, sqlEngine));
                                subPlans.forEach(plan -> {
                                    allSonPlans.add(plan.getId());
                                    try {
                                        List<String> toDeleteTaskIds = commandService.execute(new GetChildTaskIdsCascade(PLAN, plan.getId(), null));
                                        allSonTasks.addAll(toDeleteTaskIds);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
                //delete
                IBusinessModel dirBm = this.bmService.getBusinessModelBySName(this.toString(), COLLAB_SCHEMA_ID, Table);
                this.sqlEngine.getBmService().delete(dirBm, CommonTools.list2String(allDirs));
                if (!CommonTools.isEmptyList(allSonPrjs)) {
                    IBusinessModel projectBm = this.bmService.getBusinessModelBySName(CollabConstants.PROJECT, COLLAB_SCHEMA_ID, Table);
                    this.sqlEngine.getBmService().delete(projectBm, CommonTools.list2String(allSonPrjs));
                    if (!CommonTools.isEmptyList(allSonPlans)) {
                        IBusinessModel planBm = this.bmService.getBusinessModelBySName(CollabConstants.PLAN, COLLAB_SCHEMA_ID, Table);
                        this.sqlEngine.getBmService().delete(planBm, CommonTools.list2String(allSonPlans));
                    }
                    if (!CommonTools.isEmptyList(allSonTasks)) {
                        IBusinessModel taskBm = this.bmService.getBusinessModelBySName(CollabConstants.TASK, COLLAB_SCHEMA_ID, Table);
                        this.sqlEngine.getBmService().delete(taskBm, CommonTools.list2String(allSonTasks));
                    }
                }
                //construct retVal
                allSonPrjs.forEach(targetId -> retVal.addTreeDeleteTarget(PROJECT, targetId));
                allSonPlans.forEach(targetId -> retVal.addTreeDeleteTarget(PLAN, targetId));
                allSonTasks.forEach(targetId -> retVal.addTreeDeleteTarget(TASK, targetId));
                retVal.setDeleteSuccess(true);
            } else {
                boolean hasProjectOrPlanDescendant = hasProjectOrPlanDescendant(curDataId, toDeleteDirIds);
                if (hasProjectOrPlanDescendant) {
                    retVal.setErrorMsg("子树存在项目或计划节点,不能删除");
                } else {
                    toDeleteDirIds.deleteCharAt(toDeleteDirIds.length() - 1);
                    String[] dirIds = toDeleteDirIds.toString().split(",");
                    for (String dirId : dirIds) {
                        retVal.addTreeDeleteTarget(this.toString(), dirId);
                    }
                    IBusinessModel dirBm = this.bmService.getBusinessModelBySName(this.toString(), COLLAB_SCHEMA_ID, Table);
                    this.sqlEngine.getBmService().delete(dirBm, toDeleteDirIds.toString());
                    retVal.setDeleteSuccess(true);
                }
            }
            return retVal;
        }

        @Override
        public CommonResponseData createDefaultRole(IBusinessModel businessModel, Map<String, String> dataMap) {
            return new CommonResponseData(true, "");
        }

        @Override
        public Pair<ProjectTreeNodeStrategy, Map<String, String>> getParentNodeData(Map<String, String> curNodeData) {
            IBusinessModel curBusinessModel = this.bmService.getBusinessModelBySName(this.toString(), COLLAB_SCHEMA_ID, Table);
            String parDirId = CommonTools.Obj2String(curNodeData.get(curBusinessModel.getS_table_name() + "_ID"));
            if (CommonTools.isNullString(parDirId)) {
                return null;
            }

            curBusinessModel.setReserve_filter(" AND ID='" + parDirId + "'");

            Map<String, String> data = (Map<String, String>) this.sqlEngine.getBmService().createModelQuery(curBusinessModel).list().get(0);

            return new Pair<>(this, data);
        }

        //this tree won't have too much layers, so preform a DFS
        private boolean hasProjectOrPlanDescendant(String curDataId, StringBuilder toDeleteDirIds) {
            List<ProjectTreeNode> prjSubNodes = getSubNodesOfType(this.toString(), curDataId, PROJECT);
            if (prjSubNodes.size() > 0) {
                return true;
            }

            List<ProjectTreeNode> planSubNodes = getSubNodesOfType(this.toString(), curDataId, PLAN);
            if (planSubNodes.size() > 0) {
                return true;
            }

            List<ProjectTreeNode> dirSubNodes = getSubNodesOfType(this.toString(), curDataId, DIRECTORY);
            for (ProjectTreeNode dirSubNode : dirSubNodes) {
                if (hasProjectOrPlanDescendant(dirSubNode.getDataId(), toDeleteDirIds)) {
                    return true;
                }
                toDeleteDirIds.append(dirSubNode.getDataId()).append(",");
            }

            return false;
        }

        public void getSonDirsWithSelf(String curDataId, StringBuilder toDeleteDirIds) {
            List<ProjectTreeNode> dirSubNodes = getSubNodesOfType(this.toString(), curDataId, DIRECTORY);
            for (ProjectTreeNode dirSubNode : dirSubNodes) {
                toDeleteDirIds.append(dirSubNode.getDataId()).append(",");
                getSonDirsWithSelf(dirSubNode.getDataId(), toDeleteDirIds);
            }
        }
    },
    ProjectNode(PROJECT) {
        @Override
        public List<ProjectTreeNode> getNextLayerNodes(FunctionModule functionModule, String curDataId) {
            List<ProjectTreeNode> retV = UtilFactory.newArrayList();
            retV.addAll(getSubNodesOfType(this.toString(), curDataId, PLAN));
            return retV;
        }

        @Override
        public TreeDeleteResult deleteNode(String curDataId) {
            TreeDeleteResult retV = new TreeDeleteResult();
            try {
                Project project = this.sqlEngine.getTypeMappingBmService().getById(Project.class, curDataId);
                List<Plan> subPlans = commandService.execute(new GetAllSubPlansCmd(project, sqlEngine));
                List<String> toDeletePlanIds = UtilFactory.newArrayList();
                for (Plan subPlan : subPlans) {
                    toDeletePlanIds.add(subPlan.getId());
                    retV.addTreeDeleteTarget(PLAN, subPlan.getId());
                }
                String toDelPlanIdString = CommonTools.listJoinCommaToString(toDeletePlanIds);
                List<String> toDeleteTaskIds = commandService.execute(new GetChildTaskIdsCascade(PLAN, toDelPlanIdString, null));
                toDeleteTaskIds.forEach(toDelTaskId -> retV.addTreeDeleteTarget(TASK, toDelTaskId));
                String toDelTaskIdString = CommonTools.listJoinCommaToString(toDeleteTaskIds);
                TreeDeleteTarget treeDeleteTarget = new TreeDeleteTarget(PROJECT, curDataId);
                retV.getTreeDeleteTargets().add(treeDeleteTarget);
                if (CollabConstants.COLLAB_DEBUG_MODEL || STATUS_UNSTARTED.equals(project.getStatus())) {
                    sqlEngine.getTypeMappingBmService().delete(Project.class, curDataId, false);
                    if (!CommonTools.isNullString(toDelPlanIdString)) {
                        sqlEngine.getTypeMappingBmService().delete(Plan.class, toDelPlanIdString, false);
                    }
                    if (!CommonTools.isNullString(toDelTaskIdString)) {
                        sqlEngine.getTypeMappingBmService().delete(Task.class, toDelTaskIdString, false);
                    }
                    retV.setDeleteSuccess(true);
                } else {
                    retV.setErrorMsg("只能删除未开始的项目!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return retV;
        }

        @Override
        public CommonResponseData createDefaultRole(IBusinessModel businessModel, Map<String, String> dataMap) {
            CommonResponseData retV = new CommonResponseData(true, "");
            doCreateDefaultRole(businessModel, dataMap);
            return retV;
        }

        @Override
        public Pair<ProjectTreeNodeStrategy, Map<String, String>> getParentNodeData(Map<String, String> curNodeData) {
            IBusinessModel dirBm = this.bmService.getBusinessModelBySName(DIRECTORY, COLLAB_SCHEMA_ID, Table);
            String parDirId = CommonTools.Obj2String(curNodeData.get(dirBm.getS_table_name() + "_ID"));
            if (CommonTools.isNullString(parDirId)) {
                return null;
            }

            dirBm.setReserve_filter(" AND ID='" + parDirId + "'");
            Map<String, String> data = (Map<String, String>) this.sqlEngine.getBmService().createModelQuery(dirBm).list().get(0);
            return new Pair<>(DirNode, data);
        }
    },
    PlanNode(PLAN) {
        @Override
        public List<ProjectTreeNode> getNextLayerNodes(FunctionModule functionModule, String curDataId) {
            List<ProjectTreeNode> retV = UtilFactory.newArrayList();
            if (functionModule == FunctionModule.PROJECT_MNG) {
                retV.addAll(getSubNodesOfType(this.toString(), curDataId, PLAN));
            } else if (functionModule == FunctionModule.PLAN_MNG) {
                retV.addAll(getSubNodesOfType(this.toString(), curDataId, TASK));
            }
            return retV;
        }

        @Override
        public TreeDeleteResult deleteNode(String curDataId) {

            TreeDeleteResult retV = new TreeDeleteResult();
            try {
                IBusinessModel planModel = this.bmService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
                IBusinessModel projectModel = this.bmService.getBusinessModelBySName(PROJECT, COLLAB_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
                Map planMap = this.sqlEngine.getBmService().createModelQuery(planModel).findById(curDataId);
                Plan plan = this.sqlEngine.getTypeMappingBmService().getById(Plan.class, curDataId);
                List<Plan> subPlans = commandService.execute(new GetAllSubPlansCmd(plan, sqlEngine));
                List<String> toDeletePlanIds = UtilFactory.newArrayList();
                toDeletePlanIds.addAll(subPlans.stream().map(Plan::getId).collect(Collectors.toList()));
                toDeletePlanIds.add(plan.getId());
                toDeletePlanIds.forEach(toDelPlanId -> retV.addTreeDeleteTarget(PROJECT, toDelPlanId));
                String toDelPlanIdString = CommonTools.listJoinCommaToString(toDeletePlanIds);
                List<String> toDeleteTaskIds = commandService.execute(new GetChildTaskIdsCascade(PLAN, toDelPlanIdString, null));
                toDeleteTaskIds.forEach(toDelTaskId -> retV.addTreeDeleteTarget(TASK, toDelTaskId));
                String toDelTaskIdString = CommonTools.listJoinCommaToString(toDeleteTaskIds);
                if (CollabConstants.COLLAB_DEBUG_MODEL) {
                    if (!CommonTools.isNullString(toDelPlanIdString)) {
                        sqlEngine.getTypeMappingBmService().delete(Plan.class, toDelPlanIdString, false);
                    }
                    if (!CommonTools.isNullString(toDelTaskIdString)) {
                        sqlEngine.getTypeMappingBmService().delete(Task.class, toDelTaskIdString, false);
                    }
                    retV.setDeleteSuccess(true);
                } else {
                    if (STATUS_UNSTARTED.equals(planMap.get("STATUS_" + planModel.getId()))) {
                        Pair<ProjectTreeNodeStrategy, Map<String, String>> parentNodeData = getParentNodeData(planMap);
                        while (parentNodeData != null && parentNodeData.fst != ProjectTreeNodeStrategy.ProjectNode) {
                            parentNodeData = parentNodeData.fst.getParentNodeData(parentNodeData.snd);
                        }
                        String projectStatus = CommonTools.Obj2String(parentNodeData.snd.get("STATUS_" + projectModel.getId()));
                        if (!STATUS_UNSTARTED.equals(projectStatus)) {
                            retV.setErrorMsg("项目已启动");
                            return retV;
                        }
                        if (!CommonTools.isNullString(toDelPlanIdString)) {
                            sqlEngine.getTypeMappingBmService().delete(Plan.class, toDelPlanIdString, false);
                        }
                        if (!CommonTools.isNullString(toDelTaskIdString)) {
                            sqlEngine.getTypeMappingBmService().delete(Task.class, toDelTaskIdString, false);
                        }
                        retV.setDeleteSuccess(true);
                        return retV;
                    } else {
                        retV.setErrorMsg("只能删除未开始的计划!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return retV;
        }

        @Override
        public CommonResponseData createDefaultRole(IBusinessModel businessModel, Map<String, String> dataMap) {
            CommonResponseData retV = new CommonResponseData(true, "");
            String dataId = CommonTools.Obj2String(dataMap.get("ID"));
            doCreateDefaultRole(businessModel, dataMap);
            return retV;
        }

        @Override
        public Pair<ProjectTreeNodeStrategy, Map<String, String>> getParentNodeData(Map<String, String> curNodeData) {
            IBusinessModel curBusinessModel = this.bmService.getBusinessModelBySName(this.toString(), COLLAB_SCHEMA_ID, Table);
            String parPlanId = CommonTools.Obj2String(curNodeData.get(curBusinessModel.getS_table_name() + "_ID"));

            if (CommonTools.isNullString(parPlanId)) {
                IBusinessModel projectBm = this.bmService.getBusinessModelBySName(PROJECT, COLLAB_SCHEMA_ID, Table);
                String parProjectId = CommonTools.Obj2String(curNodeData.get(projectBm.getS_table_name() + "_ID"));
                if (CommonTools.isNullString(parProjectId)) {
                    IBusinessModel dirBm = this.bmService.getBusinessModelBySName(DIRECTORY, COLLAB_SCHEMA_ID, Table);
                    String parDirId = CommonTools.Obj2String(curNodeData.get(dirBm.getS_table_name() + "_ID"));

                    projectBm.setReserve_filter("AND ID='" + parDirId + "'");
                    Map<String, String> data = (Map<String, String>) this.sqlEngine.getBmService().createModelQuery(dirBm).list().get(0);
                    return new Pair<>(DirNode, data);
                } else {
                    projectBm.setReserve_filter("AND ID='" + parProjectId + "'");
                    Map<String, String> data = (Map<String, String>) this.sqlEngine.getBmService().createModelQuery(projectBm).list().get(0);
                    return new Pair<>(ProjectNode, data);
                }
            } else {
                curBusinessModel.setReserve_filter("AND ID='" + parPlanId + "'");
                Map<String, String> data = (Map<String, String>) this.sqlEngine.getBmService().createModelQuery(curBusinessModel).list().get(0);
                return new Pair<>(PlanNode, data);
            }
        }
    },
    TaskNode(TASK) {
        @Override
        public List<ProjectTreeNode> getNextLayerNodes(FunctionModule functionModule, String curDataId) {
            List<ProjectTreeNode> retV = UtilFactory.newArrayList();
            retV.addAll(getSubNodesOfType(this.toString(), curDataId, TASK));
            return retV;
        }

        @Override
        public TreeDeleteResult deleteNode(String curDataId) {
            TreeDeleteResult retV = new TreeDeleteResult();
            Task task = this.sqlEngine.getTypeMappingBmService().getById(Task.class, curDataId);
            if (task == null) {
                retV.setErrorMsg("任务已删除");
            } else {
                if (CollabConstants.COLLAB_DEBUG_MODEL || STATUS_UNSTARTED.equals(task.getStatus())) {
                    try {
                        String pdKey = "";
                        if (!CommonTools.isNullString(task.getParPlanId())) {
                            Plan parPlan = this.sqlEngine.getTypeMappingBmService().getById(Plan.class, task.getParPlanId());
                            pdKey = PLAN + COLLAB_PD_NAME_SPERATOR + parPlan.getId();
                        } else if (!CommonTools.isNullString(task.getParTaskId())) {
                            Task parTask = this.sqlEngine.getTypeMappingBmService().getById(Task.class, curDataId);
                            pdKey = TASK + COLLAB_PD_NAME_SPERATOR + parTask.getId();
                        }
                        List<ProcessDefinition> processDefinitionList = processDefinitionBusiness.getAllPrcDefsWithPdKeyOrNameDescByVersion(pdKey, true);
                        if (processDefinitionList.size() > 0) {
                            ProcessDefinition processDefinition = processDefinitionList.get(0);
                            ProcessInstance pi = processInstanceBusiness.getLatestActiveProcessInstanceByPdId(processDefinition.getId());
                            if (!CollabConstants.COLLAB_DEBUG_MODEL && pi != null) {
                                retV.setErrorMsg("已有运行中流程,无法删除");
                                return retV;
                            }
                        }
                        List<String> toDeleteTaskIds = commandService.execute(new GetChildTaskIdsCascade(TASK, curDataId, null));
                        toDeleteTaskIds.add(curDataId);
                        toDeleteTaskIds.forEach(toDelTaskId -> retV.addTreeDeleteTarget(TASK, toDelTaskId));
                        String toDelTaskIdString = CommonTools.listJoinCommaToString(toDeleteTaskIds);
                        if (!CommonTools.isNullString(toDelTaskIdString)) {
                            sqlEngine.getTypeMappingBmService().delete(Task.class, toDelTaskIdString, false);
                        }
                        retV.setDeleteSuccess(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    retV.setErrorMsg("只可删除未开始的任务");
                }
            }
            return retV;
        }

        @Override
        public CommonResponseData createDefaultRole(IBusinessModel businessModel, Map<String, String> dataMap) {
            CommonResponseData retV = new CommonResponseData(true, "");
            doCreateDefaultRole(businessModel, dataMap);
            return retV;
        }

        @Override
        public Pair<ProjectTreeNodeStrategy, Map<String, String>> getParentNodeData(Map<String, String> curNodeData) {
            IBusinessModel curBusinessModel = this.bmService.getBusinessModelBySName(this.toString(), COLLAB_SCHEMA_ID, Table);
            String parTaskId = CommonTools.Obj2String(curNodeData.get(curBusinessModel.getS_table_name() + "_ID"));

            if (CommonTools.isNullString(parTaskId)) {
                IBusinessModel planBm = this.bmService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, Table);
                String parPlanId = CommonTools.Obj2String(curNodeData.get(planBm.getS_table_name() + "_ID"));
                if (CommonTools.isNullString(parPlanId)) {
                    return null;
                } else {
                    planBm.setReserve_filter("AND ID='" + parPlanId + "'");
                    Map<String, String> data = (Map<String, String>) this.sqlEngine.getBmService().createModelQuery(planBm).list().get(0);
                    return new Pair<>(PlanNode, data);
                }
            } else {
                curBusinessModel.setReserve_filter("AND ID='" + parTaskId + "'");
                Map<String, String> data = (Map<String, String>) this.sqlEngine.getBmService().createModelQuery(curBusinessModel).list().get(0);
                return new Pair<>(TaskNode, data);
            }
        }
    };


    abstract public List<ProjectTreeNode> getNextLayerNodes(FunctionModule functionModule, String curDataId);

    abstract public TreeDeleteResult deleteNode(String curDataId);

    abstract public CommonResponseData createDefaultRole(IBusinessModel businessModel, Map<String, String> dataMap);

    abstract public Pair<ProjectTreeNodeStrategy, Map<String, String>> getParentNodeData(Map<String, String> curNodeData);

    /**
     * @param collabTask
     * @param plan
     * @return the path up to project (a stack with upper at upper),
     * each map contains a key named modelName which is the model name
     */
    public Deque<Map<String, String>> getParentRouteInfo(Task collabTask, Plan plan) {
        Deque<Map<String, String>> retV = new ArrayDeque<>();

        IBusinessModel taskBm = this.bmService.getBusinessModelBySName(TASK, COLLAB_SCHEMA_ID, Table);
        IBusinessModel planBm = this.bmService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, Table);
        IBusinessModel projectBm = this.bmService.getBusinessModelBySName(PROJECT, COLLAB_SCHEMA_ID, Table);

        IBusinessModel parentBm = null;
        String parentDataId = null;
        if (collabTask != null) {
            parentBm = CommonTools.isNullString(collabTask.getParTaskId()) ? planBm : taskBm;
            parentDataId = CommonTools.isNullString(collabTask.getParTaskId()) ?
                    collabTask.getParPlanId() : collabTask.getParTaskId();
        } else {
            parentBm = CommonTools.isNullString(plan.getParPlanId()) ? projectBm : planBm;
            parentDataId = CommonTools.isNullString(plan.getParPlanId()) ?
                    plan.getParProjectId() : plan.getParPlanId();
        }

        while (parentBm != null) {
            Map<String, String> parentData = this.sqlEngine.getBmService().createModelQuery(parentBm).findById(parentDataId);
            String theId = CommonTools.Obj2String(parentData.get(parentBm.getS_table_name() + "_ID"));

            parentData.put(CollabConstants.MODEL_NAME, parentBm.getMatrix().getName());
            retV.push(parentData);
            if (!CommonTools.isNullString(theId)) {
                parentDataId = theId;
                continue;
            } else {
                if (parentBm.getMatrix().getName().equals(TASK)) {
                    parentBm = planBm;
                } else if (parentBm.getMatrix().getName().equals(PLAN)) {
                    parentBm = projectBm;
                } else {
                    break;
                }

                parentDataId = CommonTools.Obj2String(parentData.get(parentBm.getS_table_name() + "_ID"));
            }
        }

        return retV;
    }

    public CommonResponseData updateDefaultRole(IBusinessModel businessModel, String dataId, String oldAssigneeIds, String newAssigneeIds) {
        CommonResponseData retV = new CommonResponseData(true, "");
        if (this == DirNode) {
            return retV;
        }

        List<CollabRole> collabRoles = this.collabRoleService.list(Restrictions.eq(MODEL_NAME, this.toString()), Restrictions.eq(NODE_ID, dataId));
        for (CollabRole collabRole : collabRoles) {
            DefaultTeamRole defaultTeamRole = DefaultTeamRole.fromString(collabRole.getName());
            if (defaultTeamRole == null) {
                continue;
            }

            defaultTeamRole.updateRoleUsers(businessModel, dataId, collabRole, newAssigneeIds);
        }

        return retV;
    }

    public List<ProjectTreeNode> getSubNodesOfType(String curModelName, String curDataId, String subModelName) {

        IBusinessModel curBm = bmService.getBusinessModelBySName(curModelName, COLLAB_SCHEMA_ID, Table);
        IBusinessModel subBm = bmService.getBusinessModelBySName(subModelName, COLLAB_SCHEMA_ID, Table);

        subBm.setReserve_filter(" AND " + curBm.getS_table_name() + "_ID = '" + curDataId + "'");
        List<Map<String, Object>> oriDatas = sqlEngine.getBmService().createModelQuery(subBm).list();

        Collections.sort(oriDatas, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String order1 = CommonTools.Obj2String(o1.get(DISPLAY_ORDER_COL + subBm.getId()));
                String order2 = CommonTools.Obj2String(o2.get(DISPLAY_ORDER_COL + subBm.getId()));

                if ("".equals(order1) || "".equals(order2)) {
                    if ("".equals(order1) && "".equals(order2)) {
                        return 0;
                    }

                    if ("".equals(order1)) {
                        return -1;
                    } else {
                        return 1;
                    }
                }

                if (Double.valueOf(order1) > Double.valueOf(order2)) {
                    return 1;
                } else if (Double.valueOf(order1) < Double.valueOf(order2)) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        List<ProjectTreeNode> retV = UtilFactory.newArrayList();
        for (Map<String, Object> oriData : oriDatas) {
            ProjectTreeNode node = new ProjectTreeNode();
            node.setIconCls("icon-" + subModelName);
            node.setModelName(subModelName);
            node.setModelId(subBm.getId());
            node.setDataId(CommonTools.Obj2String(oriData.get("ID")));
            node.setId(subModelName + NODE_ID_SPLIT + node.getDataId());
            node.setText(CommonTools.Obj2String(oriData.get("NAME_" + subBm.getId())));
            node.setOrder(CommonTools.Obj2String(oriData.get("DISPLAY_ORDER_" + subBm.getId())));
            node.setStatus(CommonTools.Obj2String(oriData.get("STATUS_" + subBm.getId())));
            retV.add(node);
        }

        return retV;
    }

    protected void doCreateDefaultRole(IBusinessModel bm, Map<String, String> dataMap) {
        for (DefaultTeamRole defaultTeamRole : DefaultTeamRole.values()) {
            CollabRole role = new CollabRole();
            role.setNodeId(CommonTools.Obj2String(dataMap.get("NODE_ID")));
            //role.setModelName(this.toString());
            role.setName(defaultTeamRole.toString());
            List<CollabFunction> functions = this.collabFunctionService.list(Restrictions.eq(BELONGED_MODEL, this.toString()));
            role.setFunctions(new HashSet<>(functions));
            Set<String> userIds = defaultTeamRole.getDefaultUserIds(this, bm, dataMap);
            if (userIds.size() > 0) {
                List<CollabRole.User> users = this.userDAO.list(CollabRole.User.class, Restrictions.in("id", userIds));
                role.setUsers(new HashSet<>(users));
            }
            this.collabRoleService.save(role);
        }
    }

    ProjectTreeNodeStrategy(String nodeType) {
        this.nodeType = nodeType;
    }

    private final String nodeType;
    protected IBusinessModelService bmService = OrientContextLoaderListener.Appwac.getBean(IBusinessModelService.class);
    protected ISqlEngine sqlEngine = OrientContextLoaderListener.Appwac.getBean(ISqlEngine.class);
    protected CollabRoleService collabRoleService = OrientContextLoaderListener.Appwac.getBean(CollabRoleService.class);
    protected CollabFunctionService collabFunctionService = OrientContextLoaderListener.Appwac.getBean(CollabFunctionService.class);
    protected UserDAO userDAO = OrientContextLoaderListener.Appwac.getBean(UserDAO.class);
    protected CommandService commandService = OrientContextLoaderListener.Appwac.getBean(CommandService.class);
    protected ProcessInstanceBusiness processInstanceBusiness = OrientContextLoaderListener.Appwac.getBean("processInstanceBusiness", ProcessInstanceBusiness.class);
    protected ProcessDefinitionBusiness processDefinitionBusiness = OrientContextLoaderListener.Appwac.getBean("processDefinitionBusiness", ProcessDefinitionBusiness.class);

    @Override
    public String toString() {
        return nodeType;
    }

    private static final Map<String, ProjectTreeNodeStrategy> stringToEnum = UtilFactory.newHashMap();

    static {
        for (ProjectTreeNodeStrategy s : values()) {
            stringToEnum.put(s.toString(), s);
        }
    }

    public static ProjectTreeNodeStrategy fromString(String nodeType) {
        return stringToEnum.get(nodeType);
    }

    public static boolean contains(String type) {
        Set<String> defaultTypes = stringToEnum.keySet();
        return defaultTypes.contains(type);
    }

}
