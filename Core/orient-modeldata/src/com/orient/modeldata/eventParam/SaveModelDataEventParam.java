package com.orient.modeldata.eventParam;

import com.orient.web.base.OrientEventBus.OrientEventParams;

import java.util.Map;

/**
 * Created by enjoy on 2016/3/23 0023.
 */
public class SaveModelDataEventParam extends OrientEventParams{

    private boolean createData;

    private String modelId;

    private Map dataMap;

    private Map<String,String> oriDataMap;

    public Map getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map dataMap) {
        this.dataMap = dataMap;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public boolean isCreateData() {
        return createData;
    }

    public void setCreateData(boolean createData) {
        this.createData = createData;
    }

    public Map<String,String> getOriDataMap() {
        return oriDataMap;
    }

    public void setOriDataMap(Map<String,String> oriDataMap) {
        this.oriDataMap = oriDataMap;
    }
}
