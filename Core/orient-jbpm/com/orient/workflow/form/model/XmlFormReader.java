package com.orient.workflow.form.model;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;




public class XmlFormReader {
	private Document document;
	
	private List<XmlForm> forms;
	
	private List<SubXmlForm> subForms;
	
	private List<AlertInfo> alertInfos = UtilFactory.newArrayList();
    
	private String formPath = "/forms/form";
	
	private String subFormPath = "/forms/subForm";
	
	private String projectId = "";
	
	private String schemaId = "";

	public XmlFormReader(InputStream in) throws MalformedURLException, DocumentException{
		SAXReader saxReader = new SAXReader();
		this.document = saxReader.read(in);
		init();
    }
	
	@SuppressWarnings("unchecked")
	private void init(){
		//解析基本信息
		Element Root = document.getRootElement();
		projectId = CommonTools.null2String(Root.attributeValue("projectId"));
		schemaId = CommonTools.null2String(Root.attributeValue("schemaId"));
		//解析form信息
		forms = new ArrayList<XmlForm>();
		List<Element> elements = document.selectNodes(formPath);
		for(Element element : elements){
			XmlForm form = new XmlForm(element);
			forms.add(form);
		}
		//解析subForm信息
		subForms = new ArrayList<SubXmlForm>();
		List<Element> subElements = document.selectNodes(subFormPath);
		for(Element element : subElements){
			SubXmlForm subForm = new SubXmlForm(element);
			subForms.add(subForm);
		}
		
		List<Element> alertInfoEles = document.selectNodes("alert");
		for(Element element : alertInfoEles){
			AlertInfo alertInfo = new AlertInfo(element);
			this.alertInfos.add(alertInfo);
		}
    }
	
	public XmlForm getFormByName(String taskName){
		if(taskName.equals("")){
			for(XmlForm form : forms){
				if(form.getStart().equals("true")){
					return form;
				}
			}
		} else {
			for(XmlForm form : forms){
				if(form.getName().equals(taskName)){
					return form;
				}
			}
		}
		return null;
	}
	
	/**
	 * get form by id
	 *
	 * @author [创建人]  spf <br/> 
	 * 		   [创建时间] 2014-7-26 上午11:26:27 <br/> 
	 * 		   [修改人] spf <br/>
	 * 		   [修改时间] 2014-7-26 上午11:26:27
	 * @param id
	 * @return
	 * @see
	 */
	public XmlForm getFormById(String id){
		for(XmlForm form : forms){
			if(form.getId().equals(id)){
				return form;
			}
		}
		return null;
	}
	
	public SubXmlForm getSubFormByName(String taskName){
		
		for(SubXmlForm form : subForms){
			if(form.getName().equals(taskName)){
				return form;
			}
		}
		return null;
	}
	
    public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getFormPath() {
		return formPath;
	}

	public void setFormPath(String formPath) {
		this.formPath = formPath;
	}
	public List<XmlForm> getForms() {
		return forms;
	}

	public void setForms(List<XmlForm> forms) {
		this.forms = forms;
	}

	public List<SubXmlForm> getSubForms() {
		return subForms;
	}

	public void setSubForms(List<SubXmlForm> subForms) {
		this.subForms = subForms;
	}

	public String getSubFormPath() {
		return subFormPath;
	}

	public void setSubFormPath(String subFormPath) {
		this.subFormPath = subFormPath;
	}
	
	public String getProjectId() {
		return projectId;
	}
	
	public String getSchemaId() {
		return schemaId;
	}
	
	public List<AlertInfo> getAlertInfos() {
		return alertInfos;
	}

	public void setAlertInfos(List<AlertInfo> alertInfos) {
		this.alertInfos = alertInfos;
	}
}
