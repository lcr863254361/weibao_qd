package com.orient.auditflow.business;

import com.orient.auditflow.config.AuditFlowStatus;
import com.orient.auditflow.config.AuditFlowType;
import com.orient.auditflow.model.AuditFlowInfo;
import com.orient.auditflow.model.AuditFlowTask;
import com.orient.auditflow.model.TaskAssignerNode;
import com.orient.background.bean.AuditFlowTaskSettingEntityWrapper;
import com.orient.background.business.AuditFlowModelBindBusiness;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.business.FlowTaskBusiness;
import com.orient.flow.business.ProcessDefinitionBusiness;
import com.orient.flow.business.ProcessInstanceBusiness;
import com.orient.flow.config.FlowType;
import com.orient.flow.model.FlowTaskWithAssigner;
import com.orient.sysmodel.domain.flow.AuditFlowModelBindEntity;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.utils.CommonTools;
import com.orient.utils.PageUtil;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.ExtGridData;
import com.orient.workflow.WorkFlowConstants;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * the audit flow information business
 *
 * @author Seraph
 *         2016-08-01 上午10:13
 */
@Component
public class AuditFlowInfoBusiness {

    public AuditFlowInfo getAuditFlowInfo(String modelName, String dataId, String mainType) {
        List<FlowDataRelation> flowDataRelations = flowDataRelationService.list(new Criterion[]{
                        Restrictions.eq(FlowDataRelation.TABLE_NAME, modelName),
                        Restrictions.eq(FlowDataRelation.DATA_ID, dataId),
                        Restrictions.eq(FlowDataRelation.MAIN_TYPE, mainType)},
                Order.desc(FlowDataRelation.CREATE_TIME));

        if (flowDataRelations.size() < 1) {
            return new AuditFlowInfo(AuditFlowStatus.NotStarted, null, null, null);
        }

        ProcessInstanceBusiness processInstanceBusiness = OrientContextLoaderListener.Appwac.getBean("processInstanceBusiness", ProcessInstanceBusiness.class);
        FlowDataRelation flowDataRelation = flowDataRelations.get(0);
        HistoryProcessInstance historyProcessInstance = processInstanceBusiness.getHistoryProcessInstanceById(flowDataRelation.getPiId());
        if(historyProcessInstance == null){
            return new AuditFlowInfo(AuditFlowStatus.NotStarted, null, null, null);
        }

        if (!StringUtil.isEmpty(historyProcessInstance.getState())) {
            if (historyProcessInstance.getState().equals(WorkFlowConstants.STATE_ACTIVE)) {
                return new AuditFlowInfo(AuditFlowStatus.Active, historyProcessInstance.getProcessDefinitionId(), historyProcessInstance.getProcessInstanceId(), historyProcessInstance.getStartTime());
            } else if (historyProcessInstance.getState().equals(WorkFlowConstants.STATE_END_ERROR)) {
                AuditFlowInfo retV = new AuditFlowInfo(AuditFlowStatus.EndError, historyProcessInstance.getProcessDefinitionId(), historyProcessInstance.getProcessInstanceId(), historyProcessInstance.getStartTime());
                retV.setEndTime(historyProcessInstance.getEndTime());
                return retV;
            } else if (historyProcessInstance.getState().equals(WorkFlowConstants.STATE_END_CANCEL)) {
                AuditFlowInfo retV = new AuditFlowInfo(AuditFlowStatus.EndCancel, historyProcessInstance.getProcessDefinitionId(), historyProcessInstance.getProcessInstanceId(), historyProcessInstance.getStartTime());
                retV.setEndTime(historyProcessInstance.getEndTime());
                return retV;
            }
        }
        return new AuditFlowInfo(AuditFlowStatus.End, historyProcessInstance.getProcessDefinitionId(), historyProcessInstance.getProcessInstanceId(), historyProcessInstance.getStartTime());
    }

