package com.orient.workflow.form.model;

import java.util.HashSet;
import java.util.Set;

import com.orient.workflow.form.model.SubXmlForm;
import com.orient.workflow.form.model.XmlForm;

/**
 * 对应一个_Form.xml文件内容
 */
public class Forms {
	
	private Set<XmlForm> formSet = new HashSet<XmlForm>();
	
	private Set<SubXmlForm> subFormSet = new HashSet<SubXmlForm>();
	
	private String datatableid;
	
	private String schemaId = "";
	
	private String projectId = "";
	
	private String dataId = "";
	
	private String rootType = "";
	
	private String rootName = "";
	
	public String getSchemaId() {
		return schemaId;
	}

	public void setSchemaId(String schemaId) {
		this.schemaId = schemaId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public Set<XmlForm> getFormSet() {
		return formSet;
	}

	public void setFormSet(Set<XmlForm> formSet) {
		this.formSet = formSet;
	}

	public String getDatatableid() {
		return datatableid;
	}

	public void setDatatableid(String datatableid) {
		this.datatableid = datatableid;
	}

	public Set<SubXmlForm> getSubFormSet() {
		return subFormSet;
	}

	public void setSubFormSet(Set<SubXmlForm> subFormSet) {
		this.subFormSet = subFormSet;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getRootType() {
		return rootType;
	}

	public void setRootType(String rootType) {
		this.rootType = rootType;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}
	
	
}
