package com.orient.sysmodel.service.sys.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.sys.ISysLogDao;
import com.orient.sysmodel.domain.sys.SysLog;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.sys.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 日志管理
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class SysLogService extends BaseService<SysLog> implements ISysLogService {

    @Autowired
    ISysLogDao sysLogDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.sysLogDao;
    }
}
