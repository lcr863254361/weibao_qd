package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabHistoryNodeDao;
import com.orient.sysmodel.domain.collabdev.CollabHistoryNode;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabHistoryNodeService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 16:46
 * @Version 1.0
 **/
@Service
public class CollabHistoryNodeService extends BaseService<CollabHistoryNode> implements ICollabHistoryNodeService {

    @Autowired
    ICollabHistoryNodeDao collabHistoryNodeDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabHistoryNodeDao;
    }

}
