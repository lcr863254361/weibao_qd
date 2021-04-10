/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.web.base.OrientEventBus;

import org.springframework.context.ApplicationEvent;

/**
 * Created by mengbin on 16/3/24.
 * Purpose:
 * Detail:
 */
public class OrientEvent extends ApplicationEvent {

    protected OrientEventParams params = new OrientEventParams();

    public OrientEvent(Object source,OrientEventParams params) {
        super(source);
        if(params!=null){
            this.params = params;
        }


    }

    public OrientEventParams getParams() {
        return params;
    }

    public void setParams(OrientEventParams params) {
        this.params = params;
    }

    public boolean isAboard(){
        return  this.params.isbAboard();
    }

    public void aboardEvetn(){
        this.getParams().setbAboard(true);
    }
}
