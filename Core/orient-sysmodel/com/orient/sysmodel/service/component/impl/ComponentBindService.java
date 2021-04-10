package com.orient.sysmodel.service.component.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.component.IComponentBindDao;
import com.orient.sysmodel.domain.component.CwmComponentModelEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.component.IComponentBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 组件绑定管理
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class ComponentBindService extends BaseService<CwmComponentModelEntity> implements IComponentBindService {

    @Autowired
    IComponentBindDao componentBindDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.componentBindDao;
    }
}
