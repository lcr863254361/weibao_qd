package com.orient.history.core.expression.context;

import com.orient.history.core.engine.prepare.IPrepareIntermediator;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class HisTaskPreparerContext extends Context {

    private IPrepareIntermediator prepareIntermediator;

    public IPrepareIntermediator getPrepareIntermediator() {
        return prepareIntermediator;
    }

    public void setPrepareIntermediator(IPrepareIntermediator prepareIntermediator) {
        this.prepareIntermediator = prepareIntermediator;
    }

    public HisTaskPreparerContext(List<String> inputList) {
        super(inputList);
    }
}
