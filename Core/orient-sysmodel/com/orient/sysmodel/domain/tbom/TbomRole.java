/*
 * Title: DynamicTbom.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:56:58 AM
 * Version: 4.0
 */
package com.orient.sysmodel.domain.tbom;

import com.orient.sysmodel.operationinterface.ITbomRole;

/**
 * The Class DynamicTbom.
 * 
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public class TbomRole extends AbstractTbomRole implements ITbomRole {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new dynamic tbom.
	 */
	public TbomRole() {
		super();
	}

	public TbomRole(String nodeId, String roleId, String dataSource) 
	{
		super(nodeId, roleId, dataSource);
	}

}
