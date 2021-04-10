package com.orient.background.business;

import com.orient.sysmodel.domain.quantity.CfQuantityDO;
import com.orient.sysmodel.domain.quantity.CfQuantityTemplateDO;
import com.orient.sysmodel.domain.quantity.CfQuantityTemplateRelationDO;
import com.orient.sysmodel.service.quantity.IQuantityService;
import com.orient.sysmodel.service.quantity.IQuantityTemplateRelationService;
import com.orient.sysmodel.service.quantity.IQuantityTemplateService;
import com.orient.web.base.BaseHibernateBusiness;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class QuantityTemplateRelationBusiness extends BaseHibernateBusiness<CfQuantityTemplateRelationDO> {

    @Autowired
    IQuantityTemplateRelationService quantityTemplateRelationService;

    @Autowired
    IQuantityService quantityService;

    @Autowired
    IQuantityTemplateService quantityTemplateService;

    @Override
    public IQuantityTemplateRelationService getBaseService() {
        return quantityTemplateRelationService;
    }

    public void createRelation(Long templateId, Long[] quantityIds) {
        CfQuantityTemplateDO quantityTemplateDO = quantityTemplateService.getById(templateId);
        for (Long quantityId : quantityIds) {
            CfQuantityDO quantityDO = quantityService.getById(quantityId);
            CfQuantityTemplateRelationDO quantityTemplateRelationDO = new CfQuantityTemplateRelationDO();
            quantityTemplateRelationDO.setBelongQuantity(quantityDO);
            quantityTemplateRelationDO.setBelongTemplate(quantityTemplateDO);
            quantityTemplateRelationService.save(quantityTemplateRelationDO);
        }
    }

    public void removeRelation(Long templateId, Long[] quantityIds) {
        List<CfQuantityTemplateRelationDO> quantityTemplateRelationDOS = quantityTemplateRelationService.list(Restrictions.eq("belongTemplate.id", templateId), Restrictions.in("belongQuantity.id", quantityIds));
        quantityTemplateRelationDOS.forEach(toRemoveDO -> quantityTemplateRelationService.delete(toRemoveDO));
    }
}
