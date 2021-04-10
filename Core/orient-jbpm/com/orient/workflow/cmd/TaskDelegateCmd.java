package com.orient.workflow.cmd;

import org.jbpm.api.TaskService;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.task.Participation;
import org.jbpm.pvm.internal.task.TaskImpl;

public class TaskDelegateCmd implements Command<Object> {
    //serialVersionUID is

    private static final long serialVersionUID = -5161185114675222055L;
    private String taskId;
    private String newAssignUserName;

    public TaskDelegateCmd(String taskId, String newAssignUserName) {
        this.taskId = taskId;
        this.newAssignUserName = newAssignUserName;
    }

    public Object execute(Environment env) throws Exception {
        TaskService taskService = env.get(TaskService.class);
        TaskImpl task = (TaskImpl) taskService.getTask(taskId);
        taskService.addTaskParticipatingUser(taskId, newAssignUserName, Participation.REPLACED_ASSIGNEE);
        taskService.addTaskComment(taskId, "任务从" + task.getAssignee() + " 指定到" + newAssignUserName);
        task.setAssignee(newAssignUserName);
        task.setNew(false);
        taskService.saveTask(task);
        return null;
    }

}
