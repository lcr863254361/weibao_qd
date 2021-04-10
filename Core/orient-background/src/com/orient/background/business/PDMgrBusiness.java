package com.orient.background.business;

import com.orient.background.bean.PDBean;
import com.orient.sysmodel.service.flow.*;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseBusiness;
import com.orient.workflow.WorkFlowConstants;
import com.orient.workflow.service.ProcessInformationService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/2 0002.
 * 流程定义管理
 */
@Component
public class PDMgrBusiness extends BaseBusiness {

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    IFlowDataRelationService flowDataRelationService;

    @Autowired
    ITaskDataRelationService taskDataRelationService;

    @Autowired
    IAuditFlowModelBindService auditFlowModelBindService;

    @Autowired
    IAuditFlowTaskSettingService auditFlowTaskSettingService;

    @Autowired
    IAuditFlowOpinionSettingService auditFlowOpinionSettingService;

    @Autowired
    IAuditFlowOpinionService auditFlowOpinionService;

    @Autowired
    ProcessInformationService processInformationService;


    public List<PDBean> list(List<String> auditFlowPdIds, List<String> collabFlowPdIds, PDBean filter) {

        String type = filter.getType();
        String name = filter.getName();
        List<String> distinctPdIds = new ArrayList<>();
        if ("collab".equals(type)) {
            distinctPdIds = StringUtil.isEmpty(name) ? getDistinctPdIds(collabFlowPdIds) : collabFlowPdIds;
        } else {
            distinctPdIds = StringUtil.isEmpty(name) ? getDistinctPdIds(auditFlowPdIds) : auditFlowPdIds;
        }
        //转化为Bean对象
        List<PDBean> retVal = constructFromPdId(distinctPdIds, type, name);
        return retVal;
    }

    /**
     * @param distinctPdIds
     * @return 从流程定义ID 转化为 流程定义前端对象
     */
    private List<PDBean> constructFromPdId(List<String> distinctPdIds, String type, String name) {

        List<PDBean> retVal = new ArrayList<>();
        distinctPdIds.forEach(pdId -> {
            String pdName = pdId.substring(0, pdId.indexOf(WorkFlowConstants.PROCESS_NAME_VERSION_CONNECTER));
            String pdVersion = pdId.substring(pdId.indexOf(WorkFlowConstants.PROCESS_NAME_VERSION_CONNECTER) + 1, pdId.length());
            if (StringUtil.isEmpty(name) || (!StringUtil.isEmpty(name) && name.equals(pdName))) {
                PDBean pdBean = new PDBean();
                pdBean.setId(pdId);
                pdBean.setName(pdName);
                pdBean.setVersion(pdVersion);
                pdBean.setType(type);
                retVal.add(pdBean);
            }
        });
        return retVal;
    }

    /**
     * @param pdIds
     * @return 合并流程版本 留取最高版本信息
     */
    private List<String> getDistinctPdIds(List<String> pdIds) {
        List<String> retVal = new ArrayList<>();
        Map<String, Integer> pdMap = new LinkedHashMap<>();
        pdIds.forEach(pdId -> {
            String pdName = pdId.substring(0, pdId.indexOf(WorkFlowConstants.PROCESS_NAME_VERSION_CONNECTER));
            Integer version = Integer.valueOf(pdId.substring(pdId.indexOf(WorkFlowConstants.PROCESS_NAME_VERSION_CONNECTER) + 1, pdId.length()));
            Boolean put = pdMap.containsKey(pdName) && version < pdMap.get(pdName) ? false : true;
            if (put) {
                pdMap.put(pdName, version);
            }
        });
        pdMap.forEach((pdName, pdVersion) -> {
            String pdId = pdName + WorkFlowConstants.PROCESS_NAME_VERSION_CONNECTER + pdVersion;
            retVal.add(pdId);
        });
        return retVal;
    }

    /**
     * @param pdBean
     * @return 删除流程定义 及其关联信息
     */
    public Boolean delete(PDBean pdBean) {
        HistoryService historyService = processEngine.getHistoryService();
        //删除流程实例绑定的额外的信息
        historyService.createHistoryProcessInstanceQuery().processDefinitionId(pdBean.getId()).list().forEach(historyProcessInstance -> {
            deleteFlowInstanceRelatedData(historyProcessInstance.getProcessInstanceId());
        });
        //删除流程流程定义
        RepositoryService repositoryService = processEngine.getRepositoryService();
        String deploymentId = repositoryService.createProcessDefinitionQuery().processDefinitionId(pdBean.getId()).uniqueResult().getDeploymentId();
        repositoryService.deleteDeploymentCascade(deploymentId);
        //删除流程绑定
        Criterion criterion = Restrictions.and(Restrictions.eq("flowName", pdBean.getName()), Restrictions.eq("flowVersion", pdBean.getVersion()));
        auditFlowModelBindService.list(criterion).forEach(auditFlowModelBindEntity -> {
            //未做自动关联 故需要手动删除
            auditFlowTaskSettingService.list(Restrictions.eq("belongAuditBind", auditFlowModelBindEntity.getId())).forEach(auditFlowTaskSettingEntity -> {
                auditFlowTaskSettingService.delete(auditFlowTaskSettingEntity);
            });
            auditFlowModelBindService.delete(auditFlowModelBindEntity);
        });
        //删除流程意见设置信息
        auditFlowOpinionSettingService.list(Restrictions.eq("pdId", pdBean.getId())).forEach(auditFlowOpinionSettingEntity -> {
            auditFlowOpinionSettingService.delete(auditFlowOpinionSettingEntity);
        });
        return true;
    }

    private void deleteFlowInstanceRelatedData(String piId) {
        processInformationService.getMainAndSubPIs(piId).forEach(flowInfo -> {
            Criterion criterion = Restrictions.eq("piId", flowInfo.getId());
            flowDataRelationService.list(criterion).forEach(flowDataRelation -> {
                flowDataRelationService.delete(flowDataRelation);
            });
            taskDataRelationService.list(criterion).forEach(taskDataRelation -> {
                taskDataRelationService.delete(taskDataRelation);
            });
            //删除流程意见信息
            auditFlowOpinionService.list(Restrictions.eq("flowid", flowInfo.getId())).forEach(auditFlowOpinionEntity -> {
                auditFlowOpinionService.delete(auditFlowOpinionEntity);
            });
        });
    }
}
