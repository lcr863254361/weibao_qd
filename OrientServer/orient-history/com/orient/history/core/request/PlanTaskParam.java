package com.orient.history.core.request;

/**
 * Created by qjs on 2016/11/25.
 * 保存计划历史信息使传递的封装参数类
 */
public class PlanTaskParam {
    private String taskId;
    private String taskType;
    private String rootModelName;
    private String rootDataId;
    private String rootModelId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getRootModelName() {
        return rootModelName;
    }

    public void setRootModelName(String rootModelName) {
        this.rootModelName = rootModelName;
    }

    public String getRootDataId() {
        return rootDataId;
    }

    public void setRootDataId(String rootDataId) {
        this.rootDataId = rootDataId;
    }

    public String getRootModelId() {
        return rootModelId;
    }

    public void setRootModelId(String rootModelId) {
        this.rootModelId = rootModelId;
    }
}
