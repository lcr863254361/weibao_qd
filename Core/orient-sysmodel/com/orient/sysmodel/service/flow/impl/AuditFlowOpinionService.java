package com.orient.sysmodel.service.flow.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.flow.IAuditFlowOpinionDao;
import com.orient.sysmodel.domain.flow.AuditFlowOpinionEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.flow.IAuditFlowOpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class AuditFlowOpinionService extends BaseService<AuditFlowOpinionEntity> implements IAuditFlowOpinionService {

    @Autowired
    IAuditFlowOpinionDao auditFlowOpinionDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.auditFlowOpinionDao;
    }
}
