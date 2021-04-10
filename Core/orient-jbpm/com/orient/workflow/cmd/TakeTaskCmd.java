package com.orient.workflow.cmd;

import org.jbpm.api.TaskService;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.history.HistoryEvent;
import org.jbpm.pvm.internal.history.events.TaskUpdated;
import org.jbpm.pvm.internal.task.TaskImpl;

public class TakeTaskCmd implements Command<Object> {
    //serialVersionUID is

    private static final long serialVersionUID = -5161185114675222055L;
    private String taskId;
    private String newAssignUserName;

    public TakeTaskCmd(String taskId, String newAssignUserName) {
        this.taskId = taskId;
        this.newAssignUserName = newAssignUserName;
    }

    public Object execute(Environment env) throws Exception {
        TaskService taskService = env.get(TaskService.class);
        TaskImpl task = (TaskImpl) taskService.getTask(taskId);
        task.take(newAssignUserName);
        //同步至历史任务中
        HistoryEvent.fire(new TaskUpdated(task), task.getExecution());
        return null;
    }

}
