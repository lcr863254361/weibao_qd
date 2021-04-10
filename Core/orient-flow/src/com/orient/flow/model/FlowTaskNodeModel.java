package com.orient.flow.model;


import java.io.Serializable;

public class FlowTaskNodeModel implements Serializable {

    public static final String STATUS_COMPLETED = "completed";
    public static final String STATUS_PROCESSING = "processing";
    public static final String STATUS_UNSTARTED = "unstarted";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAssRealName() {
        return assRealName;
    }

    public void setAssRealName(String assRealName) {
        this.assRealName = assRealName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    private String name;
    private String status;
    private String assignee;
    private String taskId;
    private String endTime;
    private String assRealName;
    private String startTime;

}
