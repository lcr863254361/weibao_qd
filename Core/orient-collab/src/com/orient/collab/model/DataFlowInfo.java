package com.orient.collab.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/27 0027.
 */
public class DataFlowInfo implements Serializable {

    List<DataFlowTransition> transitions = new ArrayList<>();

    List<DataFlowActivity> activities = new ArrayList<>();

    public List<DataFlowTransition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<DataFlowTransition> transitions) {
        this.transitions = transitions;
    }

    public List<DataFlowActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<DataFlowActivity> activities) {
        this.activities = activities;
    }

    public DataFlowInfo(List<DataFlowTransition> transitions, List<DataFlowActivity> activities) {
        this.transitions = transitions;
        this.activities = activities;
    }

    public DataFlowInfo() {

    }
}
