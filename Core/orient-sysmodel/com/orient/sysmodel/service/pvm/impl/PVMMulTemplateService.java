package com.orient.sysmodel.service.pvm.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.pvm.IPVMMulTemplateDao;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.pvm.IPVMMulTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.orient.sysmodel.domain.pvm.CwmTaskmultiplecheckmodelEntity;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class PVMMulTemplateService extends BaseService<CwmTaskmultiplecheckmodelEntity> implements IPVMMulTemplateService {

    @Autowired
    IPVMMulTemplateDao pVMMulTemplateDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.pVMMulTemplateDao;
    }
}
