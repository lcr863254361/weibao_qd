package com.orient.sysmodel.service.sys.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.sys.IUserPortalDao;
import com.orient.sysmodel.domain.sys.CwmSysUserPortalEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.sys.IUserPortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Portal service层
 *
 * @author enjoy
 * @createTime 2016-06-01 16:38
 */
@Service
public class UserPortalService extends BaseService<CwmSysUserPortalEntity> implements IUserPortalService {

    @Autowired
    IUserPortalDao userPortalDao;

    @Override
    public IBaseDao getBaseDao() {
        return userPortalDao;
    }
}
