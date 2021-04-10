package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabNodeRelationDao;
import com.orient.sysmodel.domain.collabdev.CollabNodeRelation;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabNodeRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 16:55
 * @Version 1.0
 **/
@Service
public class CollabNodeRelationService extends BaseService<CollabNodeRelation> implements ICollabNodeRelationService {

    @Autowired
    ICollabNodeRelationDao collabNodeRelationDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabNodeRelationDao;
    }

}
