package com.orient.workflow.form.model;

import java.util.ArrayList;
import java.util.List;

public abstract class XmlParam {
	
	private List<XmlTable> xmlTables = new ArrayList<XmlTable>();

	public List<XmlTable> getXmlTables() {
		return xmlTables;
	}

	public void setXmlTables(List<XmlTable> xmlTables) {
		this.xmlTables = xmlTables;
	}
}
