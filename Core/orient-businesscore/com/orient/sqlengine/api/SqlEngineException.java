/**
 * SqlEngineExcepion.java
 * com.orient.sqlengine.util
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 Mar 2, 2012 		zhu longchao
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sqlengine.api;
/**
 * ClassName:SqlEngineExcepion
 * SqlEngine模块的异常处理
 * Reason:	 TODO ADD REASON
 *
 * @author   zhu longchao
 * @version  
 * @since    Ver 1.1
 * @Date	 Mar 2, 2012		4:33:50 PM
 *
 * @see 	 
 */
public class SqlEngineException extends RuntimeException {
	
	private static final long serialVersionUID = -1328432839915898985L;
	
	public SqlEngineException(){
		super();
	}
	
	public SqlEngineException(final String message,
                              final Throwable cause){
		super(message,cause);
	}
	
	/**
     * {@link RuntimeException#RuntimeException(String)}
     */
    public SqlEngineException(final String message) {
        super(message);
    }

    /**
     * {@link RuntimeException#RuntimeException(Throwable)}
     */
    public SqlEngineException(final Throwable cause) {
        super(cause);
    }


}

