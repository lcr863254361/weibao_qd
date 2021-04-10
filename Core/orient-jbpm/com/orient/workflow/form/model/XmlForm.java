package com.orient.workflow.form.model;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;

/**
 * @ClassName XmlForm
 * Xml定义的表单
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class XmlForm extends JpdlForm{
	
	//serialVersionUID is
	
	private static final long serialVersionUID = -5151219829815679664L;
	//是否是开始节点
	private String start = "";
	//任务提示
	private String operationtip = "";
	//附件操作类型
	private String filetype = "0";
	//邮件操作类型
	private String mailType = "0";	//0 不提醒， 1： 邮件提醒， 2：短信提醒  3：短信和邮件同时提醒
	//任务的输入
	private XmlIN paramIn = new XmlIN();
	//任务的输出
	private XmlOut paramOut = new XmlOut();
	//任务的输入输出
	private XmlInOrOut paramInOrOut = new XmlInOrOut();
	//任务的按钮集合
	private List<XmlButton> buttons = new ArrayList<XmlButton>();
	//任务的意见集合
	private List<XmlInfo> infos = new ArrayList<XmlInfo>();
	//任务的权限结合
	private List<XmlCourse> courses = new ArrayList<XmlCourse>();
	
	private List<AlertInfo> alertInfos = UtilFactory.newArrayList();
	
	public XmlForm()
	{
		
	}
	
	@SuppressWarnings("unchecked")
	public XmlForm(Element formElement){
		//form属性
		id = CommonTools.Obj2String(formElement.attributeValue("id"));
		name = CommonTools.Obj2String(formElement.attributeValue("name"));
		start = CommonTools.Obj2String(formElement.attributeValue("start"));
		operationtip = CommonTools.Obj2String(formElement.attributeValue("operationtip"));
		filetype = CommonTools.Obj2String(formElement.attributeValue("filetype"));
		mailType = CommonTools.Obj2String(formElement.attributeValue("mailtype"));
		
		//解析输入
		Element paramInElement = formElement.element("in");
		parseParam(paramInElement,paramIn);
		//解析输出
		Element paramOutElement = formElement.element("out");
		parseParam(paramOutElement,paramOut);
		//解析输入输出
		Element paramInOrOutElement = formElement.element("inOrOut");
		parseParam(paramInOrOutElement,paramInOrOut);
		
		//任务属性集合
		Element attrElement = formElement.element("normalAttr");
		parseAttrs(attrElement,xmlAttrs);
		
		//解析button对象
		List<Element> buttonElements = formElement.elements("button");
		for(Element element : buttonElements){
			XmlButton column = new XmlButton(element);
			buttons.add(column);
		}
		
		//解析XmlInfo对象
		List<Element> infoElements = formElement.elements("info");
		for(Element element : infoElements){
			XmlInfo column = new XmlInfo(element);
			infos.add(column);
		}
		
		//解析xmlCourse对象
		List<Element> courseElements = formElement.elements("course");
		for(Element element : courseElements){
			XmlCourse column = new XmlCourse(element);
			courses.add(column);
		}
		
		List<Element> alertInfoEles = formElement.elements("alert");
		for(Element element : alertInfoEles){
			AlertInfo alertInfo = new AlertInfo(element);
			if(!"".equals(alertInfo.getAlarmInfo().getClassname())) {
				this.alertInfos.add(alertInfo);
			}
		}
		
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


	@SuppressWarnings("unchecked")
	private void parseParam(Element paramInElement, XmlParam param) {
		
		List<Element> tableElemens = paramInElement.elements("table");
		for(Element tableElement : tableElemens)
		{
			XmlTable xmlTable = new XmlTable();
			
			xmlTable.setId(CommonTools.null2String(tableElement.attributeValue("id")));
			xmlTable.setDisplayName(CommonTools.null2String(tableElement.attributeValue("displayName")));
			xmlTable.setIs_insert(CommonTools.null2String(tableElement.attributeValue("is_insert")));
			xmlTable.setIs_update(CommonTools.null2String(tableElement.attributeValue("is_update")));
			xmlTable.setIs_ref(CommonTools.null2String(tableElement.attributeValue("is_ref")));
			xmlTable.setRef_task(CommonTools.null2String(tableElement.attributeValue("ref_task")));
			xmlTable.setType(CommonTools.null2String(tableElement.attributeValue("type")));
			List<Element> columnElements = tableElement.elements("column");
			for(Element element : columnElements){
				XmlColumn column = new XmlColumn(element);
				xmlTable.getColumns().add(column);
			}
			param.getXmlTables().add(xmlTable);
		}
 	}


	public XmlButton getButtonByName(String buttonName){
		for(XmlButton button : buttons){
			if(button.getName().equals(buttonName)){
				return button;
			}
		}
		return null;
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


	public String getStart() {
		return start;
	}


	public void setStart(String start) {
		this.start = start;
	}


	public String getOperationtip() {
		return operationtip;
	}


	public void setOperationtip(String operationtip) {
		this.operationtip = operationtip;
	}


	public String getFiletype() {
		return filetype;
	}


	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}


	public String getMailType() {
		return mailType;
	}


	public void setMailType(String mailType) {
		this.mailType = mailType;
	}


	public XmlIN getParamIn() {
		return paramIn;
	}


	public void setParamIn(XmlIN paramIn) {
		this.paramIn = paramIn;
	}


	public XmlOut getParamOut() {
		return paramOut;
	}


	public void setParamOut(XmlOut paramOut) {
		this.paramOut = paramOut;
	}


	public XmlInOrOut getParamInOrOut() {
		return paramInOrOut;
	}


	public void setParamInOrOut(XmlInOrOut paramInOrOut) {
		this.paramInOrOut = paramInOrOut;
	}


	public List<XmlButton> getButtons() {
		return buttons;
	}


	public void setButtons(List<XmlButton> buttons) {
		this.buttons = buttons;
	}


	public List<XmlInfo> getInfos() {
		return infos;
	}


	public void setInfos(List<XmlInfo> infos) {
		this.infos = infos;
	}


	public List<XmlCourse> getCourses() {
		return courses;
	}


	public void setCourses(List<XmlCourse> courses) {
		this.courses = courses;
	}

	public List<AlertInfo> getAlertInfos() {
		return alertInfos;
	}

	public void setAlertInfos(List<AlertInfo> alertInfos) {
		this.alertInfos = alertInfos;
	}

	
}
