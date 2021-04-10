package com.orient.sysmodel.service.flow.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.flow.IAuditFlowModelBindDao;
import com.orient.sysmodel.domain.flow.AuditFlowModelBindEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.flow.IAuditFlowModelBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 审批流程与模型绑定设置
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class AuditFlowModelBindService extends BaseService<AuditFlowModelBindEntity> implements IAuditFlowModelBindService {

    @Autowired
    IAuditFlowModelBindDao auditFlowModelBindDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.auditFlowModelBindDao;
    }
}
