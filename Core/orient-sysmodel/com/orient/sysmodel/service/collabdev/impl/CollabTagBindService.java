package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabTagBindDao;
import com.orient.sysmodel.domain.collabdev.datamgrrealtion.CollabTagBind;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabTagBindService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 20:20
 * @Version 1.0
 **/
@Service
public class CollabTagBindService extends BaseService<CollabTagBind> implements ICollabTagBindService {

    @Autowired
    ICollabTagBindDao collabTagBindDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabTagBindDao;
    }
}
