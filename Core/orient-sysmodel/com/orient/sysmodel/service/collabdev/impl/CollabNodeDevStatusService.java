package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabNodeDevStatusDao;
import com.orient.sysmodel.domain.collabdev.CollabNodeDevStatus;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabNodeDevStatusService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 16:53
 * @Version 1.0
 **/
@Service
public class CollabNodeDevStatusService extends BaseService<CollabNodeDevStatus> implements ICollabNodeDevStatusService {

    @Autowired
    ICollabNodeDevStatusDao collabNodeDevStatusDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabNodeDevStatusDao;
    }

}
