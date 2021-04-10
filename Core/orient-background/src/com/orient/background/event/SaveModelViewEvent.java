package com.orient.background.event;

import com.orient.background.eventParam.SaveModelViewEventParam;
import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * Created by enjoy on 2016/3/22 0022.
 */
public class SaveModelViewEvent extends OrientEvent {

    public SaveModelViewEvent(Object source, SaveModelViewEventParam param) {
        super(source,param);
    }
}
