package com.orient.background.bean;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-17 13:42
 */
public class ExportODSVO implements Serializable {

    private ModelPathVO modelPath;

    private Long dataId;

    private Long modelId;

    public ModelPathVO getModelPath() {
        return modelPath;
    }

    public void setModelPath(ModelPathVO modelPath) {
        this.modelPath = modelPath;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }
}
