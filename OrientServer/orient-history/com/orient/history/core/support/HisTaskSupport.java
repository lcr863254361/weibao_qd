package com.orient.history.core.support;

import com.orient.history.core.engine.factory.IHisTaskEngineFactory;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class HisTaskSupport implements IHisTaskSupport {

    @Override
    public IHisTaskEngineFactory getHisTaskEngineFactory(String hisTaskType) {
        return hisTaskEngineFactoryMap.get(hisTaskType);
    }

    private Map<String, IHisTaskEngineFactory> hisTaskEngineFactoryMap;

    public void setHisTaskEngineFactoryMap(Map<String, IHisTaskEngineFactory> hisTaskEngineFactoryMap) {
        this.hisTaskEngineFactoryMap = hisTaskEngineFactoryMap;
    }
}
