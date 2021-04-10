package com.orient.flow.extend.extensions.sysDefault;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.extend.annotation.CommentInfo;
import com.orient.flow.extend.annotation.FlowTaskExecutionEventMarker;
import com.orient.flow.extend.extensions.FlowTaskExecutionEventListener;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.service.flow.impl.FlowDataRelationService;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

import java.util.List;

import static com.orient.workflow.bean.JBPMInfo.FLOW_DATA_RELATION;

/**
 * listen to the collab flow's start event
 *
 * @author Seraph
 *         2016-07-04 上午11:27
 */
@CommentInfo(displayName = "defaultFlowStartListener", allowSelect = false)
@FlowTaskExecutionEventMarker(tasks = {FlowTaskExecutionEventListener.FLOW_START}, order = -1)
public class DefaultFlowStartListener implements FlowTaskExecutionEventListener{

    @Override
    public void triggered(ExecutionImpl execution, TaskImpl task, TaskDefinitionImpl taskDefinition) {
        Object flowDataRelations = execution.getVariable(FLOW_DATA_RELATION);

        FlowDataRelationService flowDataRelationService = OrientContextLoaderListener.Appwac.getBean(FlowDataRelationService.class);
        if(flowDataRelations != null){
            List<FlowDataRelation> flowDataRelationList = (List<FlowDataRelation>) flowDataRelations;
            for(FlowDataRelation flowDataRelation : flowDataRelationList){
                flowDataRelation.setPiId(execution.getProcessInstance().getId());
                flowDataRelationService.save(flowDataRelation);
            }
        }
    }

    @Override
    public void left(ExecutionImpl execution, String activityName, TaskDefinitionImpl taskDefinition, String signalName) {
        //the start and end of a flow does not trigger this;
    }
}
