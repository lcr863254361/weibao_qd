package com.orient.sysmodel.service.pvm.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.pvm.ICheckModelDataTemplateDao;
import com.orient.sysmodel.domain.pvm.CheckModelDataTemplate;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.pvm.ICheckModelDataTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mengbin
 * @create 2016-07-30 下午2:47
 */
@Service
public class CheckModelDataTemplateService extends BaseService<CheckModelDataTemplate> implements ICheckModelDataTemplateService {

    @Autowired
    ICheckModelDataTemplateDao checkModelDataTemplateDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.checkModelDataTemplateDao;
    }
}
