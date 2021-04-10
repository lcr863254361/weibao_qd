package com.orient.weibao.bean.flowPost;

import java.io.Serializable;

public class Content implements Serializable {
    private String taskId;
    private String divingName;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDivingName() {
        return divingName;
    }

    public void setDivingName(String divingName) {
        this.divingName = divingName;
    }
}
