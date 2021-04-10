package com.orient.collab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orient.utils.UtilFactory;

import java.util.List;

/**
 * represent a gantt plan
 *
 * @author Seraph
 *         2016-07-18 上午10:38
 */
public class GanttPlan extends Plan{

    public GanttPlan(){
        super();
    }

    public GanttPlan(Plan plan){
        this.setId(plan.getId());
        this.setStatus(plan.getStatus());
        this.setName(plan.getName());
        this.setPrincipal(plan.getPrincipal());
        this.setPlannedStartDate(plan.getPlannedStartDate());
        this.setPlannedEndDate(plan.getPlannedEndDate());
        this.setActualStartDate(plan.getActualStartDate());
        this.setActualEndDate(plan.getActualEndDate());
        this.setExecutor(plan.getExecutor());
        this.setType(plan.getType());
        this.setDisplayOrder(plan.getDisplayOrder());
        this.setProgress(plan.getProgress());
        this.setParDirId(plan.getParDirId());
        this.setParProjectId(plan.getParProjectId());
        this.setParPlanId(plan.getParPlanId());
    }

    private List<GanttPlan> children = UtilFactory.newArrayList();
    private boolean leaf;
    private String parentId;
    private String resourceName;
    private String preSib;
    private String nextSib;
    private boolean newCreate;
    private String iconCls;
    private String parentNodeId;

    @JsonProperty("Id")
    public String getId() {
        return super.getId();
    }

    public List<GanttPlan> getChildren() {
        return children;
    }

    public void setChildren(List<GanttPlan> children) {
        this.children = children;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getPreSib() {
        return preSib;
    }

    public void setPreSib(String preSib) {
        this.preSib = preSib;
    }

    public String getNextSib() {
        return nextSib;
    }

    public void setNextSib(String nextSib) {
        this.nextSib = nextSib;
    }

    public boolean isNewCreate() {
        return newCreate;
    }

    public void setNewCreate(boolean newCreate) {
        this.newCreate = newCreate;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(String parentNodeId) {
        this.parentNodeId = parentNodeId;
    }
}
