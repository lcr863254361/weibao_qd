package com.orient.workflow.cmd;

import java.io.ByteArrayInputStream;

import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.session.RepositorySession;

import com.orient.workflow.form.EdmFormCache;
import com.orient.workflow.form.model.XmlFormReader;

public class GetFormReaderCommand implements Command<XmlFormReader>{

	//serialVersionUID is
	
	private static final long serialVersionUID = -490490876813680168L;
	
	private String pdId = "";
	
	public GetFormReaderCommand(String pdId)
	{
		this.pdId = pdId;
	}
	
	public void bindPdId(String pdId) {
		this.pdId = pdId;
	}
	
	@Override
	public XmlFormReader execute(Environment environment) throws Exception {
		
		RepositorySession repositorySession = environment.get(RepositorySession.class); 
		EdmFormCache formCache = environment.get(EdmFormCache.class);
		XmlFormReader xmlFormReader = formCache.get(pdId);
		if(null == xmlFormReader)
		{
			ProcessDefinitionImpl pd = repositorySession.findProcessDefinitionById(pdId);
			String imageName = pd.getImageResourceName();
			String fileName = imageName.replace(".png", "_form.xml");
			byte[] formBytes = repositorySession.getBytes(pd.getDeploymentId(), fileName);
			if (formBytes != null) {
				ByteArrayInputStream in =  new ByteArrayInputStream(formBytes);
				xmlFormReader = new XmlFormReader(in);
				formCache.set(pdId, xmlFormReader);
			 }
		}
		return xmlFormReader;
	}
	
}
