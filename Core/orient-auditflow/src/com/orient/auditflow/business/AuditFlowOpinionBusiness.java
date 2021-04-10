package com.orient.auditflow.business;

import com.orient.sysmodel.domain.flow.AuditFlowOpinionEntity;
import com.orient.sysmodel.service.flow.IAuditFlowOpinionService;
import com.orient.web.base.BaseHibernateBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class AuditFlowOpinionBusiness extends BaseHibernateBusiness<AuditFlowOpinionEntity> {

    @Autowired
    IAuditFlowOpinionService auditFlowOpinionService;

    @Override
    public IAuditFlowOpinionService getBaseService() {
        return auditFlowOpinionService;
    }
}
