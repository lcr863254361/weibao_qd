/*
 * Copyright (c) 2016. Orient Company
 *
 */

/**
 * 
 */
package com.orient.edm.util;

import com.orient.edm.asyncbean.EDM_UserContainer_Async;

import javax.servlet.http.HttpServletRequest;


/**
 * 获取session中的用户信息.
 *
 * <p>detailed comment</p>
 * @author [创建人]  zhulongchao <br/> 
 * 		   [创建时间] 2014-7-9 下午03:13:00 <br/> 
 * 		   [修改人] zhulongchao <br/>
 * 		   [修改时间] 2014-7-9 下午03:13:00
 * @see
 */
public class SessionUtil {
	
	public static String getUserId(HttpServletRequest request) {
		return ((EDM_UserContainer_Async) request.getSession().getAttribute("userInfo")).getUserId();
	}
	
	public static String getUserNAME(HttpServletRequest request) {
		return ((EDM_UserContainer_Async) request.getSession().getAttribute("userInfo")).getLoginName();
	}
	
	public static String getShowName(HttpServletRequest request) {
		return ((EDM_UserContainer_Async) request.getSession().getAttribute("userInfo")).getDisplayName();
	}
}
