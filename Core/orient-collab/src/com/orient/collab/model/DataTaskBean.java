package com.orient.collab.model;

import com.orient.sysmodel.domain.collab.CollabDataTask;

/**
 * Created by mengbin on 16/8/29.
 * Purpose:
 * Detail:
 */
public class DataTaskBean extends CollabDataTask {

    protected String taskName;

    protected String group;//added by sunwp 增加了分组

    public String getGroup() {return group;}

    public void setGroup(String group) {this.group = group; }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
