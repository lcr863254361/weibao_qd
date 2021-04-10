package com.orient.workflow.form.model;

import java.util.ArrayList;
import java.util.List;

public class XmlTable {
	
	private String id = "";
	
	private String displayName = "";
	
	private String is_insert = "";
	
	private String is_update = "";
	
	private String is_ref = "";
	
	private String ref_task = "";
	
	private String type = "";
	
	private List<XmlColumn> columns = new ArrayList<XmlColumn>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getIs_insert() {
		return is_insert;
	}

	public void setIs_insert(String isInsert) {
		is_insert = isInsert;
	}

	public String getIs_update() {
		return is_update;
	}

	public void setIs_update(String isUpdate) {
		is_update = isUpdate;
	}

	public String getIs_ref() {
		return is_ref;
	}

	public void setIs_ref(String isRef) {
		is_ref = isRef;
	}

	public String getRef_task() {
		return ref_task;
	}

	public void setRef_task(String refTask) {
		ref_task = refTask;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<XmlColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<XmlColumn> columns) {
		this.columns = columns;
	}
	
	
}
