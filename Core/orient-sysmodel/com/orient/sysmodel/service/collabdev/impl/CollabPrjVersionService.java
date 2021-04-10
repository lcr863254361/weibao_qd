package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabPrjVersionDao;
import com.orient.sysmodel.domain.collabdev.CollabPrjVersion;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabPrjVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class CollabPrjVersionService extends BaseService<CollabPrjVersion> implements ICollabPrjVersionService {

    @Autowired
    ICollabPrjVersionDao collabPrjVersionDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabPrjVersionDao;
    }
}
