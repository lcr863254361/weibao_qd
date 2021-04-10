package com.orient.workflow.form.model;


import com.orient.workflow.form.model.XmlAttrs;

@SuppressWarnings("serial")
public class JpdlForm  implements java.io.Serializable{
	
	protected String id;
	
	protected String name;
	
	protected XmlAttrs xmlAttrs = new XmlAttrs();

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

	public XmlAttrs getXmlAttrs() {
		return xmlAttrs;
	}

	public void setXmlAttrs(XmlAttrs xmlAttrs) {
		this.xmlAttrs = xmlAttrs;
	}
	
}
