package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabShareFilePrivilegeDao;
import com.orient.sysmodel.domain.collabdev.datashare.CollabShareFilePrivilege;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabShareFilePrivilegeService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 19:57
 * @Version 1.0
 **/
@Service
public class CollabShareFilePrivilegeService extends BaseService<CollabShareFilePrivilege> implements ICollabShareFilePrivilegeService {
    @Autowired
    ICollabShareFilePrivilegeDao collabShareFilePrivilegeDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabShareFilePrivilegeDao;
    }
}
