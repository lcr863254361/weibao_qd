package com.orient.sysmodel.service.collab.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collab.ICollabDataFlowDao;
import com.orient.sysmodel.domain.collab.CollabDataFlow;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collab.ICollabDataFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mengbin
 * @create 2016-08-22 下午2:00
 */
@Service
public class CollabDataFlowService extends BaseService<CollabDataFlow> implements ICollabDataFlowService {

    @Autowired
    ICollabDataFlowDao collabDataFlowDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabDataFlowDao;
    }
}
