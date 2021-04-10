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
public class ExtenderOderList {

    private List<Listener> listenerList = new ArrayList<>();

    public List<Listener> getListenerList() {
        return listenerList;
    }

    public void setListenerList(List<Listener> listenerList) {
        this.listenerList = listenerList;
    }
}
