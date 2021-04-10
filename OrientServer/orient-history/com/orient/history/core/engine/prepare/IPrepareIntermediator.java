package com.orient.history.core.engine.prepare;

import com.orient.history.core.request.FrontViewRequest;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public interface IPrepareIntermediator {

    /**
     * @param frontViewRequest 前端输入
     * @return 结果存储于线程变量中
     */
    void doPrepare(FrontViewRequest frontViewRequest);

    void setLastPrepareIntermediator(IPrepareIntermediator lastPrepareIntermediator);
}
