package com.orient.flow.extend.extensions;

import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

/**
 * listener to the Flow Task Execution Event
 *
 * @author Seraph
 *         2016-06-27 上午11:04
 */
public interface FlowTaskExecutionEventListener {

    void triggered(ExecutionImpl execution, TaskImpl task, TaskDefinitionImpl taskDefinition);

    void left(ExecutionImpl execution, String activityName, TaskDefinitionImpl taskDefinition, String signalName);

    static public final String FLOW_START = "start";
    static public final String FLOW_END = "end";
    static public final String FLOW_END_ERROR = "end-error";
    String COUNTER_SIGN = "COUNTERSIGN";
}

