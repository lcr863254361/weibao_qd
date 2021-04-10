package com.orient.sysmodel.service.collab.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collab.ICollabPrjModelRelationDao;
import com.orient.sysmodel.domain.collab.CollabPrjModelRelationEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collab.ICollabPrjModelRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class CollabPrjModelRelationService extends BaseService<CollabPrjModelRelationEntity> implements ICollabPrjModelRelationService {

    @Autowired
    ICollabPrjModelRelationDao collabPrjModelRelationDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabPrjModelRelationDao;
    }
}
