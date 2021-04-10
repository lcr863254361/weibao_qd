package com.orient.history.core.builder;

import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.binddata.model.TaskBindData;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public interface IHisTaskBindDataBuilder {

    IBindDataHandler getHandlerChain(String taskId);

    void constructBindData(IBindDataHandler bindDataHandler, String taskId, List<TaskBindData> taskBindDatas);
}
