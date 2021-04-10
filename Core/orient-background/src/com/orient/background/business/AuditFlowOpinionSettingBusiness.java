package com.orient.background.business;

import com.orient.sysmodel.domain.flow.AuditFlowOpinionSettingEntity;
import com.orient.sysmodel.service.flow.IAuditFlowOpinionSettingService;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.workflow.tools.WorkflowCommonTools;
import org.hibernate.criterion.Criterion;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 审批流程意见设置
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class AuditFlowOpinionSettingBusiness extends BaseHibernateBusiness<AuditFlowOpinionSettingEntity> {

    @Autowired
    IAuditFlowOpinionSettingService auditFlowOpinionSettingService;

    @Autowired
    ProcessEngine processEngine;

    @Override
    public IAuditFlowOpinionSettingService getBaseService() {
        return auditFlowOpinionSettingService;
    }

    @Override
    public ExtGridData<AuditFlowOpinionSettingEntity> list(Integer page, Integer limit, AuditFlowOpinionSettingEntity filter,Criterion... criterions) {

        ExtGridData<AuditFlowOpinionSettingEntity> queryList = super.list(page, limit, filter);
        if (queryList.getTotalProperty() == 0) {
            //初始化任务信息
            String pdId = filter.getPdId();
            //获取任务集合
            ProcessDefinition pd = processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult();
            if (null != pd) {
                List<Activity> taskDefinitions = WorkflowCommonTools.getTaskActivities(pd);
                taskDefinitions.forEach(activity -> {
                    AuditFlowOpinionSettingEntity auditFlowOpinionSettingEntity = new AuditFlowOpinionSettingEntity();
                    auditFlowOpinionSettingEntity.setTaskName(activity.getName());
                    auditFlowOpinionSettingEntity.setPdId(pdId);
                    auditFlowOpinionSettingService.save(auditFlowOpinionSettingEntity);
                });
            }
            queryList = super.list(page, limit, filter);
        }
        return queryList;
    }
}
