/**
 * ITbomDir.java
 * com.orient.sysmodel.operationinterface
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-4-19 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.operationinterface;

import java.util.Date;

/**
 * ClassName:ITbomDir
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-19		上午08:53:11
 *
 * @see 	 
 */
public interface ITbomDir {
	/**
	 * 
	
	 * @Method: getId 
	
	 * TBOM树的Id
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getId();
	
	/**
	 * 
	
	 * @Method: getName 
	
	 * TBOM树的名称
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getName();
	
	/**
	 * 
	
	 * @Method: getSchemaid 
	
	 * TBOM树所属的数据模型的ID
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getSchemaid();
	
	/**
	 * 
	
	 * @Method: getIsLock 
	
	 * TBOM树上锁标记，0表示未上锁，1表示上锁
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getIsLock();
	
	/**
	 * 
	
	 * @Method: getUserid 
	
	 * 上锁解锁的用户名称(登录名)
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getUserid();
	
	/**
	 * 
	
	 * @Method: getModifiedTime 
	
	 * TBOM树的修改时间
	
	 * @return
	
	 * @return Date
	
	 * @throws
	 */
	public Date getModifiedTime();
	
	/**
	 * 
	
	 * @Method: getLockModifiedTime 
	
	 * TBOM树的上锁解锁修改时间
	
	 * @return
	
	 * @return Date
	
	 * @throws
	 */
	public Date getLockModifiedTime();
	
	/**
	 * 
	
	 * @Method: getIsdelete 
	
	 * TBOM树是否删除，0表示删除，1表示未删除
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getIsdelete();
	
	/**
	 * 
	
	 * @Method: getType 
	
	 * Tbom类型(0或者空:数据查看,1:文件查看
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getType();
	
	/**
	 * 
	
	 * @Method: getOrder_sign 
	
	 * TBOM树在web端的排序字段
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getOrder_sign();
}

