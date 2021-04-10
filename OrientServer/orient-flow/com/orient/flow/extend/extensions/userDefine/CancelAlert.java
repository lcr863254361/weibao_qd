package com.orient.flow.extend.extensions.userDefine;

import com.orient.alarm.service.AlarmService;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.extend.annotation.FlowTaskExecutionEventMarker;
import com.orient.flow.extend.extensions.FlowTaskExecutionEventListener;
import net.sf.json.JSONObject;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

import java.util.ArrayList;
import java.util.List;

@FlowTaskExecutionEventMarker(exceptTasks = FlowTaskExecutionEventListener.FLOW_START)
public class CancelAlert implements FlowTaskExecutionEventListener {


    public void process(ExecutionImpl execution, boolean isTask, String taskName) {
        List<String> alarmInfoIds = getAlarmInfoIds(execution, isTask, taskName);
        for (String alarmInfoId : alarmInfoIds) {
            cancelAlarmInfo(alarmInfoId);
        }
    }

    private boolean cancelAlarmInfo(String alarmInfoId) {
        AlarmService alarmSvc = (AlarmService) OrientContextLoaderListener.Appwac.getBean("AlarmService");
        return alarmSvc.cancel(alarmInfoId);
    }

    private List<String> getAlarmInfoIds(ExecutionImpl execution, boolean isTask, String taskName) {
        String savedJSON = null;
        if (isTask) {
            savedJSON = (String) execution.getVariable(RegisterAlert.TASK_ALERT + taskName);
        } else {
            savedJSON = (String) execution.getVariable(RegisterAlert.FLOW_ALERT + execution.getProcessInstance().getId());
        }
        JSONObject var = JSONObject.fromObject(savedJSON);
        List<String> alarmInfoIds = new ArrayList<String>();
        if (savedJSON != null) {
            alarmInfoIds = (List<String>) var.get(RegisterAlert.ALARM_INFO_ID);
        }
        return alarmInfoIds;
    }

    @Override
    public void triggered(ExecutionImpl execution, TaskImpl task, TaskDefinitionImpl taskDefinition) {
        boolean isTask = true;
        ;
        if (task == null) {
            isTask = false;
        } else {
            process(execution, isTask, task.getName());
        }
    }

    @Override
    public void left(ExecutionImpl execution, String activityName, TaskDefinitionImpl taskDefinition, String signalName) {

    }
}
