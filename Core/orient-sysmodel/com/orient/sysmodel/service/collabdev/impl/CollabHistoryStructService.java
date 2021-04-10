package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabHistoryStructDao;
import com.orient.sysmodel.domain.collabdev.CollabHistoryStruct;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabHistoryStructService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 16:49
 * @Version 1.0
 **/
@Service
public class CollabHistoryStructService extends BaseService<CollabHistoryStruct> implements ICollabHistoryStructService {

    @Autowired
    ICollabHistoryStructDao collabHistoryStructDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabHistoryStructDao;
    }

}
