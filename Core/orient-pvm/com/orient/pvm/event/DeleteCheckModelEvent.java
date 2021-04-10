package com.orient.pvm.event;

import com.orient.pvm.eventparam.DeleteCheckModelEventParam;
import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * Created by Administrator on 2016/8/10 0010.
 */
public class DeleteCheckModelEvent extends OrientEvent {

    public DeleteCheckModelEvent(Object source, DeleteCheckModelEventParam params) {
        super(source, params);
    }
}
