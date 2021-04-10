/**
 * IDynamicTbom.java
 * com.orient.sysmodel.operationinterface
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 Jul 23, 2012 		mengbin
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.operationinterface;

import java.util.List;
import java.util.Set;

import com.orient.sysmodel.domain.tbom.DynamicTbomRole;
import com.orient.sysmodel.domain.tbom.Tbom;

/**
 * ClassName:IDynamicTbom
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   mengbin@cssrc.com.cn
 * @version  
 * @since    Ver 1.1
 * @Date	 Jul 23, 2012		3:05:46 PM
 *
 * @see 	 
 */
public interface IDynamicTbom {
	
	public String getId();
	
	public Tbom getTbom();// 所属TBOM树
	
	public String getColumn();// 动态子节点的字段ID，关联CWM_TAB_COLUMNS表

	public String getTable();// 动态子节点的字段所属数据类
	
	public String getView();// 动态子节点上级的（第一）数据源，当该数据源是视图时，本字段记录视图的ID

	public String getOrder();// 动态子节点的顺序

	public String getSource();// 动态子节点的数据源集合，以"，"分割

    public String getUrl();// 链接地址
    
    public String getShowType();// 显示类型
    
    public String getDisplay();// 树形 或 Tab页
	
    public String getPid();// 父节点
    
    public String getExp();
    
    public Set getDynamicTbomRoles();
    
    public String getOrigin_exp();
    
}

