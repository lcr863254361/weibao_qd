package com.orient.history.core.binddata.handler;

import com.orient.history.core.binddata.model.TaskBindData;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class AbstractBindDataHandler implements IBindDataHandler {

    IBindDataHandler nextBindDataHandler;

    @Override
    public void constructBindData(String taskId, List<TaskBindData> taskBindDatas) {
        if (null != nextBindDataHandler) {
            nextBindDataHandler.constructBindData(taskId, taskBindDatas);
        }
    }

    public void setNextBindDataHandler(IBindDataHandler nextBindDataHandler) {
        this.nextBindDataHandler = nextBindDataHandler;
    }
}
