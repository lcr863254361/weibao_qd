package com.orient.workflow.form.model;

@SuppressWarnings("serial")
public class JpdlFormProp  implements java.io.Serializable{
	
	/** 
	 * 关联模型的columnID
	 */
	private String colid;
	
	/**
	 * 关联的流程变量Id
	 */
	private String varId;
	
	/**
	 * 属性显示名
	 */
	private String name;
	
	/**
	 * 属性所关联的模型ID
	 */
	private String tableid;
		
	/**
	 * 0:输入
	 * 1:输出
	 * 2:输入输出
	 */
	private String inOrOutType;
	
	/**
	 * 属性类型:
	 * String
	 * Float
	 * Double
	 * Date
	 * Integer
	 * Blob
	 */
	private String propType;

	public String getColid() {
		return colid;
	}

	public void setColid(String colid) {
		this.colid = colid;
	}

	public String getVarId() {
		return varId;
	}

	public void setVarId(String varId) {
		this.varId = varId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	public String getInOrOutType() {
		return inOrOutType;
	}

	public void setInOrOutType(String inOrOutType) {
		this.inOrOutType = inOrOutType;
	}

	public String getPropType() {
		return propType;
	}

	public void setPropType(String propType) {
		this.propType = propType;
	}
	
	
	
}
