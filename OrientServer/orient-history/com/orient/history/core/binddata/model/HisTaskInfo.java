package com.orient.history.core.binddata.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26 0026.
 * 历史任务信息描述 序列化至数据库
 */
public class HisTaskInfo implements Serializable {

    private String taskId;

    private String taskType;

    private String taskName;

    private String taskAssigner;

    private String taskBeginTime;

    private String taskEndTime;

    List<TaskBindData> taskBindDataList = new ArrayList<>();

    public List<TaskBindData> getTaskBindDataList() {
        return taskBindDataList;
    }

    public void setTaskBindDataList(List<TaskBindData> taskBindDataList) {
        this.taskBindDataList = taskBindDataList;
    }

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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskAssigner() {
        return taskAssigner;
    }

    public void setTaskAssigner(String taskAssigner) {
        this.taskAssigner = taskAssigner;
    }

    public String getTaskBeginTime() {
        return taskBeginTime;
    }

    public void setTaskBeginTime(String taskBeginTime) {
        this.taskBeginTime = taskBeginTime;
    }

    public String getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }
}
