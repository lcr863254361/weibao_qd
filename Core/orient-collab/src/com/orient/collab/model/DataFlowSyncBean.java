package com.orient.collab.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/27 0027.
 */
public class DataFlowSyncBean implements Serializable {

    private DataFlowInfo toSaveData;
    private Long[] toRemoveEdgeIds;
    private String modelName;
    private String dataId;

    public DataFlowInfo getToSaveData() {
        return toSaveData;
    }

    public void setToSaveData(DataFlowInfo toSaveData) {
        this.toSaveData = toSaveData;
    }

    public Long[] getToRemoveEdgeIds() {
        return toRemoveEdgeIds;
    }

    public void setToRemoveEdgeIds(Long[] toRemoveEdgeIds) {
        this.toRemoveEdgeIds = toRemoveEdgeIds;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }
}
