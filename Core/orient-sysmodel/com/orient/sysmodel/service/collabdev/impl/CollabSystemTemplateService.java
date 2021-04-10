package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabSystemTemplateDao;
import com.orient.sysmodel.domain.collabdev.CollabSystemTemplate;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabSystemTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 20:17
 * @Version 1.0
 **/
@Service
public class CollabSystemTemplateService extends BaseService<CollabSystemTemplate>  implements ICollabSystemTemplateService {

    @Autowired
    ICollabSystemTemplateDao collabSystemTemplateDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabSystemTemplateDao;
    }
}