    public ExtGridData<AuditFlowTask> getAuditFlowTasks(String userName, int page, int limit, Date startDate, Date endDate, String taskName) {
        ExtGridData<AuditFlowTask> retV = new ExtGridData<>();

        List<AuditFlowTask> auditFlowTasks = UtilFactory.newArrayList();

        List<Task> tasks = this.flowTaskBusiness.getCurrentTaskListOfType(userName, FlowType.Audit, null);


        if(startDate !=null || endDate != null){
            tasks = tasks.stream().filter(task1 -> {

                if(startDate != null && endDate != null){
                    return task1.getCreateTime().after(startDate) && task1.getCreateTime().before(endDate);
                }

                if(startDate != null){
                    return task1.getCreateTime().after(startDate);
                }

                if(endDate != null){
                    return task1.getCreateTime().before(endDate);
                }

                return true;
            }).collect(Collectors.toList());
        }

        for (Task task : tasks) {
            TaskImpl taskImpl = (TaskImpl) task;
            String piId = taskImpl.getExecution().getProcessInstance().getId();
            List<FlowDataRelation> flowDataRelations = this.flowDataRelationService.list(Restrictions.eq(FlowDataRelation.PI_ID, piId));

            AuditFlowTask auditFlowTask = new AuditFlowTask();
            auditFlowTask.setFlowTaskId(task.getId());
            auditFlowTask.setName(task.getName());
            auditFlowTask.setPiId(piId);
            auditFlowTask.setId(task.getId());
            ProcessDefinition processDefinition = processDefinitionBusiness.getPrcDefByPrcInstId(piId);
            auditFlowTask.setPdName(processDefinition.getName());
            auditFlowTask.setPdId(processDefinition.getId());
            auditFlowTask.setGroupTask(StringUtil.isEmpty(task.getAssignee()));

            if (flowDataRelations.size() > 0) {
                FlowDataRelation flowDataRelation = flowDataRelations.get(0);
                String mainTypeName = flowDataRelations.get(0).getMainType();
                AuditFlowType type = AuditFlowType.fromString(mainTypeName);
                auditFlowTask.setAuditType(mainTypeName);
                auditFlowTask.setAuditTypeDescription(type == null ? mainTypeName : type.getDescription());

                List<AuditFlowModelBindEntity> auditFlowModelBindEntities = auditFlowModelBindBusiness.getModelBindPds(flowDataRelation.getTableName());
                auditFlowModelBindEntities.forEach(tmpModelBind -> {
                    if (auditFlowTask.getPdId().equals(tmpModelBind.getFlowName() + WorkFlowConstants.PROCESS_NAME_VERSION_CONNECTER + tmpModelBind.getFlowVersion())) {
                        auditFlowTask.setPdName(auditFlowTask.getPdName() + "-" + tmpModelBind.getRemark());
                    }
                });
            }

            auditFlowTasks.add(auditFlowTask);
        }

        retV.setResults(PageUtil.page(auditFlowTasks, page, limit));
        retV.setSuccess(true);
        retV.setTotalProperty(auditFlowTasks.size());
        return retV;
    }

    /**
     * @param pdId                         流程定义ID
     * @param auditFlowTaskSettingEntities 流程任务设置信息
     * @return 根据模型绑定的流程定义信息 获取流程定义下所有任务的执行人信息
     */
    public List<TaskAssignerNode> getAllTaskAssigner(String pdId, List<AuditFlowTaskSettingEntityWrapper> auditFlowTaskSettingEntities) {
        //返回值
        List<TaskAssignerNode> retVal = new ArrayList<>();
        //获取任务原始执行人信息
        List<FlowTaskWithAssigner> flowTaskWithAssigners = processDefinitionBusiness.getTasksAssignByPdId(pdId);
        auditFlowTaskSettingEntities.forEach(auditFlowTaskSettingEntityWrapper -> {
            TaskAssignerNode taskAssignerNode = new TaskAssignerNode();
            taskAssignerNode.setTaskName(auditFlowTaskSettingEntityWrapper.getTaskName());
            //TODO 暂未考虑子流程
            taskAssignerNode.setLeaf(true);
            taskAssignerNode.setCanChooseOther(auditFlowTaskSettingEntityWrapper.getCanAssignOther().equals(1l));
            FlowTaskWithAssigner jpdlTaskAssign = flowTaskWithAssigners.stream().filter(flowTaskWithAssigner -> flowTaskWithAssigner.getTaskName().equals(auditFlowTaskSettingEntityWrapper.getTaskName())).findFirst().get();
            taskAssignerNode.setAssign(CommonTools.list2String(jpdlTaskAssign.getTaskAssignerIds()));
            taskAssignerNode.setAssign_display(CommonTools.list2String(jpdlTaskAssign.getTaskAssignerDisplayNames()));
            retVal.add(taskAssignerNode);
        });
        return retVal;
    }

    public String getPiIdByBindModel(String modelId, String dataId) {
        Criterion[] criterions = new Criterion[]{Restrictions.eq("tableName", modelId), Restrictions.eq("dataId", dataId), Restrictions.eq("mainType", "ModelDataAudit")};
        List<FlowDataRelation> flowDataRelations = flowDataRelationService.list(criterions, Order.desc("id"));
        String retVal = CommonTools.isEmptyList(flowDataRelations) ? "" : flowDataRelations.get(0).getPiId();
        return retVal;
    }


    @Autowired
    private IFlowDataRelationService flowDataRelationService;
    @Autowired
    private FlowTaskBusiness flowTaskBusiness;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private ProcessDefinitionBusiness processDefinitionBusiness;
    @Autowired
    private ProcessInstanceBusiness processInstanceBusiness;

    @Autowired
    private AuditFlowModelBindBusiness auditFlowModelBindBusiness;
}
