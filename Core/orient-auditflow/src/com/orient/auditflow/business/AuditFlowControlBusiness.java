package com.orient.auditflow.business;

import com.orient.auditflow.config.AuditFlowType;
import com.orient.flow.business.FlowExecutionBusiness;
import com.orient.flow.business.FlowTaskBusiness;
import com.orient.flow.business.ProcessDefinitionBusiness;
import com.orient.sysmodel.domain.flow.AuditFlowOpinionEntity;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.service.flow.IAuditFlowOpinionService;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.CommonResponseData;
import com.orient.web.util.UserContextUtil;
import com.orient.workflow.bean.AssignUser;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.orient.workflow.bean.JBPMInfo.FLOW_DATA_RELATION;

/**
 * the audit flow control business
 *
 * @author Seraph
 *         2016-08-01 上午10:09
 */
@Component
public class AuditFlowControlBusiness extends BaseBusiness {

    /**
     * @param bindDatas           pair's first param is model, second param is dataId
     * @param pdName
     * @param firstTaskUserAssign if set to null, the user will be the one assigned from JPDL
     * @param auditTypeName
     * @return
     */
    public CommonResponseData startAuditFlow(List<FlowDataRelation> bindDatas, String pdName, AssignUser firstTaskUserAssign, String auditTypeName) {
        CommonResponseData retV = new CommonResponseData();

        ProcessDefinition pd = this.processDefinitionBusiness.getLatestPrcDefWithPdKeyOrName(pdName, false);
        List<Activity> startTransitionActivities = this.processDefinitionBusiness.getStartTransitionActivities(pd);
        if (startTransitionActivities.size() > 1) {
            retV.setMsg("开始节点后有多个任务流向");
            return retV;
        }

        Map<String, AssignUser> taskAssignMap = UtilFactory.newHashMap();
        taskAssignMap.put(startTransitionActivities.get(0).getName(), firstTaskUserAssign);

        AuditFlowType auditFlowType = AuditFlowType.fromString(auditTypeName);
        for (FlowDataRelation flowDataRelation : bindDatas) {
            flowDataRelation.setCreateTime(new Date());
            flowDataRelation.setMainType(auditFlowType == null ? auditTypeName : auditFlowType.toString());
        }

        Map<String, Object> flowVariables = UtilFactory.newHashMap();
        flowVariables.put(FLOW_DATA_RELATION, bindDatas);
        ProcessInstance pi = this.flowExecutionBusiness.startPrcInstanceByPrcDefId(pd.getId(), taskAssignMap, null, flowVariables);
        if (pi == null) {
            retV.setMsg("流程启动失败");
            return retV;
        }
        retV.setMsg("启动成功");
        retV.setSuccess(true);
        return retV;
    }

    public CommonResponseData startAuditFlowByPdId(List<FlowDataRelation> bindDatas, String pdId, Map<String, AssignUser> taskAssignMap, String auditTypeName) {
        CommonResponseData retV = new CommonResponseData();

        ProcessDefinition pd = this.processDefinitionBusiness.getProcessDefinitionByPdId(pdId);
        List<Activity> startTransitionActivities = this.processDefinitionBusiness.getStartTransitionActivities(pd);
        if (startTransitionActivities.size() > 1) {
            retV.setMsg("开始节点后有多个任务流向");
            return retV;
        }

        AuditFlowType auditFlowType = AuditFlowType.fromString(auditTypeName);
        for (FlowDataRelation flowDataRelation : bindDatas) {
            flowDataRelation.setCreateTime(new Date());
            flowDataRelation.setMainType(auditFlowType == null ? auditTypeName : auditFlowType.toString());
        }

        Map<String, Object> flowVariables = UtilFactory.newHashMap();
        flowVariables.put(FLOW_DATA_RELATION, bindDatas);
        ProcessInstance pi = this.flowExecutionBusiness.startPrcInstanceByPrcDefId(pd.getId(), taskAssignMap, null, flowVariables);
        if (pi == null) {
            retV.setMsg("流程启动失败");
            return retV;
        }
        retV.setMsg("启动成功");
        retV.setSuccess(true);
        return retV;
    }

    public CommonResponseData commitTask(String flowTaskId, String transitionName, Map<String, AssignUser> nextTasksUserAssign, List<AuditFlowOpinionEntity> opinions) {
        CommonResponseData retV = new CommonResponseData(false, "");
        String currentUserName = UserContextUtil.getUserAllName();
        opinions.forEach(auditFlowOpinionEntity -> {
            auditFlowOpinionEntity.setHandleuser(currentUserName);
            auditFlowOpinionEntity.setHandletime(new Date());
            auditFlowOpinionService.save(auditFlowOpinionEntity);
        });
        //保存审批意见
        this.flowTaskBusiness.completeTaskWithAssign(flowTaskId, transitionName, nextTasksUserAssign);
        retV.setSuccess(true);
        return retV;
    }

    @Autowired
    private IFlowDataRelationService flowDataRelationService;
    @Autowired
    private ProcessDefinitionBusiness processDefinitionBusiness;
    @Autowired
    private FlowExecutionBusiness flowExecutionBusiness;
    @Autowired
    private FlowTaskBusiness flowTaskBusiness;

    @Autowired
    private IAuditFlowOpinionService auditFlowOpinionService;
}
