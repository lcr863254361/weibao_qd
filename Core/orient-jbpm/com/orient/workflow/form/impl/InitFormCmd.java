package com.orient.workflow.form.impl;

import java.io.ByteArrayInputStream;
import java.util.Set;

import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.repository.DeploymentImpl;
import org.jbpm.pvm.internal.session.RepositorySession;

import com.orient.workflow.form.EdmFormCache;
import com.orient.workflow.form.model.XmlFormReader;

/**
 * @ClassName InitFormCmd
 * 缓存该部署文件所对应的所有表单
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 * 该类暂时不需要使用了 孟斌
 */

@Deprecated
public class InitFormCmd implements Command<String> {

	/** 
	* @Fields serialVersionUID : 
	*/
	
	private static final long serialVersionUID = 1L;
	
	private DeploymentImpl deployment;
	public InitFormCmd(DeploymentImpl deployment){
		this.deployment = deployment;
	}
	@Override
	public String execute(Environment environment) throws Exception {
		RepositorySession repositorySession = environment.get(RepositorySession.class); 
		EdmFormCache formCache = environment.get(EdmFormCache.class);
		//流程定义ID集合
		Set<String> pdIds = deployment.getProcessDefinitionIds();
		 
		for(String pdId:pdIds){
			ProcessDefinitionImpl pd = repositorySession.findProcessDefinitionById(pdId);
			String imageName = pd.getImageResourceName();
			String fileName = imageName.replace(".png", "_form.xml");
			byte[] formBytes = repositorySession.getBytes(deployment.getId(), fileName);
			if (formBytes!=null) {
				ByteArrayInputStream in =  new ByteArrayInputStream(formBytes);
				XmlFormReader formReader = new XmlFormReader(in);
				formCache.set(pdId, formReader);
			 }
		}
		return deployment.getId();
	}

}
