package com.orient.pvm.event;

import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * Created by mengbin on 16/7/30.
 * Purpose:
 * Detail:
 */


public class ImportCheckTemplateEvent extends OrientEvent{

    public ImportCheckTemplateEvent(Object source, OrientEventParams params) {
        super(source, params);
    }
}
