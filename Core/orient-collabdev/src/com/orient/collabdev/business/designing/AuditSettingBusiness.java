package com.orient.collabdev.business.designing;

import com.orient.collabdev.constant.ApprovalTriggerTypeEnum;
import com.orient.collabdev.constant.ApprovalTypeEnum;
import com.orient.sysmodel.domain.collabdev.approval.CollabSettingsApproval;
import com.orient.sysmodel.domain.collabdev.approval.CollabSettingsApprovalDetail;
import com.orient.sysmodel.service.collabdev.ICollabSettingsApprovalDetailService;
import com.orient.sysmodel.service.collabdev.ICollabSettingsApprovalService;
import com.orient.utils.CommonTools;
import com.orient.web.base.BaseBusiness;
import com.orient.workflow.tools.WorkflowCommonTools;
import org.hibernate.criterion.Restrictions;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-07 10:44 AM
 */
@Component
public class AuditSettingBusiness extends BaseBusiness {


    public List<CollabSettingsApproval> getCollabSettingsApprovalByNode(String nodeId) {
        return collabSettingsApprovalService.list(Restrictions.eq("settingsTarget", nodeId),
                Restrictions.eq("approvalType", ApprovalTypeEnum.NODEAPPROVAL.toString()));
    }

    public List<Map<String, String>> listAuditPds(String nodeId, List<String> pdIds) {
        List<Map<String, String>> retVal = new ArrayList<>();
        //已经选中的流程定义信息
        List<CollabSettingsApproval> collabSettingsApprovals = collabSettingsApprovalService.list(Restrictions.eq("settingsTarget", nodeId)
                , Restrictions.eq("approvalType", ApprovalTypeEnum.NODEAPPROVAL.toString()));
        List<String> existIds = new ArrayList<>();
        collabSettingsApprovals.forEach(collabSettingsApproval -> existIds.add(collabSettingsApproval.getPdName() + "-" + collabSettingsApproval.getPdVersion()));
        pdIds.forEach(pdId -> {
            if (!existIds.contains(pdId)) {
                retVal.add(WorkflowCommonTools.splitNameAndVersion(pdId));
            }
        });
        return retVal;
    }

    public void createMulti(List<CollabSettingsApproval> collabSettingsApprovals) {
        if (!CommonTools.isEmptyList(collabSettingsApprovals)) {
            collabSettingsApprovals.forEach(collabSettingsApproval -> {
                collabSettingsApproval.setApprovalType(ApprovalTypeEnum.NODEAPPROVAL.toString());
                collabSettingsApproval.setTriggerType(ApprovalTriggerTypeEnum.SUBMIT.toString());
                collabSettingsApprovalService.save(collabSettingsApproval);
            });
        }
    }

    public void deleteBind(String[] toDelIds) {
        List<CollabSettingsApproval> collabSettingsApprovals = collabSettingsApprovalService.getByIds(toDelIds);
        //delete cascade
        collabSettingsApprovals.forEach(collabSettingsApproval -> collabSettingsApprovalService.delete(collabSettingsApproval));
    }

    public void update(CollabSettingsApproval collabSettingsApproval) {
        String id = collabSettingsApproval.getId();
        CollabSettingsApproval dbPO = collabSettingsApprovalService.getById(id);
        dbPO.setTriggerType(collabSettingsApproval.getTriggerType());
        collabSettingsApprovalService.update(dbPO);
    }

    public List<CollabSettingsApprovalDetail> listAuditDetail(String belongAuditBind) {
        List<CollabSettingsApprovalDetail> details = collabSettingsApprovalDetailService.list(Restrictions.eq("belongSetting.id", belongAuditBind));
        if (CommonTools.isEmptyList(details)) {
            //init
            CollabSettingsApproval collabSettingsApproval = collabSettingsApprovalService.getById(belongAuditBind);
            String pdId = collabSettingsApproval.getPdName() + "-" + collabSettingsApproval.getPdVersion();
            //获取任务集合
            ProcessDefinition pd = processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult();
            if (null != pd) {
                List<Activity> taskDefinitions = WorkflowCommonTools.getTaskActivities(pd);
                taskDefinitions.forEach(activity -> {
                    CollabSettingsApprovalDetail collabSettingsApprovalDetail = new CollabSettingsApprovalDetail();
                    collabSettingsApprovalDetail.setBelongSetting(collabSettingsApproval);
                    collabSettingsApprovalDetail.setCanAssignOther(1l);
                    collabSettingsApprovalDetail.setTaskName(activity.getName());
                    collabSettingsApprovalDetailService.save(collabSettingsApprovalDetail);
                    details.add(collabSettingsApprovalDetail);
                });
            }
        }
        return details;
    }

    public void updateAuditDetail(CollabSettingsApprovalDetail detail) {
        String id = detail.getId();
        CollabSettingsApprovalDetail dbPO = collabSettingsApprovalDetailService.getById(id);
        dbPO.setFormId(detail.getFormId());
        dbPO.setCanAssignOther(detail.getCanAssignOther());
        dbPO.setCustomPath(detail.getCustomPath());
        collabSettingsApprovalDetailService.update(dbPO);
    }

    @Autowired
    ICollabSettingsApprovalService collabSettingsApprovalService;

    @Autowired
    ICollabSettingsApprovalDetailService collabSettingsApprovalDetailService;

    @Autowired
    ProcessEngine processEngine;


}
