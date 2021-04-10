package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabVersionDetailDao;
import com.orient.sysmodel.domain.collabdev.CollabVersionDetail;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabVersionDetailService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 20:28
 * @Version 1.0
 **/
@Service
public class CollabVersionDetailService extends BaseService<CollabVersionDetail> implements ICollabVersionDetailService {

    @Autowired
    ICollabVersionDetailDao collabVersionDetailDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabVersionDetailDao;
    }
}
