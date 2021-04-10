package com.orient.workflow.form.model;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.orient.utils.CommonTools;

/**
 * @ClassName XmlButton
 * 表单定义中按钮节点
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class XmlButton {
	
	private String name = "";
	private String type = "";
	private String isopen = "";
	private String connection = "";
	private List<XmlButtonColumn> buttonColumns = new ArrayList<XmlButtonColumn>();
	private List<XmlButtonRight> buttonRights = new ArrayList<XmlButtonRight>();

	public XmlButton()
	{
		
	}
	
	@SuppressWarnings("unchecked")
	public XmlButton(Element buttonElement){
		name = CommonTools.Obj2String(buttonElement.attributeValue("name"));
		type = CommonTools.Obj2String(buttonElement.attributeValue("type"));
		isopen = CommonTools.Obj2String(buttonElement.attributeValue("isopen"));
		connection = CommonTools.Obj2String(buttonElement.attributeValue("connection"));
		
		//解析buttonColumns对象
		List<Element> buttonElements = buttonElement.elements("button_column");
		for(Element element : buttonElements){
			XmlButtonColumn column = new XmlButtonColumn(element);
			buttonColumns.add(column);
		}

		//解析buttonRights对象
		List<Element> buttonRightElements = buttonElement.elements("button_right");
		for(Element element : buttonRightElements){
			XmlButtonRight column = new XmlButtonRight(element);
			buttonRights.add(column);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsopen() {
		return isopen;
	}

	public void setIsopen(String isopen) {
		this.isopen = isopen;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public List<XmlButtonColumn> getButtonColumns() {
		return buttonColumns;
	}

	public void setButtonColumns(List<XmlButtonColumn> buttonColumns) {
		this.buttonColumns = buttonColumns;
	}

	public List<XmlButtonRight> getButtonRights() {
		return buttonRights;
	}

	public void setButtonRights(List<XmlButtonRight> buttonRights) {
		this.buttonRights = buttonRights;
	}
}
