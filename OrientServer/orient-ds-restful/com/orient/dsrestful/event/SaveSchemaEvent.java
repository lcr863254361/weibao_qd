package com.orient.dsrestful.event;

import com.orient.dsrestful.eventparam.SaveSchemaParam;
import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-31 10:12
 */
public class SaveSchemaEvent extends OrientEvent {

    public SaveSchemaEvent(Object source, SaveSchemaParam params) {
        super(source, params);
    }

}
