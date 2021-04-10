package com.orient.collab.model;

import com.orient.utils.StringUtil;

import java.io.Serializable;

/**
 * 协同树种从任意层级获取根节点信息 文件夹除外
 *
 * @author Administrator
 * @create 2017-07-20 9:16
 */
public class ModelDataDescVO implements Serializable{

    private String modelId;

    private String dataId;

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


    public boolean isEmpy() {
        return StringUtil.isEmpty(modelId) && StringUtil.isEmpty(dataId);
    }

    public ModelDataDescVO() {
    }

    public ModelDataDescVO(String modelId, String dataId) {
        this.modelId = modelId;
        this.dataId = dataId;
    }
}
