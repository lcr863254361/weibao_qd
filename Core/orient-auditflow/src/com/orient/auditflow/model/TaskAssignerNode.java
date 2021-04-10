package com.orient.auditflow.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class TaskAssignerNode implements Serializable {

    private String taskName;

    private Boolean canChooseOther = false;

    private String assign;

    private String assign_display;

    //默认节点样式
    private String iconCls = "icon-audit-task";

    private Boolean leaf = false;

    private Boolean expanded = false;

    //子流程节点信息
    private List<TaskAssignerNode> results = new ArrayList<>();

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public List<TaskAssignerNode> getResults() {
        return results;
    }

    public void setResults(List<TaskAssignerNode> results) {
        this.results = results;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Boolean getCanChooseOther() {
        return canChooseOther;
    }

    public void setCanChooseOther(Boolean canChooseOther) {
        this.canChooseOther = canChooseOther;
    }

    public String getAssign() {
        return assign;
    }

    public void setAssign(String assign) {
        this.assign = assign;
    }

    public String getAssign_display() {
        return assign_display;
    }

    public void setAssign_display(String assign_display) {
        this.assign_display = assign_display;
    }
}
