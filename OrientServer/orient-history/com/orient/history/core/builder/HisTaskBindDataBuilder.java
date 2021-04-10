package com.orient.history.core.builder;

import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.binddata.model.TaskBindData;
import com.orient.history.core.handlerchain.ConstructHandlerChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2016/10/4 0004.
 *
 */
@Component
public class HisTaskBindDataBuilder implements IHisTaskBindDataBuilder{

    @Autowired
    protected ConstructHandlerChain constructHandlerChain;

    @Override
    public IBindDataHandler getHandlerChain(String taskId) {
        return constructHandlerChain.getHandlerChain(taskId);
    }

    @Override
    public void constructBindData(IBindDataHandler bindDataHandler, String taskId, List<TaskBindData> taskBindDatas) {
        //保存绑定数据信息
        bindDataHandler.constructBindData(taskId, taskBindDatas);
    }
}
