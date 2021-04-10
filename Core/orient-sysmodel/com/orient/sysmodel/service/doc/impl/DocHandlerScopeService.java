package com.orient.sysmodel.service.doc.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.doc.IDocHandlerScopeDao;
import com.orient.sysmodel.domain.doc.CwmDocColumnScopeEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.doc.IDocHandlerScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class DocHandlerScopeService extends BaseService<CwmDocColumnScopeEntity> implements IDocHandlerScopeService {

    @Autowired
    IDocHandlerScopeDao docHandlerScopeDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.docHandlerScopeDao;
    }
}
