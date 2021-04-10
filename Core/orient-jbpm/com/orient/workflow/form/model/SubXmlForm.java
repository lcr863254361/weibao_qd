package com.orient.workflow.form.model;

import java.util.List;

import org.dom4j.Element;

import com.orient.utils.CommonTools;



public class SubXmlForm extends JpdlForm{
	
	//serialVersionUID is
	
	private static final long serialVersionUID = -3816748597986866037L;

	public SubXmlForm()
	{
		
	}
	
	public SubXmlForm(Element formElement)
	{
		//form属性
		id = CommonTools.Obj2String(formElement.attributeValue("id"));
		name = CommonTools.Obj2String(formElement.attributeValue("name"));
		//任务属性集合
		Element attrElement = formElement.element("normalAttr");
		parseAttrs(attrElement,xmlAttrs);
	}
	
	@SuppressWarnings("unchecked")
	private void parseAttrs(Element attrElement, XmlAttrs xmlAttrs) {
		
		List<Element> taskAttrElemens = attrElement.elements("taskAttr");
		for(Element taskAttrElement : taskAttrElemens)
		{
			XmlAttr attr = new XmlAttr(taskAttrElement);
			xmlAttrs.getXmlAttrs().add(attr);
		}
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
}
