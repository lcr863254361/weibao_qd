package com.orient.sysmodel.service.pvm.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.pvm.IPVMMulCheckRelationDao;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.pvm.IPVMMulCheckRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.orient.sysmodel.domain.pvm.CwmTaskmulcheckrelationEntity;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class PVMMulCheckRelationService extends BaseService<CwmTaskmulcheckrelationEntity> implements IPVMMulCheckRelationService {

    @Autowired
    IPVMMulCheckRelationDao pVMMulCheckRelationDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.pVMMulCheckRelationDao;
    }
}
