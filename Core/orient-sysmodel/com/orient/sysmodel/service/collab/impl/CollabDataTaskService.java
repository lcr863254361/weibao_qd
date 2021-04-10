package com.orient.sysmodel.service.collab.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collab.ICollabDataTaskDao;
import com.orient.sysmodel.domain.collab.CollabDataTask;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collab.ICollabDataTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mengbin
 * @create 2016-08-23 下午3:28
 */
@Service
public class CollabDataTaskService extends BaseService<CollabDataTask> implements ICollabDataTaskService {

    @Autowired
    ICollabDataTaskDao collabDataTaskDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabDataTaskDao;
    }
}
