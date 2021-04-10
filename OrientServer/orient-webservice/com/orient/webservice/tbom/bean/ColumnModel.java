package com.orient.webservice.tbom.bean;

public class ColumnModel {

	/** The id value. */
	public String idValue;

	/** The pid value. */
	public String pidValue;

	/** The name value. */
	public String nameValue;

	/**
	 * Gets the id value.
	 * 
	 * @return the id value
	 */
	public String getIdValue() {
		return idValue;
	}

	/**
	 * Sets the id value.
	 * 
	 * @param idValue
	 *            the new id value
	 */
	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}

	/**
	 * Gets the pid value.
	 * 
	 * @return the pid value
	 */
	public String getPidValue() {
		return pidValue;
	}

	/**
	 * Sets the pid value.
	 * 
	 * @param pidValue
	 *            the new pid value
	 */
	public void setPidValue(String pidValue) {
		this.pidValue = pidValue;
	}

	/**
	 * Gets the name value.
	 * 
	 * @return the name value
	 */
	public String getNameValue() {
		return nameValue;
	}

	/**
	 * Sets the name value.
	 * 
	 * @param nameValue
	 *            the new name value
	 */
	public void setNameValue(String nameValue) {
		this.nameValue = nameValue;
	}

	/**
	 * Instantiates a new column model.
	 * 
	 * @param idValue
	 *            the id value
	 * @param pidValue
	 *            the pid value
	 * @param nameValue
	 *            the name value
	 */
	public ColumnModel(String idValue, String pidValue, String nameValue) {
		super();
		this.idValue = idValue;
		this.pidValue = pidValue;
		this.nameValue = nameValue;
	}
}
