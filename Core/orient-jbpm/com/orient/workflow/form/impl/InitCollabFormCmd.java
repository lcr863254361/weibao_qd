/**
 * 
 */
package com.orient.workflow.form.impl;

import java.io.ByteArrayInputStream;
import java.util.Set;

import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.session.RepositorySession;

import com.orient.workflow.form.EdmFormCache;
import com.orient.workflow.form.model.XmlFormReader;

/**
 * simple introduction.
 *
 * <p>detailed commentInitCollabFormCmd</p>
 * @author [创建人]  mengbin <br/> 
 * 		   [创建时间] 2016-1-13 下午03:09:09 <br/> 
 * 		   [修改人] mengbin <br/>
 * 		   [修改时间] 2016-1-13 下午03:09:09
 * @see
 */
public class InitCollabFormCmd implements Command<Boolean>{

	/* (non-Javadoc)
	 * @see org.jbpm.api.cmd.Command#execute(org.jbpm.api.cmd.Environment)
	 */
	
	private String processDefinitionId;
	public InitCollabFormCmd(String pdId ){
		this.processDefinitionId = pdId;
	}
	public Boolean execute(Environment environment) throws Exception {
		// TODO Auto-generated method stub
		RepositorySession repositorySession = environment.get(RepositorySession.class); 
		EdmFormCache formCache = environment.get(EdmFormCache.class);
		//流程定义ID集合
		
		ProcessDefinitionImpl pd = repositorySession.findProcessDefinitionById(processDefinitionId);
		String imageName = pd.getImageResourceName();
		String fileName = imageName.replace(".png", "_form.xml");
		byte[] formBytes = repositorySession.getBytes(pd.getDeploymentId(), fileName);
		if (formBytes!=null) {
			ByteArrayInputStream in =  new ByteArrayInputStream(formBytes);
			XmlFormReader formReader = new XmlFormReader(in);
			formCache.set(processDefinitionId, formReader);
			return true;
		 }
		else{
			return false;
		}
		
	}

}
