package com.orient.workflow.cmd;

import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.workflow.bean.AssignUser;
import com.orient.workflow.bean.JBPMInfo;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.TaskService;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ExecutionImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * change the task assign
 */
public class ChangeTaskAssignCommand implements Command<Object> {

    private static final long serialVersionUID = -5161185114675222055L;
    private String taskId;
    private List<String> newAssignUserName;
    private String toSetTaskName;

    public ChangeTaskAssignCommand(String taskId, List<String> newAssignUserName, String toSetTaskName) {
        this.taskId = taskId;
        this.newAssignUserName = newAssignUserName;
        this.toSetTaskName = toSetTaskName;
    }

    public Object execute(Environment env) throws Exception {
        TaskService taskService = env.get(TaskService.class);
        Task task = taskService.getTask(taskId);
        String executionId = task.getExecutionId();
        ExecutionService executionService = env.get(ExecutionService.class);
        ExecutionImpl execution = (ExecutionImpl) executionService.findExecutionById(executionId);
        //向上寻找主流程 防止分支
        ExecutionImpl mainExecution = execution.getProcessInstance();
        while (mainExecution.getProcessInstance().getSuperProcessExecution() != null) {
            mainExecution = mainExecution.getSuperProcessExecution()
                    .getProcessInstance();
        }
        Map<String, AssignUser> taskAssignUser = (Map<String, AssignUser>) executionService.getVariable(mainExecution.getId(), JBPMInfo.DynamicUserAssign);
        String taskName = task.getName();
        AssignUser assignuser = new AssignUser();
        if (newAssignUserName.size() > 1) {
            //group task
            assignuser.setCandidateUsers(CommonTools.list2String(newAssignUserName));
        } else if (newAssignUserName.size() == 1) {
            assignuser.setCurrentUser(newAssignUserName.get(0));
        }
        taskAssignUser = null == taskAssignUser ? new HashMap<>() : taskAssignUser;
        taskAssignUser.put(StringUtil.isEmpty(this.toSetTaskName) ? taskName : toSetTaskName, assignuser);
        final Map<String, AssignUser> finalTaskAssignUser = taskAssignUser;
        executionService.setVariables(mainExecution.getId(), new HashMap<String, Map<String, AssignUser>>() {{
            put(JBPMInfo.DynamicUserAssign, finalTaskAssignUser);
        }});
        return null;
    }

}
