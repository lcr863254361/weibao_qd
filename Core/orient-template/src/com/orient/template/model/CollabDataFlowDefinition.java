package com.orient.template.model;

import com.orient.sysmodel.domain.collab.CollabDataFlow;
import com.orient.sysmodel.domain.collab.CollabJobCarvePos;

import java.io.Serializable;
import java.util.List;

/**
 * represent a data flow template node
 *
 * @author Seraph
 *         2016-10-13 下午2:33
 */
public class CollabDataFlowDefinition implements Serializable{

    transient private List<Long> childTaskIds;
    private String modelId;
    private List<CollabDataFlow> dataFlowTransitions;
    private List<CollabJobCarvePos> carvePoses;

    private static final long serialVersionUID =  1L;

    public CollabDataFlowDefinition(String modelId, List<Long> childTaskIds){
        this.modelId = modelId;
        this.childTaskIds = childTaskIds;
    }

    public List<CollabDataFlow> getDataFlowTransitions() {
        return dataFlowTransitions;
    }

    public void setDataFlowTransitions(List<CollabDataFlow> dataFlowTransitions) {
        this.dataFlowTransitions = dataFlowTransitions;
    }

    public List<CollabJobCarvePos> getCarvePoses() {
        return carvePoses;
    }

    public void setCarvePoses(List<CollabJobCarvePos> carvePoses) {
        this.carvePoses = carvePoses;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public List<Long> getChildTaskIds() {
        return childTaskIds;
    }

    public void setChildTaskIds(List<Long> childTaskIds) {
        this.childTaskIds = childTaskIds;
    }


}
