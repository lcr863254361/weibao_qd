package com.orient.background.business;

import com.orient.sysmodel.domain.quantity.CfQuantityTemplateDO;
import com.orient.sysmodel.service.quantity.IQuantityTemplateService;
import com.orient.web.base.BaseHibernateBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class QuantityTemplateBusiness extends BaseHibernateBusiness<CfQuantityTemplateDO> {

    @Autowired
    IQuantityTemplateService quantityTemplateService;

    @Override
    public IQuantityTemplateService getBaseService() {
        return quantityTemplateService;
    }
}
