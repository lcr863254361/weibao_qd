package com.orient.sysmodel.service.flow.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.flow.IHisTaskDao;
import com.orient.sysmodel.domain.his.CwmSysHisTaskEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.flow.IHisTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class HisTaskService extends BaseService<CwmSysHisTaskEntity> implements IHisTaskService {

    @Autowired
    IHisTaskDao hisTaskDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.hisTaskDao;
    }
}
