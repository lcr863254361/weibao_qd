package com.orient.sysmodel.service.mq.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.mq.impl.MsgDao;
import com.orient.sysmodel.domain.mq.CwmMsg;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.mq.IMsgService;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FailedMsgService
 */
@Service
public class MsgService extends BaseService<CwmMsg> implements IMsgService {

    @Autowired
    private MsgDao msgDao;


    @Override
    public IBaseDao getBaseDao() {
        return this.msgDao;
    }

    public int getMsgCntByUserId(Long userId, Boolean readed) {
        int cnt = count(Restrictions.eq("userId", userId), Restrictions.eq("readed", readed));
        return cnt;
    }
}
