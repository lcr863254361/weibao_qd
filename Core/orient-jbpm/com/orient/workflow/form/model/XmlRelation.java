package com.orient.workflow.form.model;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.orient.utils.CommonTools;



/**
 * @ClassName XmlRelation
 * 关联表单的xml定义
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class XmlRelation {
	
	private String tableid = "";
	private String isMulti = "";
	private String refcolumn = "";
	private List<XmlColumn> columns;
	private String id = "";//数据展现用 20110314 add
	private String pid = "";//数据展现用 20110314 add
	private String fatherTableId = "";//数据展现用 20110314 add
	
	@SuppressWarnings("unchecked")
	public XmlRelation(Element relationElement){
		tableid = CommonTools.Obj2String(relationElement.attributeValue("tableid"));
		isMulti = CommonTools.Obj2String(relationElement.attributeValue("isMulti"));
		refcolumn = CommonTools.Obj2String(relationElement.attributeValue("refcolumn"));
		columns = new ArrayList<XmlColumn>();
		
		List<Element> columnElements = relationElement.elements("rc");
		for(Element element : columnElements){
			XmlColumn column = new XmlColumn(element);
			columns.add(column);
		}
	}

	public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	public String getIsMulti() {
		return isMulti;
	}

	public void setIsMulti(String isMulti) {
		this.isMulti = isMulti;
	}

	public String getRefcolumn() {
		return refcolumn;
	}

	public void setRefcolumn(String refcolumn) {
		this.refcolumn = refcolumn;
	}

	public List<XmlColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<XmlColumn> columns) {
		this.columns = columns;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getFatherTableId() {
		return fatherTableId;
	}

	public void setFatherTableId(String fatherTableId) {
		this.fatherTableId = fatherTableId;
	}
}
