package com.orient.sysmodel.domain.workflow;
// default package

/**
 * AbstractJbpmVariable entity provides the base persistence definition of the
 * JbpmVariable entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractJbpmVariable implements java.io.Serializable {

	// Fields

	private String id;
	private String processname;
	private String piid;
	private String taskname;
	private String taskid;
	private String variabletype;
	private String key;
	private String value;
	private String taskbelonged; //所属任务

	// Constructors

	/** default constructor */
	public AbstractJbpmVariable() {
	}

	/** full constructor */
	public AbstractJbpmVariable(String processname, String piid,
			String taskname, String taskid, String variabletype, String key,
			String value, String taskbelonged) {
		this.processname = processname;
		this.piid = piid;
		this.taskname = taskname;
		this.taskid = taskid;
		this.variabletype = variabletype;
		this.key = key;
		this.value = value;
		this.taskbelonged = taskbelonged;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessname() {
		return this.processname;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public String getPiid() {
		return this.piid;
	}

	public void setPiid(String piid) {
		this.piid = piid;
	}

	public String getTaskname() {
		return this.taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getTaskid() {
		return this.taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getVariabletype() {
		return this.variabletype;
	}

	public void setVariabletype(String variabletype) {
		this.variabletype = variabletype;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTaskbelonged() {
		return taskbelonged;
	}

	public void setTaskbelonged(String taskbelonged) {
		this.taskbelonged = taskbelonged;
	}




}