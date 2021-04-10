package com.orient.background.business;

import com.orient.background.bean.AuditFlowTaskSettingEntityWrapper;
import com.orient.sysmodel.domain.flow.AuditFlowModelBindEntity;
import com.orient.sysmodel.domain.flow.AuditFlowTaskSettingEntity;
import com.orient.sysmodel.domain.form.ModelFormViewEntity;
import com.orient.sysmodel.service.flow.IAuditFlowModelBindService;
import com.orient.sysmodel.service.flow.IAuditFlowTaskSettingService;
import com.orient.sysmodel.service.form.IModelFormViewService;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.workflow.tools.WorkflowCommonTools;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 审批流程任务设置
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class AuditFlowTaskSettingBusiness extends BaseHibernateBusiness<AuditFlowTaskSettingEntity> {

    @Autowired
    IAuditFlowTaskSettingService auditFlowTaskSettingService;

    @Autowired
    IAuditFlowModelBindService auditFlowModelBindService;

    @Autowired
    IModelFormViewService modelFormViewService;

    @Autowired
    ProcessEngine processEngine;

    @Override
    public IAuditFlowTaskSettingService getBaseService() {
        return auditFlowTaskSettingService;
    }

    public ExtGridData<AuditFlowTaskSettingEntityWrapper> listSpecial(Integer page, Integer limit, AuditFlowTaskSettingEntity filter) {
        //如果为空 则需要初始化
        ExtGridData<AuditFlowTaskSettingEntity> queryList = super.list(page, limit, filter);
        if (queryList.getTotalProperty() == 0) {
            Long belongAuditBindId = filter.getBelongAuditBind();
            if (null != belongAuditBindId) {
                AuditFlowModelBindEntity auditFlowModelBindEntity = auditFlowModelBindService.getById(belongAuditBindId);
                String pdId = auditFlowModelBindEntity.getFlowName() + "-" + auditFlowModelBindEntity.getFlowVersion();
                //获取任务集合
                ProcessDefinition pd = processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult();
                if (null != pd) {
                    List<Activity> taskDefinitions = WorkflowCommonTools.getTaskActivities(pd);
                    taskDefinitions.forEach(activity -> {
                        AuditFlowTaskSettingEntity auditFlowTaskSettingEntity = new AuditFlowTaskSettingEntity();
                        auditFlowTaskSettingEntity.setBelongAuditBind(belongAuditBindId);
                        auditFlowTaskSettingEntity.setCanAssignOther(1l);
                        auditFlowTaskSettingEntity.setTaskName(activity.getName());
                        auditFlowTaskSettingService.save(auditFlowTaskSettingEntity);
                    });
                }
            }
            queryList = super.list(page, limit, filter);
        }
        ExtGridData<AuditFlowTaskSettingEntityWrapper> retVal = new ExtGridData<>();
        retVal.setTotalProperty(queryList.getTotalProperty());
        retVal.setResults(dataChange(queryList.getResults()));
        return retVal;
    }

    private List<AuditFlowTaskSettingEntityWrapper> dataChange(List<AuditFlowTaskSettingEntity> results) {
        List<AuditFlowTaskSettingEntityWrapper> retVal = new ArrayList<>();
        List<Long> auditFlowModelBindIds = results.stream().filter(auditFlowTaskSettingEntity -> {
            return auditFlowTaskSettingEntity.getFormId() != null;
        }).map(AuditFlowTaskSettingEntity::getFormId).collect(Collectors.toList());
        Long[] ids = new Long[auditFlowModelBindIds.size()];
        auditFlowModelBindIds.toArray(ids);
        List<ModelFormViewEntity> modelFormViewEntities = modelFormViewService.getByIds(ids);
        results.forEach(auditFlowTaskSettingEntity -> {
            AuditFlowTaskSettingEntityWrapper auditFlowTaskSettingEntityWrapper = new AuditFlowTaskSettingEntityWrapper();
            BeanUtils.copyProperties(auditFlowTaskSettingEntityWrapper, auditFlowTaskSettingEntity);
            auditFlowTaskSettingEntityWrapper.setFormId_display(null == auditFlowTaskSettingEntityWrapper.getFormId() ? "" :
                    modelFormViewEntities.stream().filter(
                            modelFormViewEntity -> modelFormViewEntity.getId().equals(auditFlowTaskSettingEntityWrapper.getFormId())).findFirst().get().getName());
            retVal.add(auditFlowTaskSettingEntityWrapper);
        });
        return retVal;
    }

    public AuditFlowTaskSettingEntity getByBindIdAndTaskName(Long auditFlowBindId, String taskName) {
        AuditFlowTaskSettingEntity example = new AuditFlowTaskSettingEntity();
        example.setBelongAuditBind(auditFlowBindId);
        example.setTaskName(taskName);
        List<AuditFlowTaskSettingEntity> auditFlowTaskSettingEntities = auditFlowTaskSettingService.listBeansByExample(example);
        return CommonTools.isEmptyList(auditFlowTaskSettingEntities) ? null : auditFlowTaskSettingEntities.get(0);
    }
}
