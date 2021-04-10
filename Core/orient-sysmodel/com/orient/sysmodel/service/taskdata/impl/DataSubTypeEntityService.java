package com.orient.sysmodel.service.taskdata.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.taskdata.IDataSubTypeEntityDao;
import com.orient.sysmodel.domain.taskdata.DataSubTypeEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.taskdata.IDataSubTypeEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mengbin
 * @create 2016-07-04 下午7:26
 */
@Service
public class DataSubTypeEntityService extends BaseService<DataSubTypeEntity> implements IDataSubTypeEntityService {

    @Autowired
    IDataSubTypeEntityDao dataSubTypeEntityDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.dataSubTypeEntityDao;
    }

    @Override
    public int getNextOrderNum() {
        return dataSubTypeEntityDao.getNextOrderNum();

    }
}
