package com.orient.sysmodel.service.quantity.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.quantity.IQuantityDao;
import com.orient.sysmodel.domain.quantity.CfQuantityDO;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.quantity.IQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class QuantityService extends BaseService<CfQuantityDO> implements IQuantityService {

    @Autowired
    IQuantityDao quantityDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.quantityDao;
    }
}
