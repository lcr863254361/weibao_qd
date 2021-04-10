package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabDevDataStashDao;
import com.orient.sysmodel.domain.collabdev.data.CollabDevDataStash;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabDevDataStashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 16:41
 * @Version 1.0
 **/
@Service
public class CollabDevDataStashService extends BaseService<CollabDevDataStash> implements ICollabDevDataStashService {

    @Autowired
    ICollabDevDataStashDao collabDevDataStashDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabDevDataStashDao;
    }

}
