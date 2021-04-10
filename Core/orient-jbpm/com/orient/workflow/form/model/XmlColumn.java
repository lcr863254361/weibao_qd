package com.orient.workflow.form.model;

import org.dom4j.Element;

import com.orient.utils.CommonTools;



/**
 * @ClassName XmlColumn
 * 表单中所设置的数据类字段
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class XmlColumn {
	
	private String id = "";
	private String name = "";
	private String required = "";
	private String readonly = "";
	private String tableid = "";
	
	public  XmlColumn()
	{
		
	}
	
	public XmlColumn(Element columnElement){
		id = CommonTools.Obj2String(columnElement.attributeValue("id"));
		name = CommonTools.Obj2String(columnElement.attributeValue("name"));
		required = CommonTools.Obj2String(columnElement.attributeValue("required"));
		readonly = CommonTools.Obj2String(columnElement.attributeValue("readonly"));
		tableid = CommonTools.Obj2String(columnElement.attributeValue("tableid"));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
	
	public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}
}
