package com.orient.sysmodel.service.pvm.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.pvm.ICheckTaskHtmlTemplateDao;
import com.orient.sysmodel.domain.pvm.CwmTaskcheckHtmlEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.pvm.ICheckTaskHtmlTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * xax
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class CheckTaskHtmlTemplateService extends BaseService<CwmTaskcheckHtmlEntity> implements ICheckTaskHtmlTemplateService {

    @Autowired
    ICheckTaskHtmlTemplateDao checkTaskHtmlTemplateDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.checkTaskHtmlTemplateDao;
    }
}
