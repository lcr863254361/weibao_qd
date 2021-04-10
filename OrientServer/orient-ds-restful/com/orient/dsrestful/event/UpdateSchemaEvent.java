package com.orient.dsrestful.event;

import com.orient.dsrestful.eventparam.UpdateSchemaParam;
import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * Created by GNY on 2018/4/2
 */
public class UpdateSchemaEvent extends OrientEvent{

    public UpdateSchemaEvent(Object source, UpdateSchemaParam params) {
        super(source, params);
    }

}
