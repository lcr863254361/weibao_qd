package com.orient.history.core.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/30 0030.
 */
public class ModelDataRequest implements Serializable {

    //模型ID
    private String modelId;

    //绑定模板ID
    private String templateId;

    //模型数据集合
    private List<String> dataIds = new ArrayList<>();

    //数据集合
    private List<Map> dataList = new ArrayList<>();

    //自定义过滤条件
    private List<String> customFilters = new ArrayList<>();

    private Map<String, Object> extraData = new HashMap<>();

    public Map<String, Object> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, Object> extraData) {
        this.extraData = extraData;
    }

    public List<String> getCustomFilters() {
        return customFilters;
    }

    public void setCustomFilters(List<String> customFilters) {
        this.customFilters = customFilters;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public List<String> getDataIds() {
        return dataIds;
    }

    public void setDataIds(List<String> dataIds) {
        this.dataIds = dataIds;
    }

    public List<Map> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map> dataList) {
        this.dataList = dataList;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
