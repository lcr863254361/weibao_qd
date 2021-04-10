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
public class BaseOrderList {

    private List<OrderValue> orderValueList = new ArrayList<>();

    public List<OrderValue> getOrderValueList() {
        return orderValueList;
    }

    public void setOrderValueList(List<OrderValue> orderValueList) {
        this.orderValueList = orderValueList;
    }
}
