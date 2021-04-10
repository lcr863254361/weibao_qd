package com.orient.collab.business;

import com.orient.auditflow.config.AuditFlowType;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.businessmodel.service.impl.QueryOrder;
import com.orient.collab.business.strategy.DefaultTeamRole;
import com.orient.collab.business.strategy.ProjectTreeNodeStrategy;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.*;
import com.orient.flow.business.FlowTaskBusiness;
import com.orient.flow.business.ProcessDefinitionBusiness;
import com.orient.flow.business.ProcessInstanceBusiness;
import com.orient.flow.config.FlowType;
import com.orient.flow.util.FlowTypeHelper;
import com.orient.sqlengine.util.BusinessDataConverter;
import com.orient.sysmodel.dao.flow.FlowTaskDAO;
import com.orient.sysmodel.domain.collab.CollabDataTask;
import com.orient.sysmodel.domain.collab.CollabDataTaskHis;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.domain.flow.TaskDataRelation;
import com.orient.sysmodel.service.collab.impl.CollabRoleService;
import com.orient.sysmodel.service.flow.impl.FlowDataRelationService;
import com.orient.sysmodel.service.flow.impl.TaskDataRelationService;
import com.orient.utils.CommonTools;
import com.orient.utils.PageUtil;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.workflow.WorkFlowConstants;
import com.sun.org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.history.HistoryTaskQuery;
import org.jbpm.pvm.internal.history.model.HistoryActivityInstanceImpl;
import org.jbpm.pvm.internal.history.model.HistoryTaskImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.businessmodel.service.impl.CustomerFilter.BETWEEN_AND_FILTER_SPLIT;
import static com.orient.collab.config.CollabConstants.*;
import static com.orient.sysmodel.domain.collab.CollabRole.NODE_ID;
import static com.orient.sysmodel.domain.collab.CollabRole.MODEL_NAME;
import static com.orient.sysmodel.domain.flow.TaskDataRelation.*;

/**
 * my task business
 *
 * @author Seraph
 *         2016-07-25 上午11:02
 */
@Component
public class MyTaskBusiness extends BaseBusiness {

    public List<Plan> getMyPlans(String userId, Date startDate, Date endDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        IBusinessModel planBm = this.businessModelService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, Table);
        CustomerFilter processingFilter = new CustomerFilter(Plan.STATUS + "_" + planBm.getId(), EnumInter.SqlOperation.Equal, STATUS_PROCESSING, EnumInter.SqlConnection.Or);
        CustomerFilter suspendedFilter =new CustomerFilter(Plan.STATUS + "_" + planBm.getId(), EnumInter.SqlOperation.Equal, STATUS_SUSPENDED, EnumInter.SqlConnection.And);
        suspendedFilter.setParent(processingFilter);
        planBm.appendCustomerFilter(suspendedFilter);
        planBm.appendCustomerFilter(new CustomerFilter(Plan.PRINCIPAL + "_" + planBm.getId(), EnumInter.SqlOperation.Equal, userId));

        StringBuilder queryFilter = new StringBuilder();
        if (startDate != null && endDate != null) {

            queryFilter.append(" AND ").append("PLANNED_START_DATE").append("_").append(planBm.getId()).append(">= ")
                    .append("to_date('").append(sdf.format(startDate)).append("', 'yyyy-mm-dd hh24:mi:ss')");

            queryFilter.append(" AND ").append("PLANNED_END_DATE").append("_").append(planBm.getId()).append("<= ")
                    .append("to_date('").append(sdf.format(endDate)).append("', 'yyyy-mm-dd hh24:mi:ss')");

        } else if (startDate != null) {

            queryFilter.append(" AND ").append("PLANNED_START_DATE").append("_").append(planBm.getId()).append(">= ")
                    .append("to_date('").append(sdf.format(startDate)).append("', 'yyyy-mm-dd hh24:mi:ss')");

        } else if (endDate != null) {

            queryFilter.append(" AND ").append("PLANNED_END_DATE").append("_").append(planBm.getId()).append("<= ")
                    .append("to_date('").append(sdf.format(endDate)).append("', 'yyyy-mm-dd hh24:mi:ss')");

        }

