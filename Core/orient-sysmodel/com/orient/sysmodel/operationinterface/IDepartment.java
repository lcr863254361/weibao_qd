/**
 * IDepartment.java
 * com.orient.sysmodel.operationinterface
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-4-11 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.operationinterface;

import java.util.Set;

/**
 * ClassName:IDepartment
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-11		上午08:26:56
 *
 * @see 	 
 */
public interface IDepartment {

	/**
	 * 
	
	 * @Method: getId 
	
	 * ID
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getId();
	
	/**
	 * 
	
	 * @Method: getPid 
	
	 * 上级部门
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getPid();
	
	/**
	 * 
	
	 * @Method: getChildDepts 
	
	 * 子部门
	
	 * @return
	
	 * @return Set
	
	 * @throws
	 */
	public Set getChildDepts();
	
	/**
	 * 
	
	 * @Method: getName 
	
	 * 部门名称
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getName();
	
	/**
	 * 
	
	 * @Method: getFunction 
	
	 * 部门职能
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getFunction();
	
	/**
	 * 
	
	 * @Method: getNotes 
	
	 * 备注
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getNotes();
	
	/**
	 * 
	
	 * @Method: getAddFlg 
	
	 * 新增标志
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getAddFlg();
	
	/**
	 * 
	
	 * @Method: getDelFlg 
	
	 * 删除标志
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getDelFlg();
	
	/**
	 * 
	
	 * @Method: getEditFlg 
	
	 * 编辑标志
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getEditFlg();

	/**
	 * 获取排序
	 * @return
	 */
	public Long getOrder();
}

