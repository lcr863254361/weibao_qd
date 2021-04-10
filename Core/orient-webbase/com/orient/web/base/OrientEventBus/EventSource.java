/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.web.base.OrientEventBus;

import com.fasterxml.jackson.databind.deser.Deserializers;

/**
 * Created by mengbin on 16/3/25.
 * Purpose:
 * Detail:
 */
public class EventSource {

    private  String name ="";
    private  String eventType = "";
    private ExtenderOderList extenderOderList = null;
    private BaseOrderList baseOrderList = null;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExtenderOderList getExtenderOderList() {
        return extenderOderList;
    }

    public void setExtenderOderList(ExtenderOderList extenderOderList) {
        this.extenderOderList = extenderOderList;
    }

    public BaseOrderList getBaseOrderList() {
        return baseOrderList;
    }

    public void setBaseOrderList(BaseOrderList baseOrderList) {
        this.baseOrderList = baseOrderList;
    }
}
