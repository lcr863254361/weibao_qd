package com.orient.flow.extend.demo;

import com.orient.flow.extend.annotation.CommentInfo;
import com.orient.flow.extend.annotation.FlowTaskExecutionEventMarker;
import com.orient.flow.extend.extensions.FlowTaskExecutionEventListener;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-06-30 下午2:24
 */
@CommentInfo(displayName = "示例扩展", description="这是一个例子")
@FlowTaskExecutionEventMarker(tasks = {FlowTaskExecutionEventListener.FLOW_START, FlowTaskExecutionEventListener.FLOW_END, "任务1"})
public class DemoTaskCommitEventListner implements FlowTaskExecutionEventListener{

    private Logger logger = LoggerFactory.getLogger(DemoTaskCommitEventListner.class);

    @Override
    public void triggered(ExecutionImpl execution, TaskImpl task, TaskDefinitionImpl taskDefinition) {
        logger.info("triggered");
    }

    @Override
    public void left(ExecutionImpl execution, String activityName, TaskDefinitionImpl taskDefinition, String signalName) {
        logger.info("left");
    }
}
