package com.orient.background.business;

import com.orient.background.bean.PIBean;
import com.orient.sysmodel.service.flow.IAuditFlowOpinionService;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.sysmodel.service.flow.ITaskDataRelationService;
import com.orient.web.base.BaseBusiness;
import com.orient.workflow.cmd.DeleteHisProcessInstance;
import com.orient.workflow.service.ProcessInformationService;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.history.HistoryProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/2 0002.
 * 流程定义管理
 */
@Component
public class PIMgrBusiness extends BaseBusiness {

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    IFlowDataRelationService flowDataRelationService;

    @Autowired
    ITaskDataRelationService taskDataRelationService;

    @Autowired
    IAuditFlowOpinionService auditFlowOpinionService;

    @Autowired
    ProcessInformationService processInformationService;

    public List<PIBean> list(String pdId) {
        List<PIBean> retVal = new ArrayList<>();
        ExecutionService executionService = processEngine.getExecutionService();
        List<ProcessInstance> processInstances = executionService.createProcessInstanceQuery().processDefinitionId(pdId).list();
        processInstances.forEach(processInstance -> {
            PIBean piBean = new PIBean();
            piBean.setId(processInstance.getId());
            piBean.setStatus(processInstance.getState());
            retVal.add(piBean);
        });
        //查看历史流程实例信息
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoryProcessInstance> historyProcessInstances = historyService.createHistoryProcessInstanceQuery().processDefinitionId(pdId).list();
        historyProcessInstances.forEach(historyProcessInstance -> {
            if (historyProcessInstance.getEndTime() != null) {
                PIBean piBean = new PIBean();
                piBean.setId(historyProcessInstance.getProcessInstanceId());
                piBean.setStatus(historyProcessInstance.getState());
                retVal.add(piBean);
            }
        });
        return retVal;
    }

    public Boolean delete(String piId) {
        ExecutionService executionService = processEngine.getExecutionService();
        Boolean retVal = false;
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
        if (executionService.findExecutionById(piId) != null) {
            executionService.deleteProcessInstanceCascade(piId);
        } else {
            //已完成
            processEngine.execute(new DeleteHisProcessInstance(piId));
        }
        retVal = true;
        return retVal;
    }
}
