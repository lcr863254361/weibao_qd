/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.login.controller;

import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventCheck;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * Created by mengbin on 16/3/25.
 * Purpose:
 * Detail:
 */
@Component
public class TestListener extends OrientEventListener{

    @Override
    @OrientEventCheck(checked = false)
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {


        return aClass == OrientEvent.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> aClass) {

        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        System.out.print( "execute===TestLicenser=="+ this.getOrder()+"\n");
    }


}
