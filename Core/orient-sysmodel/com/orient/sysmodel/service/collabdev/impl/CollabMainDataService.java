package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabMainDataDao;
import com.orient.sysmodel.domain.collabdev.datamgrrealtion.CollabMainData;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabMainDataService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 16:51
 * @Version 1.0
 **/
@Service
public class CollabMainDataService extends BaseService<CollabMainData> implements ICollabMainDataService {

    @Autowired
    ICollabMainDataDao collabMainDataDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabMainDataDao;
    }

}
