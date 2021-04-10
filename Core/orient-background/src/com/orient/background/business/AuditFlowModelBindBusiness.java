package com.orient.background.business;

import com.orient.sysmodel.domain.flow.AuditFlowModelBindEntity;
import com.orient.sysmodel.service.flow.IAuditFlowModelBindService;
import com.orient.web.base.BaseHibernateBusiness;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 审批流程与模型绑定设置
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class AuditFlowModelBindBusiness extends BaseHibernateBusiness<AuditFlowModelBindEntity> {

    @Autowired
    IAuditFlowModelBindService auditFlowModelBindService;

    @Override
    public IAuditFlowModelBindService getBaseService() {
        return auditFlowModelBindService;
    }

    public List<Map<String, String>> listAuditPds(String modelId, List<String> pdIds) {
        List<Map<String, String>> retVal = new ArrayList<>();
        //已经选中的流程定义信息
        List<AuditFlowModelBindEntity> auditFlowModelBindEntities = auditFlowModelBindService.list(Restrictions.eq("modelId", modelId));
        List<String> existIds = new ArrayList<>();
        auditFlowModelBindEntities.forEach(auditFlowModelBindEntity -> {
            existIds.add(auditFlowModelBindEntity.getFlowName() + "-" + auditFlowModelBindEntity.getFlowVersion());
        });
        pdIds.forEach(pdId -> {
            if (!existIds.contains(pdId)) {
                Map<String, String> dataMap = new HashMap<>();
                dataMap.put("flowName", pdId.substring(0, pdId.lastIndexOf("-")));
                dataMap.put("flowVersion", pdId.substring(pdId.lastIndexOf("-") + 1, pdId.length()));
                retVal.add(dataMap);
            }
        });
        return retVal;
    }

    public List<AuditFlowModelBindEntity> getModelBindPds(String modelId) {
        //已经选中的流程定义信息
        List<AuditFlowModelBindEntity> auditFlowModelBindEntities = auditFlowModelBindService.list(Restrictions.eq("modelId", modelId));
        return auditFlowModelBindEntities;
    }
}
