package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabResultBindDao;
import com.orient.sysmodel.domain.collabdev.data.CollabResultBind;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabResultBindSercive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 16:59
 * @Version 1.0
 **/
@Service
public class CollabResultBindService extends BaseService<CollabResultBind> implements ICollabResultBindSercive {

    @Autowired
    ICollabResultBindDao collabResultBindDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabResultBindDao;
    }

}
