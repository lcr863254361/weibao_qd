package com.orient.devdataobj.event;

import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * Created by mengbin on 16/7/22.
 * Purpose:
 * Detail:
 */
public class DataObjModifiedEvent extends OrientEvent {
    public DataObjModifiedEvent(Object source, OrientEventParams params) {
        super(source, params);
    }
}
