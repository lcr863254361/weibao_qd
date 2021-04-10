package com.orient.dsrestful.listener.saveSchema;

import com.orient.dsrestful.event.SaveSchemaEvent;
import com.orient.dsrestful.eventparam.SaveSchemaParam;
import com.orient.metamodel.metaengine.impl.MetaUtilImpl;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.webservice.tools.ShareModelInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-04-03 15:58
 */
@Component
public class SaveFinalHandlerListener extends OrientEventListener {

    @Autowired
    MetaUtilImpl metaEngine;

    @Autowired
    ShareModelInitializer shareModelInitializer;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == SaveSchemaEvent.class || SaveSchemaEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        SaveSchemaParam param = (SaveSchemaParam) orientEvent.getParams();
        //刷新内存，把新增的schema添加到列表中
        metaEngine.refreshMetaData(param.getSchema().getId());
        shareModelInitializer.initSchema(param.getSchema());
    }

}
