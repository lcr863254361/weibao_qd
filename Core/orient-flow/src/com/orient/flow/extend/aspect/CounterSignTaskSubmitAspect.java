package com.orient.flow.extend.aspect;

import com.orient.flow.extend.activity.CounterSignActivity;
import com.orient.flow.extend.activity.CounterSignInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskService;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.jbpm.pvm.internal.wire.usercode.UserCodeActivityBehaviour;
import org.jbpm.pvm.internal.wire.usercode.UserCodeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/10 0010.
 */

@Component
public class CounterSignTaskSubmitAspect {

    public Object submitCounterSign(ProceedingJoinPoint point) throws Throwable {
        //获取任务信息
        Object[] args = point.getArgs();
        String taskId = (String) args[0];
        String outCome = (String) args[1];
        TaskService taskService = processEngine.getTaskService();
        TaskImpl task = (TaskImpl) taskService.getTask(taskId);
        ExecutionService executionService = processEngine.getExecutionService();
        if (null != task.getSuperTask()) {
            EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
            EnvironmentImpl env = null;
            try {
                env = environmentFactory.openEnvironment();
                ProcessDefinitionImpl processDefinition = task.getExecution().getProcessInstance().getProcessDefinition();
                ActivityImpl activity = processDefinition.getActivity(task.getName());
                UserCodeActivityBehaviour userCodeActivityBehaviour = (UserCodeActivityBehaviour) activity.getActivityBehaviour();
                UserCodeReference userCodeReference = userCodeActivityBehaviour.getCustomActivityReference();
                userCodeReference.setDescriptor(userCodeReference.getDescriptor());
                Object usedObject = userCodeReference.getObject(activity.getProcessDefinition());
                CounterSignActivity customActivity = (CounterSignActivity) usedObject;
                CounterSignInfo info = (CounterSignInfo) executionService.getVariable(task.getExecutionId(), "COUNTERSIGN_INFO");
                Map<String, String> map = new HashMap<>();
                map.put("user", task.getAssignee());
                map.put("strategy", outCome.equals(customActivity.getPassTransiton()) ? "PASS" : "NOPASS");
                info.getCounterSignList().add(map);
                executionService.setVariable(task.getExecutionId(), "COUNTERSIGN_INFO", info);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (env != null)
                    env.close();
            }
        }
        Object returnVal = point.proceed();
        return returnVal;
    }

    @Autowired
    ProcessEngine processEngine;

}
