package com.orient.history.core.support;

import com.orient.history.core.engine.factory.IHisTaskEngineFactory;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public interface IHisTaskSupport {

    IHisTaskEngineFactory getHisTaskEngineFactory(String hisTaskType);
}
