package com.orient.collab.business.projectCore.flowListener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.model.Task;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.config.FlowType;
import com.orient.flow.extend.annotation.CommentInfo;
import com.orient.flow.extend.annotation.FlowTaskExecutionEventMarker;
import com.orient.flow.extend.extensions.FlowTaskExecutionEventListener;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.domain.flow.TaskDataRelation;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.sysmodel.service.flow.ITaskDataRelationService;
import com.orient.utils.CommonTools;
import org.hibernate.criterion.Restrictions;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.orient.collab.config.CollabConstants.*;
import static com.orient.flow.extend.extensions.FlowTaskExecutionEventListener.FLOW_END;
import static com.orient.flow.extend.extensions.FlowTaskExecutionEventListener.FLOW_END_ERROR;
import static com.orient.flow.extend.extensions.FlowTaskExecutionEventListener.FLOW_START;

/**
 * handle when a collab flow task is created or submitted
 *
 * @author Seraph
 *         2016-08-10 下午8:54
 */
@CommentInfo(displayName = "CollabFlowTaskCommonListener", allowSelect = false)
@FlowTaskExecutionEventMarker(exceptTasks = {FLOW_START, FLOW_END, FLOW_END_ERROR}, flowTypes = FlowType.Collab, order = -1)
public class CollabFlowTaskCommonListener implements FlowTaskExecutionEventListener {

    @Override
    public void triggered(ExecutionImpl execution, TaskImpl task, TaskDefinitionImpl taskDefinition) {
        doTaskInfoInit(execution.getProcessInstance().getId(), STATUS_PROCESSING, taskDefinition.getName(), true, task);
    }

    @Override
    public void left(ExecutionImpl execution, String activityName, TaskDefinitionImpl taskDefinition, String signalName) {
        doTaskInfoInit(execution.getProcessInstance().getId(), STATUS_FINISHED, taskDefinition.getName(), false, null);
    }

    private void doTaskInfoInit(String piId, String newStatus, String taskName, boolean taskStart, TaskImpl flowTask) {

        IFlowDataRelationService flowDataRelationService = OrientContextLoaderListener.Appwac.getBean(IFlowDataRelationService.class);
        List<FlowDataRelation> flowDataRelations = flowDataRelationService.list(Restrictions.eq(FlowDataRelation.PI_ID, piId),
                Restrictions.eq(FlowDataRelation.MAIN_TYPE, FlowType.Collab.toString()));
        if (!CommonTools.isEmptyList(flowDataRelations)) {
            FlowDataRelation flowDataRelation = flowDataRelations.get(0);
            ISqlEngine sqlEngine = OrientContextLoaderListener.Appwac.getBean(ISqlEngine.class);
            List<Task> tasks = sqlEngine.getTypeMappingBmService().get(Task.class, new CustomerFilter(Task.NAME, EnumInter.SqlOperation.Equal, taskName),
                    new CustomerFilter(PLAN.equalsIgnoreCase(flowDataRelation.getTableName()) ? Task.PAR_PLAN_ID : Task.PAR_TASK_ID, EnumInter.SqlOperation.Equal, flowDataRelation.getDataId()));
            if (!CommonTools.isEmptyList(tasks)) {
                Task task = tasks.get(0);
                task.setStatus(newStatus);
                String sDate = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
                if (taskStart) {//YYYY-MM-DD HH24:MI:SS
                    task.setActualStartDate(sDate);
                    ITaskDataRelationService taskDataRelationService = OrientContextLoaderListener.Appwac.getBean(ITaskDataRelationService.class);
                    TaskDataRelation taskDataRelation = new TaskDataRelation();
                    taskDataRelation.setDataId(task.getId());
                    taskDataRelation.setPiId(piId);
                    taskDataRelation.setTaskName(taskName);
                    taskDataRelation.setTableName(TASK);
                    taskDataRelation.setType(FlowType.Collab.toString());
                    taskDataRelation.setCreateTime(new Date());
                    taskDataRelation.setFlowTaskId(flowTask.getId());
                    taskDataRelationService.save(taskDataRelation);
                } else {
                    task.setActualEndDate(sDate);
                }
                sqlEngine.getTypeMappingBmService().update(task);
            }
        }
    }
}
