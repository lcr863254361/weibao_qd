package com.orient.modeldata.bean;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-18 10:26
 */
public class SchemaNodeVO extends BaseNode implements Serializable{

    private String modelId;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
}
