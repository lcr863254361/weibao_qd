package com.orient.sysmodel.service.sys.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.sys.IBackUpJobDao;
import com.orient.sysmodel.domain.sys.QuartzJobEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.sys.IBackUpJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * QuartzJobEntity
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class BackUpJobService extends BaseService<QuartzJobEntity> implements IBackUpJobService {

    @Autowired
    IBackUpJobDao backUpJobDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.backUpJobDao;
    }
}
