package com.orient.sysmodel.service.sys.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.sys.IUserLinkDao;
import com.orient.sysmodel.domain.sys.CwmSysUserLinkEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.sys.IUserLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Portal service层
 *
 * @author enjoy
 * @createTime 2016-06-01 16:38
 */
@Service
public class UserLinkService extends BaseService<CwmSysUserLinkEntity> implements IUserLinkService {

    @Autowired
    IUserLinkDao userLinkDao;

    @Override
    public IBaseDao getBaseDao() {
        return userLinkDao;
    }
}
