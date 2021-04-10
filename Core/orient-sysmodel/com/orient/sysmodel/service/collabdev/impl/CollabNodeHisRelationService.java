package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabNodeHisRelationDao;
import com.orient.sysmodel.domain.collabdev.CollabNodeHisRelation;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabNodeHisRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 16:55
 * @Version 1.0
 **/
@Service
public class CollabNodeHisRelationService extends BaseService<CollabNodeHisRelation> implements ICollabNodeHisRelationService {

    @Autowired
    ICollabNodeHisRelationDao collabNodeHisRelationDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabNodeHisRelationDao;
    }

}
