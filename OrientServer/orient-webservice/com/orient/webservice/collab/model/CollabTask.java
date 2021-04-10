package com.orient.webservice.collab.model;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-09-26 16:31
 */
public class CollabTask {
    private String taskId;
    private String taskName;
    private String status;
    private String createTime;
    private String projectId;
    private String projectName;
    private String projectCreator;
    private String projectDesigner;
    private String note;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCreator() {
        return projectCreator;
    }

    public void setProjectCreator(String projectCreator) {
        this.projectCreator = projectCreator;
    }

    public String getProjectDesigner() {
        return projectDesigner;
    }

    public void setProjectDesigner(String projectDesigner) {
        this.projectDesigner = projectDesigner;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
