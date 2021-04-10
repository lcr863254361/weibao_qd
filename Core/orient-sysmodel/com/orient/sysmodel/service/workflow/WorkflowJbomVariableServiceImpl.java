package com.orient.sysmodel.service.workflow;

import java.util.List;

import com.orient.sysmodel.domain.workflow.JbpmVariable;
import com.orient.sysmodel.domain.workflow.JbpmVariableDAO;
import com.orient.sysmodel.operationinterface.IWorkflowJbpmVariableService;

public class WorkflowJbomVariableServiceImpl implements	IWorkflowJbpmVariableService {
	
	public JbpmVariableDAO getDao() {
		return dao;
	}

	public void setDao(JbpmVariableDAO dao) {
		this.dao = dao;
	}
	
	//数据库操作层
	private JbpmVariableDAO dao;

	@Override
	public void attachClean(JbpmVariable instance) {
		dao.attachClean(instance);
	}

	@Override
	public void attachDirty(JbpmVariable instance) {
		dao.attachDirty(instance);
		
	}

	@Override
	public void delete(JbpmVariable persistentInstance) {
		dao.delete(persistentInstance);
		
	}

	@Override
	public List findAll() {
		return dao.findAll();
	}

	@Override
	public List findByExample(JbpmVariable instance) {
		return dao.findByExample(instance);
	}

	@Override
	public JbpmVariable findById(String id) {
		return dao.findById(id);
	}

	@Override
	public List findByKey(Object key) {
		return dao.findByKey(key);
	}

	@Override
	public List findByPiid(Object piid) {
		return dao.findByPiid(piid);
	}

	@Override
	public List findByProcessname(Object processname) {
		return dao.findByProcessname(processname);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		return dao.findByProperty(propertyName, value);
	}

	@Override
	public List findByTaskid(Object taskid) {
		return dao.findByTaskid(taskid);
	}

	@Override
	public List findByTaskname(Object taskname) {
		return dao.findByTaskname(taskname);
	}

	@Override
	public List findByValue(Object value) {
		return dao.findByValue(value);
	}

	@Override
	public List findByVariabletype(Object variabletype) {
		return dao.findByVariabletype(variabletype);
	}

	@Override
	public JbpmVariable merge(JbpmVariable detachedInstance) {
		return dao.merge(detachedInstance);
	}

	@Override
	public void save(JbpmVariable transientInstance) {
		dao.save(transientInstance);
	}

	@Override
	public JbpmVariable getJbpmRefTaskDataId(String processName, String taskName,String taskId,
			String refTaskType, String piId, String key) {
		
		return dao.getJbpmRefTaskDataId(processName, taskName, taskId, refTaskType, piId, key);
	}

	@Override
	public String findStartUserByPiId(Object piId) {
		return dao.findStartUserByPiId(piId);
	}
}
