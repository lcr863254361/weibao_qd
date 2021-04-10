/*
 * Title: AbstractDynamicTbom.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:57:00 AM
 * Version: 4.0
 */
package com.orient.sysmodel.domain.tbom;


/**
 * AbstractRelationTBOM entity provides the base persistence definition of the
 * RelationTBOM entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTbomRole extends
		com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The nodeId. */
	private String nodeId;

	/** The roleId. */
	private String roleId;

	/** The dataSource  . */
	private String dataSource  ;

	/**
	 * Instantiates a new abstract dynamic tbom.
	 */
	public AbstractTbomRole() {
	}

	public AbstractTbomRole(String nodeId, String roleId, String dataSource) 
	{
		super();
		this.nodeId = nodeId;
		this.roleId = roleId;
		this.dataSource = dataSource;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	
}