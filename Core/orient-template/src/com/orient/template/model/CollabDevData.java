package com.orient.template.model;

import com.orient.sysmodel.domain.taskdata.DataObjectEntity;

import java.io.Serializable;
import java.util.List;

/**
 * represent a dev data node
 *
 * @author Seraph
 *         2016-10-14 上午10:54
 */
public class CollabDevData implements Serializable{

    private List<DataObjectEntity> bindDatas;
    transient private String modelId;
    transient private String dataId;

    private static final long serialVersionUID =  1L;

    public CollabDevData(String modelId, String dataId){
        this.modelId = modelId;
        this.dataId = dataId;
    }

    public List<DataObjectEntity> getBindDatas() {
        return bindDatas;
    }

    public void setBindDatas(List<DataObjectEntity> bindDatas) {
        this.bindDatas = bindDatas;
    }

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

}
