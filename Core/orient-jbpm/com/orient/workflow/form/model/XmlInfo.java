package com.orient.workflow.form.model;

import org.dom4j.Element;

import com.orient.utils.CommonTools;



/**
 * @ClassName XmlInfo
 * 表单的基本信息
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class XmlInfo {
	
	private String name = "";
	private String required = "";
	private String value = "";
	private String isopen = "";
	
	public XmlInfo()
	{
		
	}
	
	public XmlInfo(Element buttonElement){
		name = CommonTools.Obj2String(buttonElement.attributeValue("name"));
		required = CommonTools.Obj2String(buttonElement.attributeValue("required"));
		value = CommonTools.Obj2String(buttonElement.attributeValue("value"));
		isopen = CommonTools.Obj2String(buttonElement.attributeValue("isopen"));
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIsopen() {
		return isopen;
	}

	public void setIsopen(String isopen) {
		this.isopen = isopen;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}
}
