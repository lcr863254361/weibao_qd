package com.orient.history.core.binddata.model;

import com.orient.history.core.binddata.handler.IBindDataHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/26 0026.
 * 历史任务绑定的模型数据描述
 */
public class BindModelData extends TaskBindData {

    public BindModelData() {
        super(IBindDataHandler.BIND_TYPE_MODELDATA);
    }

    //模型ID
    private String modelId;

    //模型描述
    private String modelDesc;

    //数据描述
    private List<Map> modelDataList = new ArrayList<>();

    public String getModelDesc() {
        return modelDesc;
    }

    public void setModelDesc(String modelDesc) {
        this.modelDesc = modelDesc;
    }

    public List<Map> getModelDataList() {
        return modelDataList;
    }

    public void setModelDataList(List<Map> modelDataList) {
        this.modelDataList = modelDataList;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
}
