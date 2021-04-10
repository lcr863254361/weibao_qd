package com.orient.businessmodel.bean.impl;

import com.orient.businessmodel.bean.ITreeNodeFilterModelBean;

/**
 * @ClassName TreeNodeFilterModelBean
 * 树形节点过滤模型的条件
 * @author zhulc@cssrc.com.cn
 * @date Apr 9, 2012
 */

public class TreeNodeFilterModelBean implements ITreeNodeFilterModelBean {
	
	/** 
	* @Fields static_filter : TBOM定义的过滤表达式
	*/
	protected String static_filter = "";
	
	/** 
	* @Fields extNode_filter : 动态子节点过滤条件
	*/
	protected String extNode_filter = "";

 

	/**
	 * TODO
	 * @Method: getExtNode_filter 
	 * @return 
	 * @see com.orient.businessmodel.bean.ITreeNodeFilterModelBean#getExtNode_filter() 
	 */
	
	public String getExtNode_filter() {
		return extNode_filter;
	}

	public String getStatic_filter() {
		return static_filter;
	}
	
	public  void setStatic_filter(String strfilter)
	{
		static_filter = strfilter;
	}
}
