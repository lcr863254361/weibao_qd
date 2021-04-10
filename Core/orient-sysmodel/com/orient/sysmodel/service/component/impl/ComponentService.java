package com.orient.sysmodel.service.component.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.component.IComponentDao;
import com.orient.sysmodel.domain.component.CwmComponentEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.component.IComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 组件管理
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class ComponentService extends BaseService<CwmComponentEntity> implements IComponentService {

    @Autowired
    IComponentDao componentDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.componentDao;
    }
}
