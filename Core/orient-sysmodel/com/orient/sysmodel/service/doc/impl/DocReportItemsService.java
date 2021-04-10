package com.orient.sysmodel.service.doc.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.doc.IDocReportItemsDao;
import com.orient.sysmodel.domain.doc.CwmDocReportItemsEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.doc.IDocReportItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class DocReportItemsService extends BaseService<CwmDocReportItemsEntity> implements IDocReportItemsService {

    @Autowired
    IDocReportItemsDao docReportItemsDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.docReportItemsDao;
    }
}
