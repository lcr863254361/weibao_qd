/**
 * ITbom.java
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

import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.metamodel.operationinterface.IView;
import com.orient.sysmodel.domain.tbom.Tbom;

import java.util.Set;
import java.util.SortedSet;

/**
 * ClassName:ITbom
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-5		上午11:01:46
 *
 * @see 	 
 */
public interface ITbom {

	public String getId();
	
	/**
	 * 
	
	 * @Method: getName 
	
	 * 节点的名称
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getName();
	
	/**
	 * 
	
	 * @Method: getType 
	
	 * 节点的数据源类型，0为数据类,1为数据视图
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getType();
	
	/**
	 * 
	
	 * @Method: getDescription 
	
	 * 对节点的描述
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getDescription();
	
	/**
	 * 
	
	 * @Method: getDetailText 
	
	 * 详细文字
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getDetailText();
	
	/**
	 * 
	
	 * @Method: getBigImg 
	
	 * TBOM树节点的大图标
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getBigImg();
	
	/**
	 * 
	
	 * @Method: getSmaImg 
	
	 * TBOM树节点的小图标
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getSmaImg();
	
	/**
	 * 
	
	 * @Method: getNorImg 
	
	 * TBOM树节点的中图标
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getNorImg();
	
	/**
	 * 
	
	 * @Method: getOrder 
	
	 * 顺序号
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getOrder();
	
	/**
	 * 
	
	 * @Method: getIsValid 
	
	 * 是否有效，0表示无效，1表示有效
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getIsValid();
	
	/**
	 * 
	
	 * @Method: getIsRoot 
	
	 * 是否是TBOM树的根节点，0表示否，1表示是
	
	 * @return
	
	 * @return Long
	
	 * @throws
	 */
	public Long getIsRoot();
	
	/**
	 * 
	
	 * @Method: getParenttbom 
	
	 * 父节点
	
	 * @return
	
	 * @return Tbom
	
	 * @throws
	 */
	public Tbom getParenttbom();
	
	/**
	 * 
	
	 * @Method: getChildTboms 
	
	 * 子节点
	
	 * @return
	
	 * @return Set
	
	 * @throws
	 */
	public Set getChildTboms();
	
	public Set getCwmRelationTbomsForRelationId();
	
	public Set getCwmRelationTbomsForNodeId();
	
	/**
	 * 
	
	 * @Method: getXmlid 
	
	 * 节点的XML文件ID
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getXmlid();
	
	public Set getRelationFile();
	
	/**
	 * 
	
	 * @Method: getTable 
	
	 * 数据源-表
	
	 * @return
	
	 * @return ITable
	
	 * @throws
	 */
	public ITable getTable();
	
	/**
	 * 
	
	 * @Method: getView 
	
	 * 数据源-视图
	
	 * @return
	
	 * @return IView
	
	 * @throws
	 */
	public IView getView();
	
	/**
	 * 
	
	 * @Method: getSchema 
	
	 * 所属数据模型
	
	 * @return
	
	 * @return ISchema
	
	 * @throws
	 */
	public ISchema getSchema();
	
	/**
	 * 
	
	 * @Method: getColumn 
	
	 * 动态子节点的字段
	
	 * @return
	
	 * @return IColumn
	
	 * @throws
	 */
	public IColumn getColumn();
	
	/**
	 * 
	
	 * @Method: getColumnName 
	
	 * 动态子节点字段名称
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getColumnName();
	
	/**
	 * 
	
	 * @Method: getExpression 
	
	 * 过滤表达式
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getExpression();
	
	/**
	 * 
	
	 * @Method: getOriginExpression 
	
	 * 过滤表达式
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getOriginExpression();
	
	/**
	 * 
	
	 * @Method: getUrl 
	
	 * 链接地址
	
	 * @return
	
	 * @return String
	
	 * @throws
	 */
	public String getUrl();
	
	
	public String getShowType();
	
	public Set getTbomRoles();
	/** 
	
	 * @Method: getchildNodebyNodeId 
	
	 * 根据节点Id查找当前节点下的子节点
	
	 * @param nodeId
	 * @return
	
	 * @return ITbom
	
	 * @throws 
	
	 */
	
	public ITbom getchildNodebyNodeId(String nodeId);
	
	
	/** 
	
	 * @Method: getChildDynamicNodebyNodeId 
	
	 * 根据节点的Id查找当前节点下的动态子节点
	
	 * @param nodeId
	 * @return
	
	 * @return IDynamicTbom
	
	 * @throws 
	
	 */
	
	public IDynamicTbom getChildDynamicNodebyNodeId(String nodeId);
	
	
	

	public SortedSet<IDynamicTbom> getDynamicTbomSet() ;

	public String getTemplateid();

	public String getExpandLevel();

	public void setExpandLevel(String expandLevel);
	
}

