package com.orient.collab.model;

/**
 * for front view
 *
 * @author Seraph
 *         2016-08-11 下午3:25
 */
public class CollabFlowTask extends Task{

    private String flowTaskId;
    private boolean groupTask;
    private String piId;
    private String group;

    public String getFlowTaskId() {
        return flowTaskId;
    }

    public void setFlowTaskId(String flowTaskId) {
        this.flowTaskId = flowTaskId;
    }

    public boolean isGroupTask() {
        return groupTask;
    }

    public void setGroupTask(boolean groupTask) {
        this.groupTask = groupTask;
    }

    public String getPiId() {
        return piId;
    }

    public void setPiId(String piId) {
        this.piId = piId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
