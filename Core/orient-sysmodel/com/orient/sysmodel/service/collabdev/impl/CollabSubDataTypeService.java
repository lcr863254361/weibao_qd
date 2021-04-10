package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabSubDataTypeDao;
import com.orient.sysmodel.domain.collabdev.datamgrrealtion.CollabSubDataType;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabSubDataTypeService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 20:16
 * @Version 1.0
 **/
@Service
public class CollabSubDataTypeService extends BaseService<CollabSubDataType> implements ICollabSubDataTypeService {

    @Autowired
    ICollabSubDataTypeDao collabSubDataTypeDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabSubDataTypeDao;
    }
}
