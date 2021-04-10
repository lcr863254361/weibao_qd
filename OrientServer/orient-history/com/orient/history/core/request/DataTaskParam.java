package com.orient.history.core.request;

/**
 * Created by qjs on 2016/11/22.
 * 保存数据任务历史信息使传递的封装参数类
 */
public class DataTaskParam {
    private String dataId;
    private String taskType;
    private String modelName;
    private String taskId;

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
