package com.orient.background.bean;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2017-07-13 8:04 PM
 */
public class ModelPathVO implements Serializable {

    private String modelId;

    private String modelName;

    private ModelPathVO nextModel;

    private Integer edgeType;

    private boolean manyToMany;

    private Integer position;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public ModelPathVO getNextModel() {
        return nextModel;
    }

    public void setNextModel(ModelPathVO nextModel) {
        this.nextModel = nextModel;
    }

    public Integer getEdgeType() {
        return edgeType;
    }

    public void setEdgeType(Integer edgeType) {
        this.edgeType = edgeType;
    }

    public boolean isManyToMany() {
        return manyToMany;
    }

    public void setManyToMany(boolean manyToMany) {
        this.manyToMany = manyToMany;
    }
}
