package com.orient.collab.business.projectCore.flowListener;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.business.PlanTaskProcessBusiness;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.Task;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.config.FlowType;
import com.orient.flow.extend.annotation.CommentInfo;
import com.orient.flow.extend.annotation.FlowTaskExecutionEventMarker;
import com.orient.flow.extend.extensions.FlowTaskExecutionEventListener;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.service.flow.impl.FlowDataRelationService;
import org.hibernate.criterion.Restrictions;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

import java.util.List;

import static com.orient.collab.config.CollabConstants.*;

/**
 * process collab flow end event
 *
 * @author Seraph
 *         2016-08-12 上午11:09
 */
@CommentInfo(displayName = "CollabFlowEndListener", allowSelect = false)
@FlowTaskExecutionEventMarker(tasks = {FlowTaskExecutionEventListener.FLOW_END, FlowTaskExecutionEventListener.FLOW_END_ERROR}, flowTypes = FlowType.Collab)
public class CollabFlowEndListener implements FlowTaskExecutionEventListener {

    @Override
    public void triggered(ExecutionImpl execution, TaskImpl flowTask, TaskDefinitionImpl taskDefinition) {
        ignoreUnprocessedCollabTask(execution);
    }

    private void ignoreUnprocessedCollabTask(ExecutionImpl execution) {
        FlowDataRelationService flowDataRelationService = OrientContextLoaderListener.Appwac.getBean(FlowDataRelationService.class);
        FlowDataRelation flowDataRelation = flowDataRelationService
                .get(Restrictions.eq(FlowDataRelation.PI_ID, execution.getProcessInstance().getId()),
                        Restrictions.eq(FlowDataRelation.MAIN_TYPE, FlowType.Collab.toString()));

        ISqlEngine sqlEngine = OrientContextLoaderListener.Appwac.getBean(ISqlEngine.class);
        List<Task> collabTasks = sqlEngine.getTypeMappingBmService().get(Task.class,
                new CustomerFilter(PLAN.equalsIgnoreCase(flowDataRelation.getTableName()) ? Task.PAR_PLAN_ID : Task.PAR_TASK_ID, EnumInter.SqlOperation.Equal, flowDataRelation.getDataId()));

        for (Task collabTask : collabTasks) {
            if (STATUS_FINISHED.equals(collabTask.getStatus())) {
                continue;
            }
            collabTask.setStatus(STATUS_IGNORED);
            sqlEngine.getTypeMappingBmService().update(collabTask);
        }
        //是否自动提交计划
        if (PLAN.equalsIgnoreCase(flowDataRelation.getTableName()) && CollabConstants.COLLAB_AUTO_EXECUTE_PLAN) {
            //TODO 未考虑父子计划 先校验是否可以提交计划 如果可以提交 则执行提交操作
            PlanTaskProcessBusiness planTaskProcessBusiness = OrientContextLoaderListener.Appwac.getBean(PlanTaskProcessBusiness.class);
            planTaskProcessBusiness.submitPlan(flowDataRelation.getDataId());
        }
    }

    @Override
    public void left(ExecutionImpl execution, String activityName, TaskDefinitionImpl taskDefinition, String signalName) {

    }
}
