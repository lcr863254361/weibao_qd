package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabShareFilePrivilegeDetailGroupDao;
import com.orient.sysmodel.domain.collabdev.datashare.CollabShareFilePrivilegeDetailGroup;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabShareFilePrivilegeDetailGroupService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 19:57
 * @Version 1.0
 **/
@Service
public class CollabShareFilePrivilegeDetailGroupService extends BaseService<CollabShareFilePrivilegeDetailGroup> implements ICollabShareFilePrivilegeDetailGroupService {

    @Autowired
    ICollabShareFilePrivilegeDetailGroupDao collabShareFilePrivilegeDetailGroupDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabShareFilePrivilegeDetailGroupDao;
    }

}
