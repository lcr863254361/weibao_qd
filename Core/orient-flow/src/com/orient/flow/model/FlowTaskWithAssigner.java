package com.orient.flow.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class FlowTaskWithAssigner implements Serializable {

    private String taskName;

    private List<String> taskAssignerIds = new ArrayList<>();

    private List<String> taskAssignerNames = new ArrayList<>();

    private List<String> taskAssignerDisplayNames = new ArrayList<>();

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public List<String> getTaskAssignerNames() {
        return taskAssignerNames;
    }

    public void setTaskAssignerNames(List<String> taskAssignerNames) {
        this.taskAssignerNames = taskAssignerNames;
    }

    public List<String> getTaskAssignerDisplayNames() {
        return taskAssignerDisplayNames;
    }

    public void setTaskAssignerDisplayNames(List<String> taskAssignerDisplayNames) {
        this.taskAssignerDisplayNames = taskAssignerDisplayNames;
    }

    public List<String> getTaskAssignerIds() {
        return taskAssignerIds;
    }

    public void setTaskAssignerIds(List<String> taskAssignerIds) {
        this.taskAssignerIds = taskAssignerIds;
    }
}
