package com.orient.flow.business;

import com.orient.flow.model.NextFlowNodeInfo;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import org.hibernate.criterion.Restrictions;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * the flow information business
 *
 * @author Seraph
 *         2016-08-04 下午2:19
 */
@Component
public class FlowInfoBusiness extends BaseBusiness{

    public List<FlowDataRelation> getFlowBindDatas(String piId) {
        //转化为主流程
        List<FlowDataRelation> flowDataRelations = this.flowDataRelationService.list(Restrictions.eq(FlowDataRelation.PI_ID, piId));
        return flowDataRelations;
    }

    public List<NextFlowNodeInfo> getNextFlowNodesInfo(String flowTaskId) {
        List<NextFlowNodeInfo> nextFlowNodes = UtilFactory.newArrayList();

        Task task = processEngine.getTaskService().getTask(flowTaskId);
        ExecutionService executionService = processEngine.getExecutionService();
        String pdId = executionService.findExecutionById(task.getExecutionId()).getProcessDefinitionId();
        ProcessDefinitionImpl prcDefImpl = (ProcessDefinitionImpl)processEngine.getRepositoryService()
                .createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult();

        List<Activity>  allActivity = (List<Activity>) prcDefImpl.getActivities();
        Activity curActivity = null;
        for(Activity act : allActivity){
            if(act.getName().equals(task.getActivityName())){
                curActivity = act;
                break;
            }
        }

        List<? extends Transition> outgoingTransitions = curActivity.getOutgoingTransitions();
        if ( (outgoingTransitions!=null) && (!outgoingTransitions.isEmpty()) ) {
            for(Transition trans : outgoingTransitions){
                Activity nextAct = trans.getDestination();

                NextFlowNodeInfo nextFlowNodeInfo = new NextFlowNodeInfo(nextAct.getName(), trans.getName(), nextAct.getType());
                recurTransition(nextFlowNodeInfo, nextAct);

                nextFlowNodes.add(nextFlowNodeInfo);
            }
        }
        return nextFlowNodes;
    }

    public void recurTransition(NextFlowNodeInfo curNode, Activity activity){
        if(activity.getType().equals("fork") || activity.getType().equals("join")){
            List<? extends Transition> innerTransitions = activity.getOutgoingTransitions();
            if ( (innerTransitions!=null) && (!innerTransitions.isEmpty()) ) {
                List<NextFlowNodeInfo> nextFlowNodeInfos = UtilFactory.newArrayList();
                curNode.setNextFlowNodes(nextFlowNodeInfos);
                for(Transition innerTrans : innerTransitions){
                    Activity destActivity = innerTrans.getDestination();
                    NextFlowNodeInfo flowNodeInfo = new NextFlowNodeInfo(destActivity.getName(), destActivity.getName(), destActivity.getType());
                    nextFlowNodeInfos.add(flowNodeInfo);
                    recurTransition(flowNodeInfo, destActivity);
                }
            }
        }else{
            return;
        }

    }

    @Autowired
    private IFlowDataRelationService flowDataRelationService;
    @Autowired
    protected ProcessEngine processEngine;
}
