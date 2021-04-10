package com.orient.collabdev.controller.startup.event;

import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * @Description
 * @Author GNY
 * @Date 2018/9/17 14:02
 * @Version 1.0
 **/
public class StartUpProjectEvent extends OrientEvent {

    public StartUpProjectEvent(Object source, StartUpProjectParam params) {
        super(source, params);
    }

}
