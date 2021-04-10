package com.orient.modeldata.business;

import com.orient.sysmodel.domain.modeldata.CwmModelDataUnitEntity;
import com.orient.sysmodel.service.modeldata.IModelDataUnitService;
import com.orient.web.base.BaseHibernateBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 模型数据单位中间表
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class ModelDataUnitBusiness extends BaseHibernateBusiness<CwmModelDataUnitEntity> {

    @Autowired
    IModelDataUnitService modelDataUnitService;

    @Override
    public IModelDataUnitService getBaseService() {
        return modelDataUnitService;
    }
}
