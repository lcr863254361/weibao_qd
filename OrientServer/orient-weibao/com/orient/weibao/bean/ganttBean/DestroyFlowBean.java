package com.orient.weibao.bean.ganttBean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orient.collab.model.GanttPlan;
import com.orient.utils.UtilFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class DestroyFlowBean {

    @JsonProperty("Id")
    private String id;  //必须
    private String name;//必须
    private String startDate;//必须
    private String endDate;//必须
    private String taskType;
    private String taskDutor;
    @JsonProperty("PercentDone")
    private String  percentDone;  //必须
    private String type;

    private List<GanttPlan> children = UtilFactory.newArrayList();
    private boolean leaf; //必须
    private String parentId; //必须
    private String resourceName;
    private String preSib;
    private String nextSib;
    private boolean newCreate;
    private String iconCls;

    public String getPercentDone() {
        return percentDone;
    }

    public void setPercentDone(String percentDone) {
        this.percentDone = percentDone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskDutor() {
        return taskDutor;
    }

    public void setTaskDutor(String taskDutor) {
        this.taskDutor = taskDutor;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param ois
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
    }

    /**
     * @param objectOutputStreams
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream objectOutputStreams) throws IOException {
        objectOutputStreams.defaultWriteObject();
    }
}
