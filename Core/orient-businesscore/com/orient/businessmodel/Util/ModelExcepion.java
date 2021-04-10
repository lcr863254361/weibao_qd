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

package com.orient.businessmodel.Util;
/**
 * ClassName:SqlEngineExcepion
 * BusinessModel模块的异常处理
 * Reason:	 TODO ADD REASON
 *
 * @author   zhu longchao
 * @version  
 * @since    Ver 1.1
 * @Date	 Mar 2, 2012		4:33:50 PM
 *
 * @see 	 
 */
public class ModelExcepion extends RuntimeException {
	
	private static final long serialVersionUID = 2L;
	
	public ModelExcepion(){
		super();
	}
	
	public ModelExcepion(final String message,
            final Throwable cause){
		super(message,cause);
	}
	
	/**
     * {@link RuntimeException#RuntimeException(String)}
     */
    public ModelExcepion(final String message) {
        super(message);
    }

    /**
     * {@link RuntimeException#RuntimeException(Throwable)}
     */
    public ModelExcepion(final Throwable cause) {
        super(cause);
    }


}

