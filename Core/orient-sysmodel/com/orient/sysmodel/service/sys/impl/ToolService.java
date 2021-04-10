package com.orient.sysmodel.service.sys.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.sys.IToolDao;
import com.orient.sysmodel.domain.sys.CwmSysToolsEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.sys.IToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 工具管理
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class ToolService extends BaseService<CwmSysToolsEntity> implements IToolService {

    @Autowired
    IToolDao toolDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.toolDao;
    }
}
