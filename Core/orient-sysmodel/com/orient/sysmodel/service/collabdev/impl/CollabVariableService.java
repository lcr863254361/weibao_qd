package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabVariableDao;
import com.orient.sysmodel.domain.collabdev.CollabVariable;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabVariableService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 20:26
 * @Version 1.0
 **/
@Service
public class CollabVariableService extends BaseService<CollabVariable> implements ICollabVariableService {

    @Autowired
    ICollabVariableDao collabVariableDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabVariableDao;
    }
}
