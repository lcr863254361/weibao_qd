package com.orient.sysmodel.service.form.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.form.IModelGridViewDao;
import com.orient.sysmodel.domain.form.ModelGridViewEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.form.IModelGridViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 模型表格模板处理
 *
 * @author enjoy
 * @creare 2016-04-06 9:44
 */
@Service
public class ModelGridViewService extends BaseService<ModelGridViewEntity> implements IModelGridViewService {

    @Autowired
    IModelGridViewDao modelGridViewDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.modelGridViewDao;
    }
}
