/*
 * Title: AbstractDynamicTbom.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:57:00 AM
 * Version: 4.0
 */
package com.orient.sysmodel.domain.tbom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * AbstractRelationTBOM entity provides the base persistence definition of the
 * RelationTBOM entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractDynamicTbom extends
		com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {

	/** The id. */
	private String id;

	/** The tbom. */
	private Tbom tbom;// 所属TBOM树

	/** The column. */
	private String column;// 动态子节点的字段ID，关联CWM_TAB_COLUMNS表

	/** The table. */
	private String table;// 动态子节点的字段所属数据类

	/** The view. */
	private String view;// 动态子节点上级的（第一）数据源，当该数据源是视图时，本字段记录视图的ID

	/** The order. */
	private String order;// 动态子节点的顺序

	/** The source. */
	private String source;// 动态子节点的数据源集合，以"，"分割
	/** url */
	private String url;// 链接地址

	private String display;// 树形 或 Tab页

	private String pid; // 父节点

	private String exp;

	private String origin_exp;
	
	private String showType;
	
	private Set dynamicTbomRoles = new HashSet(0);
	
	public List<DynamicTbom> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<DynamicTbom> childrenList) {
		this.childrenList = childrenList;
	}

	private List<DynamicTbom>  childrenList = new ArrayList<DynamicTbom>();

	/**
	 * Instantiates a new abstract dynamic tbom.
	 */
	public AbstractDynamicTbom() {
	}

	/**
	 * Instantiates a new abstract dynamic tbom.
	 * 
	 * @param id
	 *            the id
	 * @param tbom
	 *            the tbom
	 * @param column
	 *            the column
	 * @param table
	 *            the table
	 * @param view
	 *            the view
	 * @param order
	 *            the order
	 * @param source
	 *            the source
	 */
	public AbstractDynamicTbom(String id, Tbom tbom, String column,
			String table, String view, String order, String source, String url, String showType) {
		super();
		this.id = id;
		this.tbom = tbom;
		this.column = column;
		this.table = table;
		this.view = view;
		this.order = order;
		this.source = source;
		this.url = url;
		this.showType = showType;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public Set getDynamicTbomRoles() {
		return dynamicTbomRoles;
	}

	public void setDynamicTbomRoles(Set dynamicTbomRoles) {
		this.dynamicTbomRoles = dynamicTbomRoles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Tbom getTbom() {
		return tbom;
	}

	public void setTbom(Tbom tbom) {
		this.tbom = tbom;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getOrigin_exp() {
		return origin_exp;
	}

	public void setOrigin_exp(String origin_exp) {
		this.origin_exp = origin_exp;
	}

}