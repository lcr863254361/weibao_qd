package com.orient.collab.model;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-12-29 14:59
 */
public class TreeDeleteTarget implements Serializable {

    private String modelName;

    private String dataId;

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

    public TreeDeleteTarget(String modelName, String dataId) {
        this.modelName = modelName;
        this.dataId = dataId;
    }
}
