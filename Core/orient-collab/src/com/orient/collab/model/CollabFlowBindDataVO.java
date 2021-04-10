package com.orient.collab.model;

import org.apache.commons.collections.map.HashedMap;

import java.io.Serializable;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-20 9:13
 */
public class CollabFlowBindDataVO implements Serializable{

    private String modelId;

    private String dataId;

    private String templateId;

    //主键显示值
    private String refShowName;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public CollabFlowBindDataVO(String modelId, String dataId, String templateId) {
        this.modelId = modelId;
        this.dataId = dataId;
        this.templateId = templateId;
    }

    public void setRefShowName(String refShowName) {
        this.refShowName = refShowName;
    }

    public String getRefShowName() {
        return refShowName;
    }

    private Map<String,String> extraData = new HashedMap();

    public Map<String, String> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, String> extraData) {
        this.extraData = extraData;
    }

}
