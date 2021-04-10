package com.orient.workflow.form.model;

import org.dom4j.Element;

import com.orient.utils.CommonTools;



/**
 * @ClassName XmlButtonRight
 * 表单中按钮所设置的数据类和值
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class XmlButtonRight {

	private String table_id = "";
	private String table_name = "";
	private String value = "";

	public XmlButtonRight()
	{
		
	}
	
	public XmlButtonRight(Element buttonColumnElement) {
		table_id = CommonTools.Obj2String(buttonColumnElement.attributeValue("table_id"));
		table_name = CommonTools.Obj2String(buttonColumnElement.attributeValue("table_name"));
		value = CommonTools.Obj2String(buttonColumnElement.attributeValue("value"));
	}

	public String getTable_id() {
		return table_id;
	}

	public void setTable_id(String table_id) {
		this.table_id = table_id;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
