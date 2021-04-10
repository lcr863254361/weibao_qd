package com.orient.history.core.engine.factory;

import com.orient.history.core.IHisTaskEngine;
import com.orient.history.core.engine.impl.HisPlanTaskEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
@Component
public class HisPlanTaskEngineFactory implements IHisTaskEngineFactory {

    @Autowired
    HisPlanTaskEngine hisPlanTaskEngine;

    @Override
    public IHisTaskEngine createHisTaskEngine() {
        return hisPlanTaskEngine;
    }
}
