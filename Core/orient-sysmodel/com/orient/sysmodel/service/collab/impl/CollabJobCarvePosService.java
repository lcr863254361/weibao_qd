package com.orient.sysmodel.service.collab.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collab.ICollabJobCarvePosDao;
import com.orient.sysmodel.domain.collab.CollabJobCarvePos;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collab.ICollabJobCarvePosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mengbin
 * @create 2016-08-22 下午3:03
 */
@Service
public class CollabJobCarvePosService extends BaseService<CollabJobCarvePos> implements ICollabJobCarvePosService {

    @Autowired
    ICollabJobCarvePosDao collabJobCarvePosDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabJobCarvePosDao;
    }
}
