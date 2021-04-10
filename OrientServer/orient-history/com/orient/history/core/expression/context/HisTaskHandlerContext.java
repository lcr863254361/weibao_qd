package com.orient.history.core.expression.context;

import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.expression.context.Context;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class HisTaskHandlerContext extends Context {

    private IBindDataHandler bindDataHandler;

    public IBindDataHandler getBindDataHandler() {
        return bindDataHandler;
    }

    public void setBindDataHandler(IBindDataHandler bindDataHandler) {
        this.bindDataHandler = bindDataHandler;
    }

    public HisTaskHandlerContext(List<String> inputList) {
        super(inputList);
    }
}
