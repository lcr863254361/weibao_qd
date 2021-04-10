package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabTagGroupDao;
import com.orient.sysmodel.domain.collabdev.datamgrrealtion.CollabTagGroup;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabTagGroupService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 20:23
 * @Version 1.0
 **/
@Service
public class CollabTagGroupService extends BaseService<CollabTagGroup> implements ICollabTagGroupService {

    @Autowired
    ICollabTagGroupDao collabTagGroupDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabTagGroupDao;
    }
}
