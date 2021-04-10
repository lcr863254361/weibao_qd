package com.orient.workflow.form.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskPlan implements Serializable{

	//serialVersionUID is
	
	private static final long serialVersionUID = 2603377910782082260L;
	
	private boolean isSubTask = false;
	
	private TaskPlan parentTask;
	
	private List<TaskPlan> childrenTask = new ArrayList<TaskPlan>();
	
	private String taskId;
	
	private String taskName;
	
	private String taskState;
	
	private String taskAssignee;
	
	private Map<String,Object> taskInfo;

	public boolean isSubTask() {
		return isSubTask;
	}

	public void setSubTask(boolean isSubTask) {
		this.isSubTask = isSubTask;
	}

	public TaskPlan getParentTask() {
		return parentTask;
	}

	public void setParentTask(TaskPlan parentTask) {
		this.parentTask = parentTask;
		parentTask.getChildrenTask().add(this);
		if(!parentTask.isSubTask())
		{
			parentTask.setSubTask(true);
		}
	}

	public List<TaskPlan> getChildrenTask() {
		return childrenTask;
	}

	public void setChildrenTask(List<TaskPlan> childrenTask) {
		this.childrenTask = childrenTask;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Map<String, Object> getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(Map<String, Object> taskInfo) {
		this.taskInfo = taskInfo;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public String getTaskAssignee() {
		return taskAssignee;
	}

	public void setTaskAssignee(String taskAssignee) {
		this.taskAssignee = taskAssignee;
	}
}
