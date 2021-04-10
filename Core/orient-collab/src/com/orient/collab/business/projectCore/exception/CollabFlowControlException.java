package com.orient.collab.business.projectCore.exception;

import com.orient.utils.exception.OrientBaseAjaxException;

/**
 * an exception happens when control collab flow
 *
 * @author Seraph
 *         2016-08-15 上午10:52
 */
public class CollabFlowControlException extends OrientBaseAjaxException{

    public CollabFlowControlException(String msg){
        super("",msg);
    }
}
