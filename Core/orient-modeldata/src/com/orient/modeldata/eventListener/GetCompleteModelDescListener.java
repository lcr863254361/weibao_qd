package com.orient.modeldata.eventListener;

import com.orient.modeldata.event.GetModelDescEvent;
import com.orient.modeldata.eventParam.GetModelDescEventParam;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.modelDesc.model.OrientModelDesc;
import com.orient.web.modelDesc.operator.builder.IModelDescBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * 获取模型描述事件基本监听
 *
 * @author enjoy
 * @creare 2016-04-01 14:22
 */
@Component
public class GetCompleteModelDescListener extends OrientEventListener {

    @Autowired
    IModelDescBuilder modelDescBuilder;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == GetModelDescEvent.class || GetModelDescEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        //获取事件参数
        GetModelDescEventParam param = (GetModelDescEventParam) orientEvent.getParams();
        //获取模型ID
        String modelId = param.getModelId();
        String isView = param.getIsView();
        //获取模型全部描述 包括模型基本信息 以及 所有字段信息
        OrientModelDesc orientModelDesc = modelDescBuilder.getModelDescByModelId(modelId, isView);
        param.setOrientModelDesc(orientModelDesc);
    }
}
