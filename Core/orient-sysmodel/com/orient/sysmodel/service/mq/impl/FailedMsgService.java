package com.orient.sysmodel.service.mq.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.mq.IFailedMsgDao;
import com.orient.sysmodel.dao.mq.impl.FailedMsgDao;
import com.orient.sysmodel.domain.mq.FailedMsg;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.mq.IFailedMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FailedMsgService
 *
 * @author Seraph
 *         2016-12-01 下午2:20
 */
@Service
public class FailedMsgService extends BaseService<FailedMsg> implements IFailedMsgService {

    @Autowired
    private FailedMsgDao failedMsgDao;


    @Override
    public IBaseDao getBaseDao() {
        return this.failedMsgDao;
    }
}
