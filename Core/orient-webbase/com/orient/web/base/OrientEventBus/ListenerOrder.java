/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.web.base.OrientEventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengbin on 16/3/25.
 * Purpose:
 * Detail:
 */
public class ListenerOrder {

    public List<EventSource> getEventSourceList() {
        return eventSourceList;
    }

    public void setEventSourceList(List<EventSource> eventSourceList) {
        this.eventSourceList = eventSourceList;
    }

    private  List<EventSource>  eventSourceList = new ArrayList<>();


}
