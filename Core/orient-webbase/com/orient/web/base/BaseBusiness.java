package com.orient.web.base;

import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.metamodel.metaengine.MetaUtil;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.roleengine.IRoleUtil;
import org.springframework.beans.factory.annotation.Autowired;


public class BaseBusiness {

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    public IBusinessModelService businessModelService;

    @Autowired
    protected IRoleUtil roleEngine;

    @Autowired
    protected MetaUtil metaEngine;

    @Autowired
    protected MetaDAOFactory metaDaoFactory;

}
