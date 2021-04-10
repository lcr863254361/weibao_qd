package com.orient.sysmodel.service.doc.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.doc.IDocHandlerDao;
import com.orient.sysmodel.domain.doc.CwmDocHandlerEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.doc.IDocHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class DocHandlerService extends BaseService<CwmDocHandlerEntity> implements IDocHandlerService {

    @Autowired
    IDocHandlerDao docHandlerDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.docHandlerDao;
    }
}
