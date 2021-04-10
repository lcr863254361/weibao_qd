package com.orient.history.core.binddata.model;

import com.orient.flow.model.FlowTaskNodeModel;
import com.orient.history.core.binddata.handler.IBindDataHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26 0026.
 * 控制流描述
 */
public class BindControlFlowData extends TaskBindData {

    public BindControlFlowData() {
        super(IBindDataHandler.BIND_TYPE_CONTROLFLOWDATA);
    }

    //节点详细信息描述
    private List<FlowTaskNodeModel> flowTaskNodeModelList = new ArrayList<>();

    //jpdl描述
    private String jpdlDesc;

    //流程实例ID
    private String piId;

    //子流程描述
    private List<BindControlFlowData> sonControlFlowData = new ArrayList<>();

    public List<BindControlFlowData> getSonControlFlowData() {
        return sonControlFlowData;
    }

    public void setSonControlFlowData(List<BindControlFlowData> sonControlFlowData) {
        this.sonControlFlowData = sonControlFlowData;
    }

    public List<FlowTaskNodeModel> getFlowTaskNodeModelList() {
        return flowTaskNodeModelList;
    }

    public void setFlowTaskNodeModelList(List<FlowTaskNodeModel> flowTaskNodeModelList) {
        this.flowTaskNodeModelList = flowTaskNodeModelList;
    }

    public String getJpdlDesc() {
        return jpdlDesc;
    }

    public void setJpdlDesc(String jpdlDesc) {
        this.jpdlDesc = jpdlDesc;
    }

    public String getPiId() {
        return piId;
    }

    public void setPiId(String piId) {
        this.piId = piId;
    }
}
