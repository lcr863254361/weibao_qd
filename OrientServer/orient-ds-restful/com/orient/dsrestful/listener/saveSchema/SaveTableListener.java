package com.orient.dsrestful.listener.saveSchema;

import com.orient.dsrestful.event.SaveSchemaEvent;
import com.orient.dsrestful.eventparam.SaveSchemaParam;
import com.orient.metamodel.service.SaveTableService;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-31 10:32
 */
@Component
public class SaveTableListener extends OrientEventListener {

    @Autowired
    SaveTableService saveTableService;

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
        saveTableService.saveTables(param.getSchema());
    }

}
