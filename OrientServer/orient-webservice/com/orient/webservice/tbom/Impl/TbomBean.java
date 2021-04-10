package com.orient.webservice.tbom.Impl;
 
import com.orient.background.business.ModelGridViewBusiness;
import com.orient.metamodel.metaengine.MetaUtil;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.sysmodel.service.role.RoleService;
import com.orient.sysmodel.service.tbom.TbomService;

public  class TbomBean{
	protected MetaUtil metaEngine;
	protected TbomService tbomService;  
	protected ISqlEngine sqlEngine;
	protected MetaDAOFactory metadaofactory;
	protected IRoleUtil roleEngine;
	protected RoleService roleService;
	protected ModelGridViewBusiness modelGridViewBusiness;
	
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public IRoleUtil getRoleEngine() {
		return roleEngine;
	}
	public void setRoleEngine(IRoleUtil roleEngine) {
		this.roleEngine = roleEngine;
	}
	public MetaDAOFactory getMetadaofactory() {
		return metadaofactory;
	}
	public void setMetadaofactory(MetaDAOFactory metadaofactory) {
		this.metadaofactory = metadaofactory;
	}
	public MetaUtil getMetaEngine() {
		return metaEngine;
	}
	public void setMetaEngine(MetaUtil metaEngine) {
		this.metaEngine = metaEngine;
	}
	public TbomService getTbomService() {
		return tbomService;
	}
	public void setTbomService(TbomService tbomService) {
		this.tbomService = tbomService;
	}
	public ISqlEngine getSqlEngine() {
		return sqlEngine;
	}
	public void setSqlEngine(ISqlEngine sqlEngine) {
		this.sqlEngine = sqlEngine;
	}
	
	protected String isNull(String a) {
		String result = a == null ? "" : a;
		return result;
	}

	public ModelGridViewBusiness getModelGridViewBusiness() {
		return modelGridViewBusiness;
	}

	public void setModelGridViewBusiness(ModelGridViewBusiness modelGridViewBusiness) {
		this.modelGridViewBusiness = modelGridViewBusiness;
	}

}
