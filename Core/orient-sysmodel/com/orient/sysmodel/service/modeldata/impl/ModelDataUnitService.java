package com.orient.sysmodel.service.modeldata.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.modeldata.IModelDataUnitDao;
import com.orient.sysmodel.domain.modeldata.CwmModelDataUnitEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.modeldata.IModelDataUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 模型数据单位中间表
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class ModelDataUnitService extends BaseService<CwmModelDataUnitEntity> implements IModelDataUnitService {

    @Autowired
    IModelDataUnitDao modelDataUnitDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.modelDataUnitDao;
    }
}
