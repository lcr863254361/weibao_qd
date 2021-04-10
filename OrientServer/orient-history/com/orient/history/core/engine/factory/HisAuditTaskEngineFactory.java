package com.orient.history.core.engine.factory;

import com.orient.history.core.IHisTaskEngine;
import com.orient.history.core.engine.impl.HisAuditTaskEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
@Component
public class HisAuditTaskEngineFactory implements IHisTaskEngineFactory {

    @Autowired
    HisAuditTaskEngine hisAuditTaskEngine;

    @Override
    public IHisTaskEngine createHisTaskEngine() {
        return hisAuditTaskEngine;

    }
}
