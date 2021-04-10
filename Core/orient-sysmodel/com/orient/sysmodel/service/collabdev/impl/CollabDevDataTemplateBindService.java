package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.impl.CollabDevDataTemplateBindDao;
import com.orient.sysmodel.domain.collabdev.data.CollabDevDataTemplateBind;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabDevDataTemplateBindService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 16:44
 * @Version 1.0
 **/
@Service
public class CollabDevDataTemplateBindService extends BaseService<CollabDevDataTemplateBind> implements ICollabDevDataTemplateBindService {

    @Autowired
    CollabDevDataTemplateBindDao collabDevDataTemplateBindDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabDevDataTemplateBindDao;
    }

}
