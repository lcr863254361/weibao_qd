package com.orient.sysmodel.domain.workflow;
// default package

/**
 * JbpmVariable entity. @author MyEclipse Persistence Tools
 */
public class JbpmVariable extends AbstractJbpmVariable implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public JbpmVariable() {
	}

	/** full constructor */
	public JbpmVariable(String processname, String piid, String taskname,
			String taskid, String variabletype, String key, String value, String taskBelonged) {
		super(processname, piid, taskname, taskid, variabletype, key, value, taskBelonged);
	}
	
	/** 不设置所属任务 */
	public JbpmVariable(String processname, String piid, String taskname,
			String taskid, String variabletype, String key, String value) {
		super(processname, piid, taskname, taskid, variabletype, key, value, "");
	}

}
