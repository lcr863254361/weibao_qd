package com.orient.history.core.binddata.model;

import com.orient.collab.model.DataFlowActivity;
import com.orient.collab.model.DataFlowTransition;
import com.orient.history.core.binddata.handler.IBindDataHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class BindDataFlowData extends TaskBindData {

    public BindDataFlowData() {
        super(IBindDataHandler.BIND_TYPE_DATAFLOWDATA);
    }

    //数据节点描述
    private List<DataFlowActivity> dataFlowActivityList = new ArrayList<>();

    //数据节点间连线描述
    private List<DataFlowTransition> dataFlowTransitions = new ArrayList<>();

    public List<DataFlowActivity> getDataFlowActivityList() {
        return dataFlowActivityList;
    }

    public void setDataFlowActivityList(List<DataFlowActivity> dataFlowActivityList) {
        this.dataFlowActivityList = dataFlowActivityList;
    }

    public List<DataFlowTransition> getDataFlowTransitions() {
        return dataFlowTransitions;
    }

    public void setDataFlowTransitions(List<DataFlowTransition> dataFlowTransitions) {
        this.dataFlowTransitions = dataFlowTransitions;
    }
}
