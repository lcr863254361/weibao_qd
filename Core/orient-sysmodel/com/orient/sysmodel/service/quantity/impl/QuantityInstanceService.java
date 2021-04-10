package com.orient.sysmodel.service.quantity.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.quantity.IQuantityInstanceDao;
import com.orient.sysmodel.domain.quantity.CfQuantityInstanceDO;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.quantity.IQuantityInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class QuantityInstanceService extends BaseService<CfQuantityInstanceDO> implements IQuantityInstanceService {

    @Autowired
    IQuantityInstanceDao quantityInstanceDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.quantityInstanceDao;
    }
}
