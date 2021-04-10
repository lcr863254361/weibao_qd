/**
 * ArithService.java
 * com.orient.sysmodel.service.arith
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-3-20 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.service.arith;

import java.util.List;

import com.orient.sysmodel.domain.arith.Arith;

/**
 * ClassName:ArithService
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-3-20		上午08:43:59
 *
 * @see 	 
 */
public interface ArithService {

	/**
	 * 查找算法
	
	 * @Method: findById 
	
	 * TODO
	
	 * @param arithId
	 * @return
	
	 * @return Arith
	
	 * @throws
	 */
	public Arith findById(String arithId);
	
	/**
	 * 取得多个算法的名称
	
	 * @Method: getArithNames 
	
	 * TODO
	
	 * @param arithIds
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getArithNames(String arithIds);
	/**
	
	 * @Method: findAll 
	
	 * TODO
	
	 * @param 
	 * @return 算法集合
	
	 * @see com.orient.sysmodel.service.arith.ArithService#findById(java.lang.String) 
	
	 */
	public List findAll();
	
}

