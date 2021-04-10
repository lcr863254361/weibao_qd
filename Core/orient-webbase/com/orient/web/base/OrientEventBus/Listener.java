/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.web.base.OrientEventBus;

/**
 * Created by mengbin on 16/3/25.
 * Purpose:
 * Detail:
 */
public class Listener {
    private String listnerName = "";
    private int     order = 0;

    public String getListnerName() {
        return listnerName;
    }

    public void setListnerName(String listnerName) {
        this.listnerName = listnerName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
