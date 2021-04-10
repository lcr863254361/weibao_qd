package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabShareFilePrivilegeDetailDao;
import com.orient.sysmodel.domain.collabdev.datashare.CollabShareFilePrivilegeDetail;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabShareFilePrivilegeDetailService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 19:57
 * @Version 1.0
 **/
@Service
public class CollabShareFilePrivilegeDetailService extends BaseService<CollabShareFilePrivilegeDetail> implements ICollabShareFilePrivilegeDetailService{
    @Autowired
    ICollabShareFilePrivilegeDetailDao collabShareFilePrivilegeDetailDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabShareFilePrivilegeDetailDao;
    }
}
