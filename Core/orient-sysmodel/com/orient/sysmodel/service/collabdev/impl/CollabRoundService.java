package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabRoundDao;
import com.orient.sysmodel.domain.collabdev.CollabRound;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabRoundService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 17:01
 * @Version 1.0
 **/
@Service
public class CollabRoundService extends BaseService<CollabRound> implements ICollabRoundService {

    @Autowired
    ICollabRoundDao collabRoundDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabRoundDao;
    }

}
