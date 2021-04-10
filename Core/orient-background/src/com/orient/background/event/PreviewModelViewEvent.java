package com.orient.background.event;

import com.orient.background.eventParam.PreviewModelViewEventParam;
import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * Created by enjoy on 2016/3/23 0023.
 */
public class PreviewModelViewEvent extends OrientEvent {
    public PreviewModelViewEvent(Object source,PreviewModelViewEventParam param) {
        super(source,param);
    }
}
