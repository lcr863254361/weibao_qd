package com.orient.collabdev.controller.startup.event;

import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * @Description
 * @Author GNY
 * @Date 2018/9/27 8:57
 * @Version 1.0
 **/
public class ManualStartUpPlanEvent extends OrientEvent {

    public ManualStartUpPlanEvent(Object source, ManualStartUpPlanParam params) {
        super(source, params);
    }

}
