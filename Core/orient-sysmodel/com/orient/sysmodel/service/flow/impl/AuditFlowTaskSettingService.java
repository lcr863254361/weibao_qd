package com.orient.sysmodel.service.flow.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.flow.IAuditFlowTaskSettingDao;
import com.orient.sysmodel.domain.flow.AuditFlowTaskSettingEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.flow.IAuditFlowTaskSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 审批流程任务设置
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class AuditFlowTaskSettingService extends BaseService<AuditFlowTaskSettingEntity> implements IAuditFlowTaskSettingService {

    @Autowired
    IAuditFlowTaskSettingDao auditFlowTaskSettingDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.auditFlowTaskSettingDao;
    }
}
