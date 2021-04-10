package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabSubDataDetailDao;
import com.orient.sysmodel.domain.collabdev.datamgrrealtion.CollabSubDataDetail;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabSubDataDetailService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 20:11
 * @Version 1.0
 **/
@Service
public class CollabSubDataDetailService extends BaseService<CollabSubDataDetail> implements ICollabSubDataDetailService {

    @Autowired
    ICollabSubDataDetailDao collabSubDataDetailDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabSubDataDetailDao;
    }
}
