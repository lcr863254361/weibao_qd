/**
 *
 */
package com.orient.flow.business;

import com.orient.flow.extend.activity.CounterSignActivity;
import com.orient.flow.model.FlowTaskWithAssigner;
import com.orient.flow.util.FlowCommissionHelper;
import com.orient.sysmodel.operationinterface.IUser;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import com.orient.workflow.WorkFlowConstants;
import com.orient.workflow.bean.DeployProperty;
import com.orient.workflow.bean.FlowInfo;
import com.orient.workflow.cmd.PropertiesCommand;
import com.orient.workflow.service.ProcessInformationService;
import org.jbpm.api.*;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskConstants;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.wire.usercode.UserCodeActivityBehaviour;
import org.jbpm.pvm.internal.wire.usercode.UserCodeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 处理流程定义的业务类
 * <p>
 * <p>detailed commentProcessDefinitionBusiness</p>
 *
 * @author [创建人]  mengbin <br/>
 *         [创建时间] 2016-1-12 下午02:45:44 <br/>
 *         [修改人] mengbin <br/>
 *         [修改时间] 2016-1-12 下午02:45:44
 * @see
 */
@Service
public class ProcessDefinitionBusiness extends BaseBusiness {

    @Autowired
    protected ProcessEngine processEngine;

    @Autowired
    ProcessInformationService processInformationService;

    @Autowired
    protected FlowCommissionHelper flowCommissionHelper;


