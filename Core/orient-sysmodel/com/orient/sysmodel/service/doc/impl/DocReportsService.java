package com.orient.sysmodel.service.doc.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.doc.IDocReportsDao;
import com.orient.sysmodel.domain.doc.CwmDocReportsEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.doc.IDocReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class DocReportsService extends BaseService<CwmDocReportsEntity> implements IDocReportsService {

    @Autowired
    IDocReportsDao docReportsDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.docReportsDao;
    }
}
