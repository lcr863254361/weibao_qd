package com.orient.sysmodel.service.taskdata.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.taskdata.IDataObjectOldVersionDao;
import com.orient.sysmodel.domain.taskdata.DataObjectOldVersionEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.taskdata.IDataObjectOldVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据实例历史数据
 *
 * @author mengbin
 * @create 2016-07-13 下午4:24
 */
@Service
public class DataObjectOldVersionService extends BaseService<DataObjectOldVersionEntity> implements IDataObjectOldVersionService {

    @Autowired
    IDataObjectOldVersionDao dataObjectOldVersionDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.dataObjectOldVersionDao;
    }
}
