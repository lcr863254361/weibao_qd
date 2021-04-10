package com.orient.sysmodel.service.quantity.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.quantity.IUnitDao;
import com.orient.sysmodel.domain.quantity.CwmSysNumberunitDO;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.quantity.IUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class UnitService extends BaseService<CwmSysNumberunitDO> implements IUnitService {

    @Autowired
    IUnitDao unitDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.unitDao;
    }
}
