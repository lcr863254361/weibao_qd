package com.orient.sysmodel.service.taskdata.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.taskdata.IDataTypeEntityDao;
import com.orient.sysmodel.domain.taskdata.DataTypeEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.taskdata.IDataTypeEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 研发数据类型管理数据服务实现
 *
 * @author mengbin
 * @create 2016-07-04 下午2:37
 */
@Service
public class DataTypeEntityService extends BaseService<DataTypeEntity> implements IDataTypeEntityService {

    @Autowired
    IDataTypeEntityDao dataTypeEntityDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.dataTypeEntityDao;
    }
}
