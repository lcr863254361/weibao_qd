package com.orient.dsrestful.event;

import com.orient.dsrestful.eventparam.DeleteSchemaParam;
import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-29 10:15
 */
public class DeleteSchemaEvent extends OrientEvent {

    public DeleteSchemaEvent(Object source, DeleteSchemaParam params) {
        super(source, params);
    }

}
