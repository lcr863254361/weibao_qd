/**
 * IFunction.java
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

import java.util.Set;

/**
 * ClassName:IFunction
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-5		上午11:00:40
 *
 * @see 	 
 */
public interface IFunction {
	
	
	
	/** 
	* @Fields webShowId : web页面展现的跟功能点 需要和数据库中的匹配
	*/
	static long webShowRootId = 8;
	

	//long webShowRootId = 0;

	/**
	 * 
	
	 * @Method: getCode 
	
	 * 功能点编码
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getCode();
	
	/**
	 * 
	
	 * @Method: getName 
	
	 * 功能点名称
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getName();
	
	/**
	 * 
	
	 * @Method: getUrl 
	
	 * 功能点所在的请求路径
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getUrl();
	
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
	
	 * 是否可以添加子功能点标志, 1．可以添加 0．不可以添加
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getAddFlg();
	
	/**
	 * 
	
	 * @Method: getDelFlg 
	
	 * 是否可以删除功能点标志, 1.可以删除0.不可以删除
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getDelFlg();
	
	/**
	 * 
	
	 * @Method: getEditFlg 
	
	 * 是否可以编辑, 1.可以编辑0.不可以编辑
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getEditFlg();
	
	/**
	 * 
	
	 * @Method: getPosition 
	
	 * 同级功能点的显示位置
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getPosition();
	
	/**
	 * 
	
	 * @Method: getLogtype 
	
	 * 日志记录标识 1.记录日志0.不记录
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getLogtype();
	
	/**
	 * 
	
	 * @Method: getLogshow 
	
	 * 日志显示标识 1.显示记录的日志类型 0.不显示
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getLogshow();
	
	/**
	 * 
	
	 * @Method: getFunctionid 
	
	 * 功能点ID
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getFunctionid();
	
	/**
	 * 
	
	 * @Method: getParentid 
	
	 * 父级功能点ID
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getParentid();
	
	/**
	 * 
	
	 * @Method: getIsShow 
	
	 * 给角色分配功能点页面上是否显示 1 显示, 0 不显示
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getIsShow();
	
	/**
	 * 
	
	 * @Method: getTbomFlg 
	
	 * 是否挂接tbom 1:该功能挂接了TBOM  0:功能未挂接TBOM
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getTbomFlg();
	
	/**
	 * 
	
	 * @Method: getChildrenFunction 
	
	 * 子功能点
	
	 * @return
	
	 * @return Set
	
	 * @throws
	 */
	public Set getChildrenFunction();
}

