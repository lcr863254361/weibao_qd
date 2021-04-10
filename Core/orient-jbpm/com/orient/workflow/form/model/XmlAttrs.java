package com.orient.workflow.form.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XmlAttrs implements Serializable{

	//serialVersionUID is
	
	private static final long serialVersionUID = -4958749946868847996L;
	
	public XmlAttrs()
	{
		
	}
	
	private List<XmlAttr> xmlAttrs = new ArrayList<XmlAttr>();

	public List<XmlAttr> getXmlAttrs() {
		return xmlAttrs;
	}

	public void setXmlAttrs(List<XmlAttr> xmlAttrs) {
		this.xmlAttrs = xmlAttrs;
	}
	
	
}
