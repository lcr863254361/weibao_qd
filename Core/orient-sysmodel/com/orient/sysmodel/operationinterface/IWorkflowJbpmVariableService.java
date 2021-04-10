package com.orient.sysmodel.operationinterface;

import java.util.List;

import com.orient.sysmodel.domain.workflow.JbpmVariable;

public interface IWorkflowJbpmVariableService {
	
	public void save(JbpmVariable transientInstance);
	
	public void delete(JbpmVariable persistentInstance);
	
	public JbpmVariable findById(java.lang.String id);
	
	public List findByExample(JbpmVariable instance);
	
	public List findByProperty(String propertyName, Object value);
	
	public List findByProcessname(Object processname);
	
	public List findByPiid(Object piid) ;
	
	public List findByTaskname(Object taskname);
	
	public List findByTaskid(Object taskid);
	
	public List findByVariabletype(Object variabletype) ;
	
	public List findByKey(Object key);
	
	public List findByValue(Object value);
	
	public List findAll();
	
	public String findStartUserByPiId(Object piId);
	
	public JbpmVariable merge(JbpmVariable detachedInstance);
	
	public void attachDirty(JbpmVariable instance);
	
	public void attachClean(JbpmVariable instance);
	
	public JbpmVariable getJbpmRefTaskDataId(String processName,String taskName, String taskId, String  refTaskType, String piId, String key);
	
}
