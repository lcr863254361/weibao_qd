package com.orient.history.core.binddata.handler;

import com.orient.history.core.binddata.model.TaskBindData;

import java.util.List;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public interface IBindDataHandler {



    //模型数据
    public static final String BIND_TYPE_MODELDATA = "modelData";

    //系统数据
    public static final String BIND_TYPE_SYSDATA = "sysData";

    //数据流数据
    public static final String BIND_TYPE_DATAFLOWDATA = "dataFlowData";

    //控制流数据
    public static final String BIND_TYPE_CONTROLFLOWDATA = "controlFlowData";

    //任务意见
    public static final String BIND_TYPE_OPINIONDATA = "opinionData";

    //freemarker
    public static final String BIND_TYPE_FREEMARKER = "freeMarker";

    //额外处理
    public static final String BIND_TYPE_EXTRADATA = "extraData";

    void constructBindData(String taskId, List<TaskBindData> taskBindDatas);

    void setNextBindDataHandler(IBindDataHandler nextBindDataHandler);
}
