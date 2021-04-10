package com.orient.modeldata.eventListener;

import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.modeldata.event.DeleteModelDataEvent;
import com.orient.modeldata.eventParam.DeleteModelDataEventParam;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * 默认模型数据保存前数据处理
 */
@Component
public class DeleteModelDataListener extends OrientEventListener {

    @Autowired
    ModelDataBusiness modelDataBusiness;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == DeleteModelDataEvent.class || DeleteModelDataEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        DeleteModelDataEventParam eventSource = (DeleteModelDataEventParam) orientEvent.getParams();
        String modelId = eventSource.getModelId();
        String isCascade = eventSource.getIsCascade();
        modelDataBusiness.delete(modelId, eventSource.getToDelIds(),isCascade);
    }
}

