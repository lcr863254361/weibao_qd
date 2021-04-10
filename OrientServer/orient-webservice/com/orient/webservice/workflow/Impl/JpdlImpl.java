package com.orient.webservice.workflow.Impl;

import java.util.Map;

import com.orient.webservice.workflow.IJpdl;

public class JpdlImpl implements IJpdl {

	@Override
	public String deleteJpdl(String name, String version, String username) {
		
		return workFlowJpdl.deleteJpdl(name, version, username);
	}

	@Override
	public Map<String, byte[]> getJdpl(String id) {
		
		return workFlowJpdl.getJdpl(id);
	}

	@Override
	public String getJpdl(String name, String version, String type) {

		return workFlowJpdl.getJpdl(name, version, type);
	}

	@Override
	public String getJpdlInfo() {
		
		return workFlowJpdl.getJpdlInfo();
	}

	@Override
	public String getSource() {
		//得到所有schema集合
		return workFlowSource.getSource();
	}

	@Override
	public String getTableDetail(String tableId, String type) {
		
		return workFlowSource.getTableDetail(tableId, type);
	}

	@Override
	public String setJpdl(String name, String version, String username,
			String type, String info) {
		
		return workFlowJpdl.setJpdl(name, version, username, type, info);
	}
	
	@Override
	public String getAssignee(int type) {
		
		return assignManager.getAssignee(type);
	}
	
	public SourceManager getWorkFlowSource() {
		return workFlowSource;
	}

	public void setWorkFlowSource(SourceManager workFlowSource) {
		this.workFlowSource = workFlowSource;
	}
	
	public JpdlManager getWorkFlowJpdl() {
		return workFlowJpdl;
	}

	public void setWorkFlowJpdl(JpdlManager workFlowJpdl) {
		this.workFlowJpdl = workFlowJpdl;
	}
	
	public AssignManager getAssignManager() {
		return assignManager;
	}

	public void setAssignManager(AssignManager assignManager) {
		this.assignManager = assignManager;
	}

	private SourceManager workFlowSource;
	
	private JpdlManager workFlowJpdl;

	private AssignManager assignManager;
}
