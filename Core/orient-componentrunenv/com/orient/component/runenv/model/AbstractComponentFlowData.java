package com.orient.component.runenv.model;

public abstract class AbstractComponentFlowData  {


	private String id;
	private String projectCode;//协同试验项目编号
	private String key;
	private String value;
	
	public AbstractComponentFlowData(String id, String projectCode, String key, String value) {
		this.id = id;
		this.projectCode = projectCode;
		this.key = key;
		this.setValue(value);
	}
	
	public AbstractComponentFlowData() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
