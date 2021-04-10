package com.orient.sysmodel.service.form.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.form.IModelBtnInstanceDao;
import com.orient.sysmodel.domain.form.ModelBtnInstanceEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.form.IModelBtnInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 模型按钮实例Service
 *
 * @author enjoy
 * @creare 2016-04-11 13:58
 */
@Service
public class ModelBtnInstanceService extends BaseService<ModelBtnInstanceEntity> implements IModelBtnInstanceService {

    @Autowired
    IModelBtnInstanceDao modelBtnInstanceDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.modelBtnInstanceDao;
    }
}
