package com.orient.workflow.form.model;

import org.dom4j.Element;

import com.orient.utils.CommonTools;

public class XmlAttr {
	
	private String name;
	
	private String value;

	public XmlAttr()
	{
		
	}
	
	public XmlAttr(Element taskAttrElement) {
		
		name = CommonTools.Obj2String(taskAttrElement.attributeValue("name"));
		value = CommonTools.Obj2String(taskAttrElement.attributeValue("value"));
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
	
	
}
