package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabSubDataModelBindDao;
import com.orient.sysmodel.domain.collabdev.datamgrrealtion.CollabSubDataModelBind;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabSubDataModelBindService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 20:13
 * @Version 1.0
 **/
@Service
public class CollabSubDataModelBindService extends BaseService<CollabSubDataModelBind> implements ICollabSubDataModelBindService {
    @Autowired
    ICollabSubDataModelBindDao collabSubDataModelBindDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabSubDataModelBindDao;
    }

}
