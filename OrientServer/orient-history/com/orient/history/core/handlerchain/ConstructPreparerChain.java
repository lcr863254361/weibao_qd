package com.orient.history.core.handlerchain;

import com.orient.history.core.engine.prepare.IPrepareIntermediator;

/**
 * Created by Administrator on 2016/9/27 0027.
 *
 */
public interface ConstructPreparerChain {

    IPrepareIntermediator getPrepareChain(String taskType);
}