    public ProcessDefinition getProcessDefinitionByPdId(String pdId) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(pdId)
                .notSuspended()
                .orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION).uniqueResult();
        return processDefinition;
    }

    /**
     * 根据执行Id获取流程定义名称.
     * <p>
     * <p>getPdNameByExecutionId</p>
     *
     * @param executionId
     * @return String
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-13 上午08:48:12 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-13 上午08:48:12
     * @see
     */
    public String getPdNameByExecutionId(String executionId) {
        String split = ".";
        int loc = executionId.indexOf(split);
        String pdName = executionId.substring(0, loc);
        return pdName;
    }

    /**
     * 根据流程定义名称和版本号拼接流程PdID
     * <p>
     * <p>getPdIdByPdNameAndVersion</p>
     *
     * @param pdName
     * @param pdVersion
     * @return String
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-13 上午08:48:46 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-13 上午08:48:46
     * @see
     */
    public String getPdIdByPdNameAndVersion(String pdName, int pdVersion) {
        return pdName + "-" + pdVersion;
    }


    /**
     * 获取所有的流程定义，并且以版本号降序
     * <p>
     * <p>getAllPrcessDefinitions</p>
     *
     * @return List<ProcessDefinition>
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-12 下午02:53:13 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-12 下午02:53:13
     * @see
     */
    public List<ProcessDefinition> getAllPrcessDefinitions() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
                .notSuspended()
                .orderDesc(ProcessDefinitionQuery.PROPERTY_NAME).list();
        return processDefinitionList;

    }

    /**
     * 根据流程定义名称，获取最新版本的流程定义
     * <p>
     * <p>getLatestPrcDefWithPdKey</p>
     *
     * @return ProcessDefinition
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-12 下午02:58:12 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-12 下午02:58:12
     * @see
     */
    public ProcessDefinition getLatestPrcDefWithPdKeyOrName(String keyWord, boolean isPdKey) {
        List<ProcessDefinition> processDefinitionList = getAllPrcDefsWithPdKeyOrNameDescByVersion(keyWord, isPdKey);
        ProcessDefinition latestPd = null;
        if (!processDefinitionList.isEmpty()) {
            latestPd = processDefinitionList.get(0);
        }
        return latestPd;
    }

    /**
     * 根据流程定义的关键字获取所有的流程定义，并且以版本号降序
     * <p>
     * <p>getAllPrcDefsWithPdKeyDescByVersion</p>
     *
     * @return List<ProcessDefinition>
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-12 下午02:54:36 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-12 下午02:54:36
     * @see
     */
    public List<ProcessDefinition> getAllPrcDefsWithPdKeyOrNameDescByVersion(String keyWord, boolean isPdKey) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> processDefinitionList;
        if (isPdKey) {
            processDefinitionList = repositoryService.createProcessDefinitionQuery().processDefinitionKey(keyWord).notSuspended()
                    .orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION)
                    .list();
        } else {
            processDefinitionList = repositoryService.createProcessDefinitionQuery().processDefinitionName(keyWord).notSuspended()
                    .orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION)
                    .list();
        }
        return processDefinitionList;
    }


    /**
     * 根据流程定义Id，获取流程定义
     * <p>
     * <p>getProcessDefinitionByDeployId</p>
     *
     * @param deployId
     * @return ProcessDefinition
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-12 下午02:56:10 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-12 下午02:56:10
     * @see
     */
    public ProcessDefinition getProcessDefinitionByDeployId(String deployId) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        return repositoryService.createProcessDefinitionQuery().deploymentId(deployId).uniqueResult();
    }


    /**
     * 根据历史流程实例Id，获取流程定义
     * <p>
     * <p>getProcessDefinitionByHisPiId</p>
     *
     * @param hisPiId
     * @return ProcessDefinition
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-12 下午03:02:11 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-12 下午03:02:11
     * @see
     */
    public ProcessDefinition getProcessDefinitionByHisPiId(String hisPiId) {
        HistoryService historyService = processEngine.getHistoryService();
        HistoryProcessInstance hisPi = historyService.createHistoryProcessInstanceQuery().processInstanceId(hisPiId).uniqueResult();
        return this.getProcessDefinitionByDeployId(hisPi.getProcessDefinitionId());
    }

    /**
     * 获取流程实例的流程定义，该方法支持已完成的流程实例和当前的流程实例
     *
     * @param prcInstId
     * @return
     * @author [创建人]  spf <br/>
     * [创建时间] 2014-6-24 下午4:38:23 <br/>
     * [修改人] spf <br/>
     * [修改时间] 2014-6-24 下午4:38:23
     * @see
     */
    public ProcessDefinition getPrcDefByPrcInstId(String prcInstId) {
        String prcDefId = "";
        ProcessInstance prcInst = processEngine.getExecutionService().createProcessInstanceQuery().processInstanceId(prcInstId).uniqueResult();
        if (prcInst != null) {
            prcDefId = prcInst.getProcessDefinitionId();
            return getProcessDefinitionByPdId(prcDefId);
        } else {
            HistoryProcessInstance hisProInst = processEngine.getHistoryService().createHistoryProcessInstanceQuery().processInstanceId(prcInstId).uniqueResult();
            return getProcessDefinitionByPdId(hisProInst.getProcessDefinitionId());
        }
    }


    /**
     * 获取流程定义的相关属性
     * <p>
     * <p>getProcessDefinitionProperty</p>
     *
     * @param pd
     * @return
     * @author [创建人]  cxk <br/>
     * [创建时间] 2014-6-12 下午04:34:59 <br/>
     * [修改人] cxk <br/>
     * [修改时间] 2014-6-12 下午04:34:59
     * @see
     */
    @SuppressWarnings("unchecked")
    public List<DeployProperty> getProcessDefinitionProperty(ProcessDefinition pd) {
        String deploymentId = pd.getDeploymentId();
        List<DeployProperty> propertys = (List<DeployProperty>) processEngine.execute(new PropertiesCommand(deploymentId));
        return propertys;
    }


    /**
     * g根据流程任务Id，获取后面要执行的流程任务
     * <p>
     * <p>getFlowTaskTransDestinationMap</p>
     *
     * @param flowTaskId
     * @return Map<String,List<String>>
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-12 下午03:33:29 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-12 下午03:33:29
     * @see
     */
    @SuppressWarnings("unchecked")
    public Map<String, List<String>> getFlowTaskTransDestinationMap(String flowTaskId) {
        Map<String, List<String>> transDesMap = UtilFactory.newHashMap();

        Task task = processEngine.getTaskService().getTask(flowTaskId);
        ExecutionService executionService = processEngine.getExecutionService();
        String pdId = executionService.findExecutionById(task.getExecutionId()).getProcessDefinitionId();
        ProcessDefinitionImpl prcDefImpl = (ProcessDefinitionImpl) processEngine.getRepositoryService()
                .createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult();

        List<Activity> allActivity = (List<Activity>) prcDefImpl.getActivities();
        Activity curActivity = null;
        for (Activity act : allActivity) {
            if (act.getName().equals(task.getActivityName())) {
                curActivity = act;
                break;
            }
        }

        List<? extends Transition> outgoingTransitions = curActivity.getOutgoingTransitions();
        if ((outgoingTransitions != null) && (!outgoingTransitions.isEmpty())) {
            for (Transition trans : outgoingTransitions) {
                Activity nextAct = trans.getDestination();
                if (nextAct.getType().equals("fork")) {
                    List<? extends Transition> innerTransitions = nextAct.getOutgoingTransitions();
                    if ((innerTransitions != null) && (!innerTransitions.isEmpty())) {
                        for (Transition innerTrans : innerTransitions) {
                            addTransDestinationMap(transDesMap, innerTrans, trans.getName());
                        }
                    }
                } else if (nextAct.getType().equals("join")) {
                    List<? extends Transition> innerTransitions = nextAct.getOutgoingTransitions();
                    addTransDestinationMap(transDesMap, innerTransitions.get(0), trans.getName());
                } else {
                    addTransDestinationMap(transDesMap, trans, trans.getName());
                }
            }
        }
        return transDesMap;
    }

    /**
     * 将后面要执行的任务添加Map中。
     * <p>
     * <p>addTransDestinationMap</p>
     *
     * @param transDesMap
     * @param trans
     * @param transitionName void
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-12 下午03:35:37 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-12 下午03:35:37
     * @see
     */
    private void addTransDestinationMap(Map<String, List<String>> transDesMap, Transition trans, String transitionName) {
        Activity destAct = trans.getDestination();
        String destActName = destAct.getName();

        if (transDesMap.get(transitionName) == null) {
            List<String> destActNames = UtilFactory.newArrayList();
            destActNames.add(destActName);
            transDesMap.put(transitionName, destActNames);
        } else {
            transDesMap.get(transitionName).add(destActName);
        }
    }


    /**
     * 获取流程start节点后的所有Activity
     * <p>
     * <p>getStartTransitionActivities</p>
     *
     * @param pd
     * @return List<Activity>
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-12 下午03:43:00 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-12 下午03:43:00
     * @see
     */
    public List<Activity> getStartTransitionActivities(ProcessDefinition pd) {

        ProcessDefinitionImpl prcDefImpl = (ProcessDefinitionImpl) pd;

        Activity theStartActivity = prcDefImpl.getInitial();
        List<Activity> theStartTransActivities = UtilFactory.newArrayList();
        List<? extends Transition> outgoingTransitions = theStartActivity.getOutgoingTransitions();
        if ((outgoingTransitions != null) && (!outgoingTransitions.isEmpty())) {
            for (Transition trans : outgoingTransitions) {
                Activity nextAct = trans.getDestination();
                theStartTransActivities.add(nextAct);
            }
        }
        return theStartTransActivities;
    }


    /**
     * 根据流程定义和当前AcitvityName 查找下一个执行的ActivityName
     * <p>
     * <p>getDestNamesByStartNameAndTransitionName</p>
     *
     * @param pdId
     * @param startActivityName
     * @param transName
     * @return String
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-15 上午11:51:55 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-15 上午11:51:55
     * @see
     */
    public String getDestNameByStartNameAndTransitionName(String pdId, String startActivityName, String transName) {

        ProcessDefinitionImpl prcDefImpl = (ProcessDefinitionImpl) processEngine.getRepositoryService()
                .createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult();
        ActivityImpl startActivityImpl = prcDefImpl.findActivity(startActivityName);

        Transition theTran = null;
        Boolean noOutcomeSpecified = TaskConstants.NO_TASK_OUTCOME_SPECIFIED.equals(transName);
        if (noOutcomeSpecified) {
            theTran = startActivityImpl.findOutgoingTransition(null);
        } else {
            theTran = startActivityImpl.findOutgoingTransition(transName);
        }

        if (theTran != null) {
            return theTran.getDestination().getName();
        } else {
            if (noOutcomeSpecified) {
                List<? extends Transition> outgoingTransitions = startActivityImpl.getOutgoingTransitions();
                // Special case: complete(id)
                if (outgoingTransitions.size() == 1) { // If only 1  transition, take  that one
                    theTran = outgoingTransitions.get(0);
                    return theTran.getDestination().getName();
                } else if (outgoingTransitions.size() > 1) { //custom QMS by SPF
                    for (Transition theTrans : outgoingTransitions) {
                        if (theTrans.getDestination().hasOutgoingTransitions()) {
                            return theTrans.getDestination().getName();
                        }
                    }
                    return "";
                } else {
                    return "";
                }
            } else {
                return "";
            }
        }
    }

    /**
     * @param pdId
     * @return 根据流程定义ID 获取其下所有任务的执行人信息
     */
    public List<FlowTaskWithAssigner> getTasksAssignByPdId(String pdId) {
        List<FlowTaskWithAssigner> retVal = new ArrayList<>();
        ProcessDefinitionImpl processDefinition = (ProcessDefinitionImpl) processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult();
        for(Activity activity : processDefinition.getActivities()) {
            String activityType = activity.getType();
            String taskName = activity.getName();
            if (activityType.equals(WorkFlowConstants.ACTIVITY_TYPE_TASK)) {
                TaskDefinitionImpl taskDefImpl = processDefinition.getTaskDefinition(taskName);
                FlowTaskWithAssigner flowTaskWithAssigner = getFlowTaskWithAssigner(taskDefImpl);
                retVal.add(flowTaskWithAssigner);
            } else if (activityType.equals(WorkFlowConstants.ACTIVITY_TYPE_CUSTOM)) {
                ActivityImpl activityImpl = (ActivityImpl) activity;
                UserCodeActivityBehaviour userCodeActivityBehaviour = (UserCodeActivityBehaviour) activityImpl.getActivityBehaviour();
                UserCodeReference userCodeReference = userCodeActivityBehaviour.getCustomActivityReference();
                userCodeReference.setDescriptor(userCodeReference.getDescriptor());
                EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
                EnvironmentImpl env = null;
                try {
                    env = environmentFactory.openEnvironment();
                    Object usedObject = userCodeReference.getObject(activityImpl.getProcessDefinition());
                    CounterSignActivity customActivity = (CounterSignActivity) usedObject;
                    List<String> usernames = CommonTools.arrayToList(customActivity.getCSUsers());
                    FlowTaskWithAssigner flowTaskWithAssigner = new FlowTaskWithAssigner();
                    flowTaskWithAssigner.setTaskName(taskName);
                    final FlowTaskWithAssigner finalRetVal = flowTaskWithAssigner;
                    for(String username : usernames) {
                        IUser iUser = roleEngine.getRoleModel(false).getUserByUserName(username);
                        if(null != iUser){
                            finalRetVal.getTaskAssignerNames().add(username);
                            finalRetVal.getTaskAssignerIds().add(iUser.getId());
                            finalRetVal.getTaskAssignerDisplayNames().add(iUser.getAllName());
                        }
                    }
                    retVal.add(flowTaskWithAssigner);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (env != null)
                        env.close();
                }
            }
        }
        retVal = flowCommissionHelper.reconfigFlowTaskWithAssignerList(pdId, retVal);
        return retVal;
    }

    /**
     * @param taskDefImpl
     * @return 获取普通任务在流程设计器中默认的执行人信息
     */
    private FlowTaskWithAssigner getFlowTaskWithAssigner(TaskDefinitionImpl taskDefImpl) {
        FlowTaskWithAssigner retVal = null;
        if (taskDefImpl.getAssigneeExpression() != null) {
            //单用户
            String assigneeUser = taskDefImpl.getAssigneeExpression().getExpressionString();
            if (!StringUtil.isEmpty(assigneeUser)) {
                IUser iUser = roleEngine.getRoleModel(false).getUserByUserName(assigneeUser);
                if (null != iUser) {
                    retVal = new FlowTaskWithAssigner();
                    retVal.setTaskName(taskDefImpl.getName());
                    retVal.getTaskAssignerNames().add(assigneeUser);
                    retVal.getTaskAssignerIds().add(iUser.getId());
                    retVal.getTaskAssignerDisplayNames().add(iUser.getAllName());
                }
            }
        } else if (taskDefImpl.getCandidateUsersExpression() != null) {
            //用户组
            String candidateUsers = taskDefImpl.getCandidateUsersExpression().getExpressionString();
            if (!StringUtil.isEmpty(candidateUsers)) {
                List<String> assignerIds = CommonTools.arrayToList(candidateUsers.split(","));
                retVal = new FlowTaskWithAssigner();
                retVal.setTaskName(taskDefImpl.getName());
                final FlowTaskWithAssigner finalRetVal = retVal;
                assignerIds.forEach(userId -> {
                    finalRetVal.getTaskAssignerNames().add(roleEngine.getRoleModel(false).getUserByUserName(userId).getUserName());
                    finalRetVal.getTaskAssignerIds().add(userId);
                    finalRetVal.getTaskAssignerDisplayNames().add(roleEngine.getRoleModel(false).getUserByUserName(userId).getAllName());
                });
            }
        } else if (taskDefImpl.getCandidateGroupsExpression() != null) {
            //角色
            String candidateGroups = taskDefImpl.getCandidateGroupsExpression().getExpressionString();
            if (!StringUtil.isEmpty(candidateGroups)) {
                List<String> roleIds = CommonTools.arrayToList(candidateGroups.split(","));
                retVal = new FlowTaskWithAssigner();
                retVal.setTaskName(taskDefImpl.getName());
                final FlowTaskWithAssigner finalRetVal = retVal;
                roleIds.forEach(roleId -> {
                    List<IUser> userList = roleEngine.getRoleModel(false).getRoleById(roleId).getAllUsers();
                    userList.forEach(iUser -> {
                        finalRetVal.getTaskAssignerIds().add(iUser.getId());
                        finalRetVal.getTaskAssignerNames().add(iUser.getUserName());
                        finalRetVal.getTaskAssignerDisplayNames().add(iUser.getAllName());
                    });
                });
            }
        }
        return retVal;
    }

    public List<FlowInfo> getMainAndSubPIs(String piId) {
        return processInformationService.getMainAndSubPIs(piId);
    }
}
