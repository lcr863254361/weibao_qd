package com.orient.collabdev.business.version.event;

import com.orient.web.base.OrientEventBus.OrientEvent;


/**
 * 版本变化事件基类
 */
public class CollabVersionChangeEvent extends OrientEvent {


    public CollabVersionChangeEvent(Object source, CollabVersionChangeEventParam params) {
        super(source, params);
    }
}
