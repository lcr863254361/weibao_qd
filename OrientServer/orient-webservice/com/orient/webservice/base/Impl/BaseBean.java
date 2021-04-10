package com.orient.webservice.base.Impl;

import com.orient.businessmodel.service.impl.BusinessModelServiceImpl;
import com.orient.edm.init.IContextLoadRun;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.metaengine.impl.MetaUtilImpl;
import com.orient.sqlengine.internal.SqlEngineImpl;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.sysmodel.service.user.UserLoginHistoryService;
import com.orient.sysmodel.service.user.UserService;

public class BaseBean {

    protected MetaUtilImpl metaEngine;
    protected MetaDAOFactory metadaofactory;
    protected BusinessModelServiceImpl businessModelService;
    protected UserService userService;
    protected IRoleUtil roleEngine;
    protected SqlEngineImpl sqlEngine;
    protected UserLoginHistoryService userLoginHistoryService;

    public IContextLoadRun getMetaEngine() {
        return metaEngine;
    }

    public void setMetaEngine(MetaUtilImpl metaEngine) {
        this.metaEngine = metaEngine;
    }

    public MetaDAOFactory getMetadaofactory() {
        return metadaofactory;
    }

    public void setMetadaofactory(MetaDAOFactory metadaofactory) {
        this.metadaofactory = metadaofactory;
    }

    public BusinessModelServiceImpl getBusinessModelService() {
        return businessModelService;
    }

    public void setBusinessModelService(BusinessModelServiceImpl businessModelService) {
        this.businessModelService = businessModelService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public SqlEngineImpl getSqlEngine() {
        return sqlEngine;
    }

    public void setSqlEngine(SqlEngineImpl sqlEngine) {
        this.sqlEngine = sqlEngine;
    }

    public UserLoginHistoryService getUserLoginHistoryService() {
        return userLoginHistoryService;
    }

    public void setUserLoginHistoryService(UserLoginHistoryService userLoginHistoryService) {
        this.userLoginHistoryService = userLoginHistoryService;
    }

    public IRoleUtil getRoleEngine() {
        return roleEngine;
    }

    public void setRoleEngine(IRoleUtil roleEngine) {
        this.roleEngine = roleEngine;
    }


}
