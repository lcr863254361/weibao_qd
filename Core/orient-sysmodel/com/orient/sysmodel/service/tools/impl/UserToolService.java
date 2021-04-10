package com.orient.sysmodel.service.tools.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.tools.impl.UserToolDao;
import com.orient.sysmodel.domain.tools.CwmUserTool;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.tools.IUserToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FailedMsgService
 */
@Service
public class UserToolService extends BaseService<CwmUserTool> implements IUserToolService {

    @Autowired
    private UserToolDao userToolDao;


    @Override
    public IBaseDao getBaseDao() {
        return this.userToolDao;
    }
}
