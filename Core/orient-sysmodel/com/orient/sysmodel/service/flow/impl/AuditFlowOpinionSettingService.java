package com.orient.sysmodel.service.flow.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.flow.IAuditFlowOpinionSettingDao;
import com.orient.sysmodel.domain.flow.AuditFlowOpinionSettingEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.flow.IAuditFlowOpinionSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 审批流程意见设置
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class AuditFlowOpinionSettingService extends BaseService<AuditFlowOpinionSettingEntity> implements IAuditFlowOpinionSettingService {

    @Autowired
    IAuditFlowOpinionSettingDao auditFlowOpinionSettingDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.auditFlowOpinionSettingDao;
    }
}
