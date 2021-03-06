/*
 * Title: Tbom.java
 * Company: DHC
 * Author: XIUJUN XU
 * Date: Nov 23, 2009 11:09:04 AM
 * Version: 4.0
 */
package com.orient.sysmodel.domain.tbom;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.orient.metamodel.metadomain.Column;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metadomain.Table;
import com.orient.metamodel.metadomain.View;
import com.orient.sysmodel.operationinterface.IDynamicTbom;
import com.orient.sysmodel.operationinterface.ITbom;

/**
 * The Class Tbom.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public class Tbom extends AbstractTbom implements Serializable, ITbom {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String expandLevel;

	public String getExpandLevel() {
		return expandLevel;
	}

	public void setExpandLevel(String expandLevel) {
		this.expandLevel = expandLevel;
	}

	/**
	 * Instantiates a new tbom.
	 */
	public Tbom() {
	}

//	public Tbom(String id, String table, String view, String name, Long type,
//			String description, String detailText, String bigImg,
//			String smaImg, String norImg, Long order,Long isRoot,String xmlid,Long valid, String column, String columnName) {
//		super(id, table, view, name, type, description, detailText, bigImg,
//				smaImg, norImg, order, isRoot,xmlid,valid,"",column,columnName);
//	}

	/**
 * Instantiates a new tbom.
 * 
 * @param id
 *            the id
 * @param table
 *            the table
 * @param view
 *            the view
 * @param name
 *            the name
 * @param type
 *            the type
 * @param description
 *            the description
 * @param detailText
 *            the detail text
 * @param bigImg
 *            the big img
 * @param smaImg
 *            the sma img
 * @param norImg
 *            the nor img
 * @param order
 *            the order
 * @param isRoot
 *            the is root
 * @param xmlid
 *            the xmlid
 * @param valid
 *            the valid
 * @param originExpression
 *            the origin expression
 * @param expression
 *            the expression
 */
public Tbom(String id, Table table, View view, String name, Long type,
			String description, String detailText, String bigImg,
			String smaImg, String norImg, Long order, Long isRoot,
			String xmlid, Long valid, String expression, String originExpression, String url, String showType, String templateId) {
		super(id, table, view, name, type, description, detailText, bigImg,
				smaImg, norImg, order, isRoot,xmlid,valid,null,null,"",expression,originExpression, url, showType,templateId);
	}

/**
 * ????????????TBOM?????????????????????.
 * 
 * @param table
 *            ?????????ID
 * @param name
 *            ????????????
 * @param order
 *            ??????
 * @param isRoot
 *            ???????????????
 * @param xmlid
 *            XML?????????ID
 * @param schemaid
 *            ?????????????????????ID
 * @param expression
 *            ??????????????????web???????????????
 * @param originExpression
 *            ??????????????????TBOM??????????????????
 */
	public Tbom(Table table, String name, Long order, Long isRoot,
			String xmlid, Schema schema, String expression,
			String originExpression, String url, String showType) {
		super(table, name, order, isRoot, xmlid, schema, expression, originExpression, url, showType);
	}

	/**
	 * Instantiates a new tbom.
	 * 
	 * @param id
	 *            the id
	 * @param table
	 *            the table
	 * @param view
	 *            the view
	 * @param name
	 *            the name
	 * @param type
	 *            the type
	 * @param description
	 *            the description
	 * @param detailText
	 *            the detail text
	 * @param bigImg
	 *            the big img
	 * @param smaImg
	 *            the sma img
	 * @param norImg
	 *            the nor img
	 * @param order
	 *            the order
	 * @param isRoot
	 *            the is root
	 * @param xmlid
	 *            the xmlid
	 * @param valid
	 *            the valid
	 * @param string
	 *            the string
	 * @param column
	 *            the column
	 * @param columnName
	 *            the column name
	 * @param expression
	 *            the expression
	 * @param originExpression
	 *            the origin expression
	 */
	public Tbom(String id, Table table, View view, String name, Long type,
			String description, String detailText, String bigImg,
			String smaImg, String norImg, Long order, Long isRoot,
			String xmlid, Long valid, Schema schema, Column column,
			String columnName, String expression, String originExpression, String url, String showType) {
		super(id, table, view, name, type, description, detailText, bigImg, smaImg,
				norImg, order, isRoot, xmlid, valid, schema, column, columnName,
				expression, originExpression, url, showType,null);
	}
	
	
	
/** 
	
	 * @Method: getchildNodebyNodeId 
	
	 * ????????????Id?????????????????????????????????
	
	 * @param nodeId
	 * @return
	
	 * @return ITbom
	
	 * @throws 
	
	 */
	
	public ITbom getchildNodebyNodeId(String nodeId){
		
		if(this.getId().equalsIgnoreCase(nodeId))
		{
			return this;
		}
		Set<ITbom> bomnodes= this.getChildTboms();
		for(ITbom loopNode :bomnodes)
		{
			if(loopNode.getId().equalsIgnoreCase(nodeId))
			{
				return loopNode;
			}
			else{
				ITbom findNode = loopNode.getchildNodebyNodeId(nodeId);
				if(findNode!=null)
				{
					return findNode;
				}
			}
		}
		return null;
		
	}

	
/**

 * @Method: getChildDynamicNodebyNodeId 

 * ?????????????????????Id???????????????????????????????????????????????????????????????

 * @param nodeId
 * @return 

 * @see com.orient.sysmodel.operationinterface.ITbom#getChildDynamicNodebyNodeId(java.lang.String) 

 */
	@Override
	public IDynamicTbom getChildDynamicNodebyNodeId(String nodeId) {
		
		// TODO Auto-generated method stub
			
		SortedSet<DynamicTbom> dynamicNodeSet =	this.getDynamicTbom();
	
		for(DynamicTbom dynamicNode: dynamicNodeSet)
		{
			if(	dynamicNode.getId().equalsIgnoreCase(nodeId))
			{
				return dynamicNode;
			}
		}
		
		Set<ITbom> bomnodes= this.getChildTboms();
		for(ITbom loopNode :bomnodes)
		{
			IDynamicTbom retDynamicNode = loopNode.getChildDynamicNodebyNodeId(nodeId);
			if(retDynamicNode!=null)
			{
				return retDynamicNode;
			}
		}
		return null;
		
	}



	/**
	
	 * @Method: getDynamicTbomSet 
	
	 *
	 * @return 
	
	 * @see com.orient.sysmodel.operationinterface.ITbom#getDynamicTbomSet() 
	
	 */
	public SortedSet<IDynamicTbom> getDynamicTbomSet() {
		
		
		SortedSet<IDynamicTbom> dynamicNodeSet=new TreeSet<IDynamicTbom>(new IDyanmicTbomComp());
		for(DynamicTbom  loopDynamcNode :this.getDynamicTbom())
		{
			IDynamicTbom tempNode=loopDynamcNode;
			dynamicNodeSet.add(tempNode);
		}
		return dynamicNodeSet;
	}

}
