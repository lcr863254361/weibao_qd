package com.orient.sysmodel.service.sys.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.sys.IDataBackDao;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.sys.IDataBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.orient.sysmodel.domain.sys.CwmBackEntity;
/**
 * 系统数据备份恢复
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class DataBackService extends BaseService<CwmBackEntity> implements IDataBackService {

    @Autowired
    IDataBackDao dataBackDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.dataBackDao;
    }
}
