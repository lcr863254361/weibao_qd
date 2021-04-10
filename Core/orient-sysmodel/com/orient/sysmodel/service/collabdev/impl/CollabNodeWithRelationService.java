package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabNodeWithRelationDao;
import com.orient.sysmodel.domain.collabdev.CollabNodeWithRelation;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabNodeWithRelationService;
import com.orient.utils.StringUtil;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class CollabNodeWithRelationService extends BaseService<CollabNodeWithRelation> implements ICollabNodeWithRelationService {

    @Autowired
    ICollabNodeWithRelationDao collabNodeWithRelationDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabNodeWithRelationDao;
    }


    @Override
    public void update(CollabNodeWithRelation collabNodeWithRelation) {
        collabNodeWithRelationDao.update(collabNodeWithRelation);
    }

}
