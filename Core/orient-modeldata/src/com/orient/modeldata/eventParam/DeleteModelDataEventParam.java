package com.orient.modeldata.eventParam;

import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * Created by enjoy on 2016/3/23 0023.
 */
public class DeleteModelDataEventParam extends OrientEventParams{

   private String modelId;

    private Long[] toDelIds;

    private String isCascade;

    public String getIsCascade() {
        return isCascade;
    }

    public void setIsCascade(String isCascade) {
        this.isCascade = isCascade;
    }

    public Long[] getToDelIds() {
        return toDelIds;
    }

    public void setToDelIds(Long[] toDelIds) {
        this.toDelIds = toDelIds;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public DeleteModelDataEventParam(String modelId, Long[] toDelIds,String isCascade) {
        this.modelId = modelId;
        this.toDelIds = toDelIds;
        this.isCascade = isCascade;
    }
}

