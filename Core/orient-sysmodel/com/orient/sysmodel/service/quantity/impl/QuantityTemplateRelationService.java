package com.orient.sysmodel.service.quantity.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.quantity.IQuantityTemplateRelationDao;
import com.orient.sysmodel.domain.quantity.CfQuantityTemplateRelationDO;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.quantity.IQuantityTemplateRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class QuantityTemplateRelationService extends BaseService<CfQuantityTemplateRelationDO> implements IQuantityTemplateRelationService {

    @Autowired
    IQuantityTemplateRelationDao quantityTemplateRelationDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.quantityTemplateRelationDao;
    }
}
