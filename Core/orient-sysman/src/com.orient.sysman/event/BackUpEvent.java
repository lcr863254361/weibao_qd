package com.orient.sysman.event;

import com.orient.sysman.eventtParam.BackUpEventParam;
import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-06-29 18:04
 */
public class BackUpEvent extends OrientEvent {

    public BackUpEvent(Object source, BackUpEventParam params) {
        super(source, params);
    }
}
