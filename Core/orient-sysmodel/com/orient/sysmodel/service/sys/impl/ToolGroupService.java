package com.orient.sysmodel.service.sys.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.sys.IToolGroupDao;
import com.orient.sysmodel.domain.sys.CwmSysToolsGroupEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.sys.IToolGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 工具分组管理
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class ToolGroupService extends BaseService<CwmSysToolsGroupEntity> implements IToolGroupService {

    @Autowired
    IToolGroupDao toolGroupDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.toolGroupDao;
    }
}
