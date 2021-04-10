package com.orient.sysmodel.service.form.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.form.IModelFormViewDao;
import com.orient.sysmodel.domain.form.ModelFormViewEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.form.IModelFormViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by enjoy on 2016/3/18 0018.
 */
@Service
public class ModelFormViewService extends BaseService<ModelFormViewEntity> implements IModelFormViewService {

    @Autowired
    IModelFormViewDao modelFormViewDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.modelFormViewDao;
    }


}
