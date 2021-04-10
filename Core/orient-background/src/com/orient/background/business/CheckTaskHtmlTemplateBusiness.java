package com.orient.background.business;

import com.orient.sysmodel.domain.pvm.CwmTaskcheckHtmlEntity;
import com.orient.sysmodel.service.pvm.ICheckTaskHtmlTemplateService;
import com.orient.web.base.BaseHibernateBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * xax
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class CheckTaskHtmlTemplateBusiness extends BaseHibernateBusiness<CwmTaskcheckHtmlEntity> {

    @Autowired
    ICheckTaskHtmlTemplateService checkTaskHtmlTemplateService;

    @Override
    public ICheckTaskHtmlTemplateService getBaseService() {
        return checkTaskHtmlTemplateService;
    }
}
