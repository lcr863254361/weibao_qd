package com.orient.collab.business.projectCore.exception;

import com.orient.utils.exception.OrientBaseAjaxException;

/**
 * current state does not apply the current operation
 *
 * @author Seraph
 *         2016-07-21 上午11:30
 */
public class StateIllegalException extends OrientBaseAjaxException {

    public StateIllegalException(String err){
        super("",err);
    }
}
