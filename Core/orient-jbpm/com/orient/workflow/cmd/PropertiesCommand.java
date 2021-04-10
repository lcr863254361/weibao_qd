package com.orient.workflow.cmd;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jbpm.api.RepositoryService;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.repository.DeploymentImpl;
import org.jbpm.pvm.internal.repository.DeploymentProperty;

import com.orient.utils.CommonTools;
import com.orient.workflow.bean.DeployProperty;

public class PropertiesCommand implements Command<Object>{

	//serialVersionUID is
	private static final long serialVersionUID = 8556825404692962211L;
	
	private String deploymentId;
	
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public PropertiesCommand(String deploymentId){
		this.deploymentId = deploymentId;
	}
	@Override
	public Object execute(Environment environment) throws Exception {
		//管理流程定义的Service
		RepositoryService repositoryService = environment.get(RepositoryService.class);
		DeploymentImpl deploymentImpl = (DeploymentImpl)repositoryService.createDeploymentQuery().deploymentId(deploymentId).uniqueResult();
		Set<DeploymentProperty> dps = deploymentImpl.getObjectProperties();
		List<DeployProperty> result = new ArrayList<DeployProperty>();
		for(DeploymentProperty dp : dps)
		{
			String objName = dp.getObjectName();
			String key = dp.getKey();
			String value = CommonTools.Obj2String(dp.getValue());
			DeployProperty deployProperty = new DeployProperty(objName, key, value);
			result.add(deployProperty);
		}
		return result;
	}
	
}
