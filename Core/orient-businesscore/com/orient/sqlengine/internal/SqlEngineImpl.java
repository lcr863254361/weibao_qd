package com.orient.sqlengine.internal;

import com.orient.sqlengine.api.IModelJdbcService;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sqlengine.api.ISysModelService;
import com.orient.sqlengine.api.ITypeMappingBmService;

/**
 * 业务层SqlEngine的实现类
 * @author zhulc@cssrc.com.cn
 * @date Apr 18, 2012
 */
public class SqlEngineImpl implements ISqlEngine {

	private IModelJdbcService bmService;
	private ISysModelService sysModelService;
	private ITypeMappingBmService typeMappingBmService;

	public IModelJdbcService getBmService() {
		return bmService;
	}

	public void setBmService(IModelJdbcService bmService) {
		this.bmService = bmService;
	}

	public ISysModelService getSysModelService() {
		return sysModelService;
	}

	@Override
	public ITypeMappingBmService getTypeMappingBmService() {
		return typeMappingBmService;
	}

	public void setSysModelService(ISysModelService sysModelService) {
		this.sysModelService = sysModelService;
	}

	public void setTypeMappingBmService(ITypeMappingBmService typeMappingBmService) {
		this.typeMappingBmService = typeMappingBmService;
	}
}
