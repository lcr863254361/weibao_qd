package com.orient.flow.model;

import java.util.List;

/**
 * the next flow task information
 *
 * @author Seraph
 *         2016-08-04 下午2:22
 */
public class NextFlowNodeInfo {

    public NextFlowNodeInfo() {

    }

    public NextFlowNodeInfo(String name, String transition, String type) {
        this.name = name;
        this.transition = transition;
        this.type = type;
    }

    private String name;
    private String transition;

    /**
     * next node may not be task, if not, the nextFlowNodes will reflect the nodes after this node
     */
    private String type;
    private List<NextFlowNodeInfo> nextFlowNodes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransition() {
        return transition;
    }

    public void setTransition(String transition) {
        this.transition = transition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<NextFlowNodeInfo> getNextFlowNodes() {
        return nextFlowNodes;
    }

    public void setNextFlowNodes(List<NextFlowNodeInfo> nextFlowNodes) {
        this.nextFlowNodes = nextFlowNodes;
    }
}
