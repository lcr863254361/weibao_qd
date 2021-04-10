package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabTagDao;
import com.orient.sysmodel.domain.collabdev.datamgrrealtion.CollabTag;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabTagService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 20:24
 * @Version 1.0
 **/
@Service
public class CollabTagService extends BaseService<CollabTag> implements ICollabTagService {

    @Autowired
    ICollabTagDao collabTagDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabTagDao;
    }
}
