package com.orient.template.business.core.exception;

import com.orient.utils.exception.OrientBaseAjaxException;

/**
 * represent an exception while do template related operations
 *
 * @author Seraph
 *         2016-09-13 上午10:55
 */
public class TemplateOperationException extends OrientBaseAjaxException{

    public TemplateOperationException(String msg){
        super(msg, msg);
    }
}
