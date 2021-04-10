package com.orient.sysmodel.service.form.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.form.IModelBtnTypeDao;
import com.orient.sysmodel.domain.form.ModelBtnTypeEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.form.IModelBtnTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 模型表格按钮类型
 *
 * @author enjoy
 * @creare 2016-04-09 10:44
 */
@Service
public class ModelBtnTypeService extends BaseService<ModelBtnTypeEntity> implements IModelBtnTypeService {

    @Autowired
    IModelBtnTypeDao modelBtnTypeDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.modelBtnTypeDao;
    }
}
