package com.orient.sysmodel.service.quantity.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.quantity.IQuantityTemplateDao;
import com.orient.sysmodel.domain.quantity.CfQuantityTemplateDO;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.quantity.IQuantityTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class QuantityTemplateService extends BaseService<CfQuantityTemplateDO> implements IQuantityTemplateService {

    @Autowired
    IQuantityTemplateDao quantityTemplateDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.quantityTemplateDao;
    }
}
