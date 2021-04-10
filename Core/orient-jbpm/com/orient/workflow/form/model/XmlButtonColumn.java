package com.orient.workflow.form.model;

import org.dom4j.Element;

import com.orient.utils.CommonTools;



/**
 * @ClassName XmlButtonColumn
 * 表单中按钮事件所关联的字段
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class XmlButtonColumn {
	
	private String column_id = "";
	private String column_name = "";
	private String table_id = "";
	private String type = "";
	private String value = "";
	
	public XmlButtonColumn()
	{
		
	}
	
	public XmlButtonColumn(Element buttonColumnElement){
		column_id = CommonTools.Obj2String(buttonColumnElement.attributeValue("column_id"));
		column_name = CommonTools.Obj2String(buttonColumnElement.attributeValue("column_name"));
		table_id = CommonTools.Obj2String(buttonColumnElement.attributeValue("table_id"));
		type = CommonTools.Obj2String(buttonColumnElement.attributeValue("type"));
		value = CommonTools.Obj2String(buttonColumnElement.attributeValue("value"));
	}

	public String getColumn_id() {
		return column_id;
	}

	public void setColumn_id(String column_id) {
		this.column_id = column_id;
	}

	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	public String getTable_id() {
		return table_id;
	}

	public void setTable_id(String table_id) {
		this.table_id = table_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}
