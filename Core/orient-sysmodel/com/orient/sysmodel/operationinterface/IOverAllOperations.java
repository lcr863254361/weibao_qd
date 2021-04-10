/**
 * IOverAllOperations.java
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

import java.util.List;

/**
 * ClassName:IOverAllOperations
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-5		上午11:00:52
 *
 * @see 	 
 */
public interface IOverAllOperations {

	/**
	 * 
	
	 * @Method: getId 
	
	 * id
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getId();
	
	/**
	 * 
	
	 * @Method: getOperationIds 
	
	 * OPERATION的ID集合,用","隔开
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getOperationIds();
	
	/**
	 * 
	
	 * @Method: getOperations 
	
	 * 取得权限下的有效操作
	
	 * @return
	
	 * @return List<IOperation>
	
	 * @throws
	 */
	public List<IOperation> getOperations();
}

