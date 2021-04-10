package com.orient.devdataobj.event;

import com.orient.devdataobj.event.param.GetDataObjParam;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * Created by mengbin on 16/8/6.
 * Purpose:
 * Detail:
 */
public class GetDataObjEvent extends OrientEvent {
    public GetDataObjEvent(Object source, GetDataObjParam params) {
        super(source, params);
    }
}
