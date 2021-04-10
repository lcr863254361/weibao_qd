package com.orient.template.model;

import com.orient.utils.UtilFactory;

import java.io.Serializable;
import java.util.List;

/**
 * represent a comp template node
 *
 * @author Seraph
 *         2016-10-19 上午10:03
 */
public class CollabComp implements Serializable{

    private String modelId;
    transient private String dataId;
    private List<Long> componentIds = UtilFactory.newArrayList();
    private static final long serialVersionUID =  1L;

    public CollabComp(String modelId, String dataId){
        this.modelId = modelId;
        this.dataId = dataId;
    }

    public String getModelId() {
        return modelId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public List<Long> getComponentIds() {
        return componentIds;
    }

    public void setComponentIds(List<Long> componentIds) {
        this.componentIds = componentIds;
    }

}