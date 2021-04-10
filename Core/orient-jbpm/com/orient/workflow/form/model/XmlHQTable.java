package com.orient.workflow.form.model;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.orient.utils.CommonTools;



/**
 * @ClassName XmlHQTable
 * 会签任务的表单
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class XmlHQTable {
	
	private String id = "";
	
	private List<XmlColumn> columns = new ArrayList<XmlColumn>();
	private List<XmlButton> buttons = new ArrayList<XmlButton>();
	private List<XmlInfo> infos = new ArrayList<XmlInfo>();
	
	@SuppressWarnings("unchecked")
	public XmlHQTable(Element tableElement){
		id = CommonTools.Obj2String(tableElement.attributeValue("id"));
		
		//解析column对象
		List<Element> columnElements = tableElement.elements("column");
		for(Element element : columnElements){
			XmlColumn column = new XmlColumn(element);
			columns.add(column);
		}
		//解析button对象
		List<Element> buttonElements = tableElement.elements("button");
		for(Element element : buttonElements){
			XmlButton column = new XmlButton(element);
			buttons.add(column);
		}
		//解析info对象
		List<Element> infoElements = tableElement.elements("info");
		for(Element element : infoElements){
			XmlInfo column = new XmlInfo(element);
			infos.add(column);
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<XmlColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<XmlColumn> columns) {
		this.columns = columns;
	}

	public List<XmlButton> getButtons() {
		return buttons;
	}

	public void setButtons(List<XmlButton> buttons) {
		this.buttons = buttons;
	}
}
