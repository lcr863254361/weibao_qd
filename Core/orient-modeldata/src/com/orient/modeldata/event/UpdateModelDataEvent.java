package com.orient.modeldata.event;

import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * Created by enjoy on 2016/3/23 0023.
 */
public class UpdateModelDataEvent extends OrientEvent {

    public UpdateModelDataEvent(Object source, SaveModelDataEventParam eventParam) {
        super(source,eventParam);
    }
}
