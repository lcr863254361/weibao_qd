/**
 * RcpLoginService.java
 * com.orient.sysmodel.service.user
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-4-27 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.service.user;

import java.util.List;

import com.orient.sysmodel.domain.user.RcpLogin;

/**
 * ClassName:RcpLoginService
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-27		下午03:05:23
 *
 * @see 	 
 */
public interface RcpLoginService {

	/**
	 * 
	
	 * @Method: createRcpLogin 
	
	 * 创建客户端登陆记录
	
	 * @param rcpLogin
	
	 * @return void
	
	 * @throws
	 */
	public void createRcpLogin(RcpLogin rcpLogin);
	
	/**
	 * 
	
	 * @Method: deleteRCPLogin 
	
	 * 删除客户端登陆记录
	
	 * @param rcpLogin
	
	 * @return void
	
	 * @throws
	 */
	public void deleteRCPLogin(RcpLogin rcpLogin);
	
	/**
	 * 
	
	 * @Method: findRcpLoginOfTable 
	
	 * 取得指定表的客户端登陆记录
	
	 * @param tableId
	 * @return
	
	 * @return List<RcpLogin>
	
	 * @throws
	 */
	public List<RcpLogin> findRcpLoginOfTable(String tableId);
}

