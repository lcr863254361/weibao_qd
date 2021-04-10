package com.orient.sysmodel.service.pvm.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.pvm.ICheckModelManageDao;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.pvm.ICheckModelManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.orient.sysmodel.domain.pvm.CwmSysCheckmodelsetEntity;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class CheckModelManageService extends BaseService<CwmSysCheckmodelsetEntity> implements ICheckModelManageService {

    @Autowired
    ICheckModelManageDao checkModelManageDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.checkModelManageDao;
    }
}