        planBm.setReserve_filter(queryFilter.toString());
        List<Map<String, Object>> oriData = this.orientSqlEngine.getBmService().createModelQuery(planBm).list();
        List<Plan> plans = BusinessDataConverter.convertMapListToBeanList(planBm, oriData, Plan.class, true);

        return plans;
    }

    /**
     * 获取当前的节点前驱节点(如果是Plan,则获取前驱,如果是Task 则根据数据流的获取)
     *
     * @param modelId
     * @param dateId
     * @return
     */
    public List<ProjectTreeNode> getPreJob(String modelId, String dateId) {
        List<ProjectTreeNode> retList = UtilFactory.newArrayList();
        String planMoodelId = orientSqlEngine.getTypeMappingBmService().getModelId(Plan.class);
        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);
        if (planMoodelId.equals(modelId)) {
            List<Plan> plans = ganttBusiness.getPredecessorPlans(dateId);
            for (Plan plan : plans) {
                ProjectTreeNode node = new ProjectTreeNode();
                node.setDataId(plan.getId());
                node.setModelName(PLAN);
                node.setModelId(planMoodelId);
                node.setText(plan.getName());
                node.setLeaf(true);
                node.setIconCls("icon-plan");
                retList.add(node);
            }
        } else if (taskModelId.equals(modelId)) {
            List<Task> tasks = dataFlowBusiness.getPreTasks(dateId);
            for (Task task : tasks) {
                ProjectTreeNode node = new ProjectTreeNode();
                node.setDataId(task.getId());
                node.setModelName(TASK);
                node.setText(task.getName());
                node.setLeaf(true);
                node.setModelId(taskModelId);
                node.setIconCls("icon-task");
                retList.add(node);
            }
        }
        return retList;
    }

    private String getCollabTaskGroup(com.orient.collab.model.Task collabTask) {
        StringBuilder group = new StringBuilder();

        IBusinessModel planBm = this.businessModelService.getBusinessModelBySName(PLAN, COLLAB_SCHEMA_ID, Table);
        IBusinessModel projectBm = this.businessModelService.getBusinessModelBySName(PROJECT, COLLAB_SCHEMA_ID, Table);

        Deque<Map<String, String>> parentRoute = ProjectTreeNodeStrategy.TaskNode.getParentRouteInfo(collabTask, null);
        Map<String, String> parentInfo = null;
        IBusinessModel parentBm = null;
        int totalSize = parentRoute.size();
        while (totalSize > 0) {
            parentInfo = parentRoute.pop();
            totalSize--;

            String modelName = parentInfo.get(CollabConstants.MODEL_NAME);
            if (TASK.equals(modelName)) {
                continue;
            }

            if (PLAN.equals(modelName)) {
                parentBm = planBm;
            } else {
                parentBm = projectBm;
            }
            group.append(parentInfo.get("NAME_" + parentBm.getId())).append("-");

        }

        return group.deleteCharAt(group.length() - 1).toString();
    }


    public List<OrientHistoryTask> getMyHistoryTasks(String userId, String userName, Date filterStartDate, Date filterEndDate, String name, String groupType) {
        if (filterStartDate == null) {
            ZonedDateTime zdt = ZonedDateTime.now();
            zdt = zdt.minusMonths(1);
            filterStartDate = Date.from(zdt.toInstant());
        }

        if (filterEndDate == null) {
            filterEndDate = new Date();
        }

        List<OrientHistoryTask> orientHistoryTasks = UtilFactory.newArrayList();

        if (CollabConstants.COLLAB_ENABLE_PLAN_BREAK) {

            if (CommonTools.isNullString(groupType) || FlowType.Collab.toString().equals(groupType)) {
                orientHistoryTasks.addAll(getHistoryFlowTask(userName, filterStartDate, filterEndDate, name, groupType));
            }

            if (CommonTools.isNullString(groupType) || "dataTask".equals(groupType)) {
                orientHistoryTasks.addAll(getHisDataTask(userId, filterStartDate, filterEndDate, name));
            }
        }

        if (CommonTools.isNullString(groupType) || PLAN.equals(groupType)) {
            orientHistoryTasks.addAll(getHistoryPlans(userId, filterStartDate, filterEndDate, name));
        }

        if (CommonTools.isNullString(groupType) || FlowType.Audit.toString().equals(groupType)) {
            orientHistoryTasks.addAll(getHistoryFlowTask(userName, filterStartDate, filterEndDate, name, groupType));
        }

        return orientHistoryTasks;
    }

    private List<OrientHistoryTask> getHistoryPlans(String userId, Date filterStartDate, Date filterEndDate, String name) {
        List<OrientHistoryTask> orientHistoryTasks = UtilFactory.newArrayList();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<CustomerFilter> filters = UtilFactory.newArrayList();
        filters.add(new CustomerFilter(Plan.PRINCIPAL, EnumInter.SqlOperation.Equal, userId));

        filters.add(new CustomerFilter(Plan.ACTUAL_START_DATE, EnumInter.SqlOperation.BetweenAnd,
                simpleDateFormat.format(filterStartDate) + BETWEEN_AND_FILTER_SPLIT + simpleDateFormat.format(filterEndDate)));

        filters.add(new CustomerFilter(Plan.ACTUAL_END_DATE, EnumInter.SqlOperation.BetweenAnd,
                simpleDateFormat.format(filterStartDate) + BETWEEN_AND_FILTER_SPLIT + simpleDateFormat.format(filterEndDate), EnumInter.SqlConnection.And));

        filters.add(new CustomerFilter(Plan.STATUS, EnumInter.SqlOperation.Equal, STATUS_FINISHED));

        if (!CommonTools.isNullString(name)) {
            filters.add(new CustomerFilter(Plan.NAME, EnumInter.SqlOperation.Like, name));
        }
        List<Plan> historyPlans = this.orientSqlEngine.getTypeMappingBmService().get(Plan.class,
                QueryOrder.desc(Plan.ACTUAL_END_DATE), filters.toArray(new CustomerFilter[]{}));

        for (Plan hisPlan : historyPlans) {
            OrientHistoryTask.Builder orientHistoryTaskBuilder = new OrientHistoryTask.Builder(PLAN, hisPlan.getName(), "计划")
                    .actualStartDate(hisPlan.getActualStartDate()).actualEndDate(hisPlan.getActualEndDate())
                    .modelName(PLAN).dataId(hisPlan.getId()).description("计划").id(PLAN + "-" + hisPlan.getId());
            ;
            orientHistoryTasks.add(orientHistoryTaskBuilder.build());
        }

        return orientHistoryTasks;
    }

    private List<OrientHistoryTask> getHistoryFlowTask(String userName, Date filterStartDate, Date filterEndDate, String name, String groupType) {
        List<OrientHistoryTask> orientHistoryTasks = UtilFactory.newArrayList();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(filterEndDate);
        calendar.add(Calendar.DATE, 1);
        filterEndDate = calendar.getTime();
        List<HistoryTask> flowHistoryTasks = this.flowTaskBusiness.getHistoryTaskByAssignee_Time(userName, filterStartDate, filterEndDate);
        if (!CommonTools.isEmptyList(flowHistoryTasks)) {
            HistoryService hisSvc = processEngine.getHistoryService();
            HistoryTaskQuery historyTaskQuery = hisSvc.createHistoryTaskQuery();
            for (HistoryTask historyTask : flowHistoryTasks) {
                HistoryTaskImpl flowHistoryTask = (HistoryTaskImpl) historyTask;
                String flowTaskName = this.flowTaskBusiness.getHistoryActivityName(flowHistoryTask.getId());
                if (!CommonTools.isNullString(name) && !flowTaskName.contains(name)) {
                    continue;
                }
                String pdName = this.processDefinitionBusiness.getPdNameByExecutionId(flowHistoryTask.getExecutionId());
                FlowType flowType = FlowTypeHelper.getFlowType(pdName);
                String bindDataId = "";
                String description = "协同任务";
                String group = "协同任务";
                String hisPiId = this.processInstanceBusiness.getPrcInstIdByHisExecutionId(flowHistoryTask.getExecutionId());
                String auditType = "";
                String identifier = "";
                if (flowType == FlowType.Audit && (CommonTools.isNullString(groupType) || FlowType.Audit.toString().equals(groupType))) {
                    group = "审批任务";
                    identifier = flowType.toString() + "-" + flowHistoryTask.getId();
                    List<FlowDataRelation> flowDataRelations = this.flowDataRelationService.list(Restrictions.eq(FlowDataRelation.PI_ID, hisPiId));
                    if (!flowDataRelations.isEmpty()) {
                        String type = flowDataRelations.get(0).getMainType();
                        auditType = type;
                        AuditFlowType auditFlowType = AuditFlowType.fromString(type);
                        if (auditFlowType != null) {
                            description = auditFlowType.getDescription();
                        }
                    }
                } else if (flowType == FlowType.Collab && (CommonTools.isNullString(groupType) || FlowType.Collab.toString().equals(groupType))) {
                    String historyActivityId = flowTaskBusiness.getHistoryActivityId(flowHistoryTask.getId());
                    List<HistoryActivityInstance> historyActivityInstances = processEngine.getHistoryService().createHistoryActivityInstanceQuery().executionId(historyTask.getExecutionId()).activityName(flowTaskName).list();
                    HistoryActivityInstanceImpl currentHisActivityInstance = (HistoryActivityInstanceImpl) historyActivityInstances.stream().filter(historyActivityInstance -> {
                        HistoryActivityInstanceImpl historyActivityInstanceImpl = (HistoryActivityInstanceImpl) historyActivityInstance;
                        return historyActivityInstanceImpl.getDbid() == Long.valueOf(historyActivityId).longValue();
                    }).findFirst().get();
                    String flowTaskId = historyTask.getId();
                    if (WorkFlowConstants.ACTIVITY_TYPE_CUSTOM.equals(currentHisActivityInstance.getType())) {
                        String superTaskId = this.flowTaskDAO.getSuperTaskIdByHisTaskDBId(historyTask.getId());
                        if (!CommonTools.isNullString(superTaskId)) {
                            flowTaskId = superTaskId;
                        }
                    }

                    TaskDataRelation bindInfo = taskDataRelationService
                            .list(Restrictions.eq("flowTaskId", flowTaskId)).get(0);
                    bindDataId = bindInfo.getDataId();
                    identifier = flowType.toString() + "-" + flowHistoryTask.getId();
                } else {
                    continue;
                }

                OrientHistoryTask.Builder orientHistoryTaskBuilder = new OrientHistoryTask.Builder(flowType.toString(), flowTaskName, group)
                        .actualStartDate(simpleDateFormat.format(flowHistoryTask.getCreateTime()))
                        .actualEndDate(simpleDateFormat.format(flowHistoryTask.getEndTime()))
                        .piId(hisPiId).flowTaskId(flowHistoryTask.getId()).description(description).auditType(auditType).dataId(bindDataId).id(identifier);
                ;

                orientHistoryTasks.add(orientHistoryTaskBuilder.build());
            }
        }

        return orientHistoryTasks;
    }

    private List<OrientHistoryTask> getHisDataTask(String userId, Date filterStartDate, Date filterEndDate, String name) {
        List<OrientHistoryTask> orientHistoryTasks = UtilFactory.newArrayList();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(filterEndDate);
        calendar.add(Calendar.DATE, 1);
        filterEndDate = calendar.getTime();

        List<CollabDataTaskHis> hisDataTasks = dataTaskBusiness.getHisDataTasks(userId, filterStartDate, filterEndDate);
        String planModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Plan.class);
        String taskModelId = orientSqlEngine.getTypeMappingBmService().getModelId(Task.class);
        for (CollabDataTaskHis hisDataTask : hisDataTasks) {

            String hisTaskName = "";
            String hisTaskType = "dataTask";
            String modelName = "";
            if (hisDataTask.getTaskmodelid().equals(Long.valueOf(planModelId))) {
                Plan plan = orientSqlEngine.getTypeMappingBmService().getById(Plan.class, String.valueOf(hisDataTask.getDataid()));
                hisTaskName = plan.getName();
                modelName = PLAN;
            } else if (hisDataTask.getTaskmodelid().equals(Long.valueOf(taskModelId))) {
                Task task = orientSqlEngine.getTypeMappingBmService().getById(Task.class, String.valueOf(hisDataTask.getDataid()));
                hisTaskName = task.getName();
                modelName = TASK;
            }

            if (!CommonTools.isNullString(name) && !hisTaskName.contains(name)) {
                continue;
            }

            OrientHistoryTask.Builder orientHistoryTaskBuilder = new OrientHistoryTask.Builder(hisTaskType, hisTaskName, "数据任务")
                    .actualStartDate(null != hisDataTask.getTaketime() ? simpleDateFormat.format(hisDataTask.getTaketime()) : "")
                    .actualEndDate(simpleDateFormat.format(hisDataTask.getFinishtime()))
                    .flowTaskId(hisDataTask.getId())
                    .description(hisDataTask.getMessage()).modelName(modelName).dataId(String.valueOf(hisDataTask.getDataid())).modelId(String.valueOf(hisDataTask.getTaskmodelid()))
                    .id(hisTaskType + "-" + String.valueOf(hisDataTask.getDataid()) + "-" + (null != hisDataTask.getTaketime() ? hisDataTask.getTaketime().toString() : ""));

            orientHistoryTasks.add(orientHistoryTaskBuilder.build());
        }

        return orientHistoryTasks;
    }

    public ExtGridData<CollabFlowTask> getMyCollabTasks(String userName, Integer page, Integer limit, Date startDate, Date endDate, String taskName) {
        ExtGridData<CollabFlowTask> retV = new ExtGridData<>();

        List<CollabFlowTask> collabFlowTasks = UtilFactory.newArrayList();
        List<org.jbpm.api.task.Task> tasks = this.flowTaskBusiness.getCurrentTaskListOfType(userName, FlowType.Collab, taskName);

        if (startDate != null || endDate != null) {
            tasks = tasks.stream().filter(task1 -> {

                if (startDate != null && endDate != null) {
                    return task1.getCreateTime().after(startDate) && task1.getCreateTime().before(endDate);
                }

                if (startDate != null) {
                    return task1.getCreateTime().after(startDate);
                }

                if (endDate != null) {
                    return task1.getCreateTime().before(endDate);
                }

                return true;
            }).collect(Collectors.toList());
        }

        List<org.jbpm.api.task.Task> pagedTasks = PageUtil.page(tasks, page, limit);
        for (org.jbpm.api.task.Task task : pagedTasks) {
            TaskImpl taskImpl = (TaskImpl) task;
            String piId = taskImpl.getExecution().getProcessInstance().getId();

            List<TaskDataRelation> taskDataRelations = taskDataRelationService.list(new Criterion[]{Restrictions.eq(TASK_NAME, task.getName()),
                            Restrictions.eq(TABLE_NAME, TASK), Restrictions.eq(PI_ID, piId)}, Order.desc(TaskDataRelation.CREATE_TIME));

            if (taskDataRelations==null || taskDataRelations.size()==0) {
                continue;
            }
            Task collabTask = this.orientSqlEngine.getTypeMappingBmService().getById(Task.class, taskDataRelations.get(0).getDataId());

            if (CollabConstants.TASK_TYPE_COUNTERSIGN.equals(collabTask.getType()) && ((TaskImpl) task).getSubTasks().size() > 0) {
                continue;
            }
            try {
                CollabFlowTask collabFlowTask = new CollabFlowTask();
                BeanUtils.copyProperties(collabFlowTask, collabTask);
                collabFlowTask.setFlowTaskId(task.getId());

                CollabRole collabRole = this.collabRoleService.get(Restrictions.eq(MODEL_NAME, TASK),
                        Restrictions.eq(NODE_ID, collabTask.getId()),
                        Restrictions.eq(CollabRole.NAME, DefaultTeamRole.Executor.toString()));
                collabFlowTask.setPiId(piId);

                if (!CollabConstants.TASK_TYPE_COUNTERSIGN.equals(collabTask.getType())) {
                    collabFlowTask.setGroupTask(StringUtil.isEmpty(task.getAssignee()) && (taskImpl.getAllParticipants().size() > 1 || collabRole.getUsers().size() > 1));
                }
                collabFlowTask.setGroup(getCollabTaskGroup(collabTask));
                collabFlowTasks.add(collabFlowTask);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        retV.setResults(collabFlowTasks);
        retV.setSuccess(true);
        retV.setTotalProperty(tasks.size());
        return retV;
    }

    /**
     * modified by sunwp 增加查询和分页功能
     */
    /**
     * 获取当前的数据任务
     *
     * @param userId
     * @param page
     * @param limit
     * @param startDate
     * @param endDate
     * @param taskName  @return
     */
    public ExtGridData<DataTaskBean> getMyDataTasks(String userId, Integer page, Integer limit, Date startDate, Date endDate, String taskName) {
        ExtGridData<DataTaskBean> retV = new ExtGridData<>();
        List<DataTaskBean> beans = UtilFactory.newArrayList();
        List<CollabDataTask> tasks = taskBusiness.getCurrentTask(userId);
        try {
            if (startDate != null || endDate != null) {
                tasks = tasks.stream().filter(task1 -> {
                    if (startDate != null && endDate != null) {
                        return task1.getCreatetime().after(startDate) && task1.getCreatetime().before(endDate);
                    }
                    if (startDate != null) {
                        return task1.getCreatetime().after(startDate);
                    }
                    if (endDate != null) {
                        return task1.getCreatetime().before(endDate);
                    }
                    return true;
                }).collect(Collectors.toList());
            }
            List<CollabDataTask> pagedTasks = PageUtil.page(tasks, page, limit);

            for (CollabDataTask task : pagedTasks) {
                DataTaskBean bean = new DataTaskBean();
                PropertyUtils.copyProperties(bean, task);
                String taskId = String.valueOf(task.getDataid());

                Task orientTask = orientSqlEngine.getTypeMappingBmService().getById(Task.class, taskId);
                bean.setTaskName(orientTask.getName());
                String s = getCollabTaskGroup(orientTask);
                bean.setGroup(s);
                beans.add(bean);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        retV.setResults(beans);
        retV.setSuccess(true);
        retV.setTotalProperty(tasks.size());
        return retV;
    }

    @Autowired
    private FlowDataRelationService flowDataRelationService;
    @Autowired
    private CollabRoleService collabRoleService;
    @Autowired
    private TaskDataRelationService taskDataRelationService;
    @Autowired
    private FlowTaskBusiness flowTaskBusiness;
    @Autowired
    protected ProcessEngine processEngine;
    @Autowired
    @Qualifier("processDefinitionBusiness")
    private ProcessDefinitionBusiness processDefinitionBusiness;
    @Autowired
    @Qualifier("processInstanceBusiness")
    private ProcessInstanceBusiness processInstanceBusiness;

    @Autowired
    private DataTaskBusiness taskBusiness;

    @Autowired
    private GanttBusiness ganttBusiness;

    @Autowired
    DataFlowBusiness dataFlowBusiness;

    @Autowired
    private DataTaskBusiness dataTaskBusiness;

    @Autowired
    private FlowTaskDAO flowTaskDAO;
}

