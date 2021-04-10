package com.orient.flow.extend.extensions.sysDefault;

import com.orient.flow.extend.annotation.CommentInfo;
import com.orient.flow.extend.annotation.FlowTaskExecutionEventMarker;
import com.orient.flow.extend.extensions.FlowTaskExecutionEventListener;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

/**
 * listen to the collab flow's end event
 *
 * @author Seraph
 *         2016-07-04 下午1:37
 */
@CommentInfo(displayName = "DefaultFlowEndListener", allowSelect = false)
@FlowTaskExecutionEventMarker(tasks = {FlowTaskExecutionEventListener.FLOW_START}, order = -1)
public class DefaultFlowEndListener implements FlowTaskExecutionEventListener {
    @Override
    public void triggered(ExecutionImpl execution, TaskImpl task, TaskDefinitionImpl taskDefinition) {

    }

    @Override
    public void left(ExecutionImpl execution, String activityName, TaskDefinitionImpl taskDefinition, String signalName) {
        //the start and end of a flow does not trigger this;
    }
}
