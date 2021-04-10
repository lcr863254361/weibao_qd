package com.orient.history.core.handlerchain;

import com.orient.history.core.binddata.handler.IBindDataHandler;

/**
 * Created by Administrator on 2016/9/27 0027.
 *
 */
public interface ConstructHandlerChain {

    IBindDataHandler getHandlerChain(String taskId);
}
