/**
 * IFunctionTbom.java
 * com.orient.sysmodel.roleengine.operationinterface
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-4-5 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.operationinterface;

import com.orient.sysmodel.domain.tbom.TbomDir;

/**
 * ClassName:IFunctionTbom
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-5		上午11:02:19
 *
 * @see 	 
 */
public interface IRoleFunctionTbom {

	/**
	 * 
	
	 * @Method: getRole 
	
	 * 取得角色信息
	
	 * @return
	
	 * @return IRole
	
	 * @throws
	 */
	public IRole getRole();
	
	/**
	 * 
	
	 * @Method: getFunction 
	
	 * 取得功能点信息
	
	 * @return
	
	 * @return IFunction
	
	 * @throws
	 */
	public IFunction getFunction();
	
	/**
	 * 
	
	 * @Method: getTbomDir 
	
	 * 取得Tbom信息
	
	 * @return
	
	 * @return ITbomDir
	
	 * @throws
	 */
	public ITbomDir getTbomDir();

	public void setTbomDir(TbomDir tbomDir);


}

