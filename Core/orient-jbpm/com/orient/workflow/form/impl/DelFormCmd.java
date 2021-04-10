package com.orient.workflow.form.impl;

import java.util.Set;

import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.repository.DeploymentImpl; 

import com.orient.workflow.form.EdmFormCache;

/**
 * @ClassName DelFormCmd
 * 删除该部署文件所对应的所有缓存表单
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class DelFormCmd  implements Command<String>{

	/** 
	* @Fields serialVersionUID : 
	*/
	
	private static final long serialVersionUID = 1L;
	private DeploymentImpl deployment;
	public DelFormCmd(DeploymentImpl deployment){
		this.deployment = deployment;
	}
	@Override
	public String execute(Environment environment) throws Exception { 
		EdmFormCache formCache = environment.get(EdmFormCache.class);
		//流程定义ID集合
		Set<String> pdIds = deployment.getProcessDefinitionIds();
		for(String pdId:pdIds){
			formCache.remove(pdId);
		}
		return deployment.getId();
	}

}
