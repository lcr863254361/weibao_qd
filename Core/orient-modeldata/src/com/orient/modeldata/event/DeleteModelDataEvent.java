package com.orient.modeldata.event;

import com.orient.modeldata.eventParam.DeleteModelDataEventParam;
import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-09-21 10:57
 */
public class DeleteModelDataEvent extends OrientEvent {

    public DeleteModelDataEvent(Object source, DeleteModelDataEventParam params) {
        super(source, params);
    }
}



