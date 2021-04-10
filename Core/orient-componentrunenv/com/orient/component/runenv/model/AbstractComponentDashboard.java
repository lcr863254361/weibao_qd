package com.orient.component.runenv.model;

import com.orient.sysmodel.domain.BaseBean;

import java.io.Serializable;

public class AbstractComponentDashboard extends BaseBean implements Serializable {



    private String projId;
    private String id;
    private String taskId;
    private String value;

    public AbstractComponentDashboard() {

    }

    public AbstractComponentDashboard(String id, String projectId, String taskId, String remark) {
        this.id = id;
        this.projId = projectId;
        this.taskId = taskId;
        this.value = remark;
    }

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
