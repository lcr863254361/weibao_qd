package com.orient.history.core.engine.prepare.impl;

import com.orient.history.core.engine.prepare.IPrepareIntermediator;
import com.orient.history.core.request.FrontViewRequest;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public abstract class AbstractPrepareIntermediator implements IPrepareIntermediator {

    protected IPrepareIntermediator lastPrepareIntermediator;

    public void doPrepare(FrontViewRequest frontViewRequest) {
        if (null != lastPrepareIntermediator) {
            lastPrepareIntermediator.doPrepare(frontViewRequest);
        }
    }

    public void setLastPrepareIntermediator(IPrepareIntermediator lastPrepareIntermediator) {
        this.lastPrepareIntermediator = lastPrepareIntermediator;
    }
}
