package com.orient.sysmodel.service.collab.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collab.ICollabDataTaskHisDao;
import com.orient.sysmodel.domain.collab.CollabDataTaskHis;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collab.ICollabDataTaskHisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mengbin
 * @create 2016-08-23 下午3:30
 */
@Service
public class CollabDataTaskHisService extends BaseService<CollabDataTaskHis> implements ICollabDataTaskHisService {

    @Autowired
    ICollabDataTaskHisDao collabDataTaskHisDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabDataTaskHisDao;
    }
}
