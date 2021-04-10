package com.orient.sysmodel.service.sys.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.sys.IPortalDao;
import com.orient.sysmodel.domain.sys.CwmPortalEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.sys.IPortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Portal service层
 *
 * @author enjoy
 * @createTime 2016-06-01 16:38
 */
@Service
public class PortalService extends BaseService<CwmPortalEntity> implements IPortalService {

    @Autowired
    IPortalDao portalDao;

    @Override
    public IBaseDao getBaseDao() {
        return portalDao;
    }
}
