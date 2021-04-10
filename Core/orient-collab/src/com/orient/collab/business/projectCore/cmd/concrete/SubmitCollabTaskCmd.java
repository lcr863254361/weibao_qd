package com.orient.collab.business.projectCore.cmd.concrete;

import com.orient.collab.business.projectCore.cmd.Command;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.Task;
import com.orient.devdataobj.business.DataObjectBusiness;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.business.FlowTaskBusiness;
import com.orient.flow.extend.activity.CounterSignActivity;
import com.orient.flow.extend.activity.CounterSignInfo;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.flow.TaskDataRelation;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.sysmodel.service.flow.impl.TaskDataRelationService;
import com.orient.utils.UtilFactory;
import com.orient.web.util.UserContextUtil;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskService;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.jbpm.pvm.internal.wire.descriptor.ObjectDescriptor;
import org.jbpm.pvm.internal.wire.descriptor.StringDescriptor;
import org.jbpm.pvm.internal.wire.operation.FieldOperation;
import org.jbpm.pvm.internal.wire.operation.Operation;
import org.jbpm.pvm.internal.wire.usercode.UserCodeActivityBehaviour;
import org.jbpm.pvm.internal.wire.usercode.UserCodeReference;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static com.orient.collab.config.CollabConstants.TASK;
import static com.orient.sysmodel.domain.flow.TaskDataRelation.*;

/**
 * submit a collab task
 *
 * @author Seraph
 *         2016-08-12 上午10:09
 */
public class SubmitCollabTaskCmd implements Command<Boolean> {

    public SubmitCollabTaskCmd(Task task, String transition) {
        this.task = task;
        this.transition = transition;
    }

    @Override
    public Boolean execute() throws Exception {
        TaskDataRelationService taskDataRelationService = OrientContextLoaderListener.Appwac.getBean(TaskDataRelationService.class);

        TaskDataRelation taskDataRelation = taskDataRelationService.list(new Criterion[]{Restrictions.eq(TASK_NAME, task.getName()),
                Restrictions.eq(TABLE_NAME, TASK), Restrictions.eq(DATA_ID, task.getId())}, Order.desc(CREATE_TIME)).get(0); //there will only be one active task at the same time

        FlowTaskBusiness flowTaskBusiness = OrientContextLoaderListener.Appwac.getBean("flowTaskBusiness", FlowTaskBusiness.class);

        String flowTaskId = flowTaskBusiness.getCurTaskByName(taskDataRelation.getPiId(), taskDataRelation.getTaskName()).getId();
        if (CollabConstants.TASK_TYPE_COUNTERSIGN.equals(task.getType())) {
            ProcessEngine processEngine = OrientContextLoaderListener.Appwac.getBean(ProcessEngine.class);
            TaskService taskService = processEngine.getTaskService();
            TaskImpl flowTask = (TaskImpl) taskService.getTask(flowTaskId);
            TaskImpl counterSignTask = (TaskImpl) flowTask.getSubTasks().stream().filter(flowTaskIter ->
                    flowTaskIter.getAssignee().equals(UserContextUtil.getUserName())).findFirst().get();

            ProcessDefinitionImpl prcDefImpl = (ProcessDefinitionImpl) processEngine
                    .getRepositoryService().createProcessDefinitionQuery()
                    .processDefinitionId(flowTask.getProcessInstance().getProcessDefinitionId()).uniqueResult();
            ActivityImpl counterSignActivity = null;
            List<ActivityImpl> allActivity = (List<ActivityImpl>) prcDefImpl.getActivities();
            for (ActivityImpl activity : allActivity) {
                if (activity.getName().equals(flowTask.getName())) {
                    counterSignActivity = activity;
                }
            }

            UserCodeActivityBehaviour activityBehaviour = (UserCodeActivityBehaviour) counterSignActivity.getActivityBehaviour();
            Field customActivityReferenceField = ReflectionUtils.findField(UserCodeActivityBehaviour.class, "customActivityReference");
            customActivityReferenceField.setAccessible(true);
            UserCodeReference userCodeReference = (UserCodeReference) ReflectionUtils.getField(customActivityReferenceField, activityBehaviour);
            ObjectDescriptor descriptor = (ObjectDescriptor) userCodeReference.getDescriptor();
            List<Operation> operations = descriptor.getOperations();

            String passTransiton = null;
            String noPassTransiton = null;
            for (Operation operation : operations) {
                FieldOperation fieldOperation = (FieldOperation) operation;
                if ("passTransiton".equals(fieldOperation.getFieldName())) {
                    StringDescriptor stringDescriptor = (StringDescriptor) fieldOperation.getDescriptor();
                    Field textField = ReflectionUtils.findField(StringDescriptor.class, "text");
                    textField.setAccessible(true);
                    passTransiton = (String) ReflectionUtils.getField(textField, stringDescriptor);
                }
            }

            CounterSignInfo info = (CounterSignInfo) processEngine.getExecutionService().getVariable(flowTask.getProcessInstance().getId(), CounterSignActivity.COUNTERSIGN_INFO);
            Map<String, String> counterSignResult = UtilFactory.newHashMap();

            if (passTransiton.equals(this.transition)) {
                counterSignResult.put(CounterSignActivity.STRATEGY, CounterSignActivity.STRATEGY_PASS);
            } else {
                counterSignResult.put(CounterSignActivity.STRATEGY, CounterSignActivity.STRATEGY_NOPASS);
            }

            counterSignResult.put("user", UserContextUtil.getUserName());
            info.getCounterSignList().add(counterSignResult);
            processEngine.getExecutionService().setVariable(flowTask.getProcessInstance().getId(), "COUNTERSIGN_INFO", info);
            taskService.completeTask(counterSignTask.getId());
        } else {
            flowTaskBusiness.completeTask(flowTaskId, transition, UserContextUtil.getUserName());
            //升级设计数据的版本
            DataObjectBusiness dataObjectBusiness = OrientContextLoaderListener.Appwac.getBean(DataObjectBusiness.class);
            ISqlEngine sqlEngine = OrientContextLoaderListener.Appwac.getBean(ISqlEngine.class);
            String modelId = sqlEngine.getTypeMappingBmService().getModelId(Task.class);
            //TODO 代码待修改
           /* List<DataObjectEntity> dataObjectEntityList = dataObjectBusiness.getAllCurrentDataObject(modelId, task.getId(), 3, true);
            for (DataObjectEntity dataObjectEntity : dataObjectEntityList) {
                dataObjectBusiness.jobSubmitUpVersion(dataObjectEntity);
            }*/
        }
        return true;
    }

    private Task task;
    private String transition;
}
