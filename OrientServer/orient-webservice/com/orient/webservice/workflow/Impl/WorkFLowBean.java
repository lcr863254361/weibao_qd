package com.orient.webservice.workflow.Impl;

import org.jbpm.api.ProcessEngine;

import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.metamodel.metaengine.MetaUtil;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;

public class WorkFLowBean {
	
	public IBusinessModelService getBusinessModelService() {
		return businessModelService;
	}

	public void setBusinessModelService(IBusinessModelService businessModelService) {
		this.businessModelService = businessModelService;
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public MetaUtil getMetaEngine() {
		return metaEngine;
	}

	public void setMetaEngine(MetaUtil metaEngine) {
		this.metaEngine = metaEngine;
	}
	
	public MetaDAOFactory getMetadaofactory() {
		return metadaofactory;
	}

	public void setMetadaofactory(MetaDAOFactory metadaofactory) {
		this.metadaofactory = metadaofactory;
	}

	protected ProcessEngine processEngine;
	
	protected MetaUtil metaEngine;
	
	protected IBusinessModelService businessModelService;
	
	protected MetaDAOFactory metadaofactory;
}
