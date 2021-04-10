/**
 *
 */
package com.orient.flow.business;

import com.orient.flow.config.FlowType;
import com.orient.flow.extend.activity.CounterSignActivity;
import com.orient.flow.model.FlowTaskNodeModel;
import com.orient.flow.model.FlowTaskTrackInfo;
import com.orient.flow.util.FlowTypeHelper;
import com.orient.sysmodel.dao.flow.FlowTaskDAO;
import com.orient.sysmodel.domain.role.Role;
import com.orient.sysmodel.operationinterface.IUser;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.BaseDAO;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.workflow.WorkFlowConstants;
import com.orient.workflow.bean.AssignUser;
import com.orient.workflow.bean.JBPMInfo;
import com.orient.workflow.cmd.GetHisSubTask;
import org.jbpm.api.*;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.api.history.HistoryActivityInstanceQuery;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.history.HistoryTaskQuery;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.history.model.HistoryActivityInstanceImpl;
import org.jbpm.pvm.internal.history.model.HistoryTaskImpl;
import org.jbpm.pvm.internal.history.model.HistoryTaskInstanceImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.wire.usercode.UserCodeActivityBehaviour;
import org.jbpm.pvm.internal.wire.usercode.UserCodeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 流程信息业务处理类
 * <p>
 * <p>detailed commentFlowContentBusiness</p>
 *
 * @author [创建人]  mengbin <br/>
 *         [创建时间] 2016-1-14 上午11:33:01 <br/>
 *         [修改人] mengbin <br/>
 *         [修改时间] 2016-1-14 上午11:33:01
 * @see
 */
@Repository
public class FlowDiagramContentBusiness extends BaseBusiness {

    @Autowired
    @Qualifier("processInstanceBusiness")
    private ProcessInstanceBusiness prcInstBusiness;

    @Autowired
    @Qualifier("processDefinitionBusiness")
    private ProcessDefinitionBusiness prcDefBusiness;

    @Autowired
    private FlowTaskDAO flowTaskDAO;

    @Autowired
    private BaseDAO baseDAO;

    @Autowired
    protected ProcessEngine processEngine;


    /**
     * 根据flowTaskId（流程任务ID）获取所有该流程的流程节点的基本状态信息
     * <p>
     * <p>getLatestFlowDiagMonitorModelByFlowTaskId</p>
     *
     * @param flowTaskId
     * @return List<FlowTaskNodeModel>
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-14 下午02:20:05 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-14 下午02:20:05
     * @see
     */
    public List<FlowTaskNodeModel> getLatestFlowDiagMonitorModelByFlowTaskId(String flowTaskId) {

        String piId = this.prcInstBusiness.getPrcInstIdByHisFlowTaskId(flowTaskId);
        return getLatestFlowDiagMonitorModelByPiId(piId);
    }


    /**
     * 根据piId获取所有该流程的流程节点的基本状态信息
     * <p>
     * <p>getLatestFlowDiagMonitorModelByPiId</p>
     *
     * @param piId
     * @return List<FlowTaskNodeModel>
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-14 下午04:54:53 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-14 下午04:54:53
     * @see
     */
    public List<FlowTaskNodeModel> getLatestFlowDiagMonitorModelByPiId(String piId) {
        List<FlowTaskNodeModel> retVal = UtilFactory.newArrayList();

        String pdId = this.prcDefBusiness.getPrcDefByPrcInstId(piId).getId();
        List<String> actAndFinActivityNames = UtilFactory.newArrayList();
        List<FlowTaskNodeModel> activeAndFinishedNodeModelList = this.getLatestActiveAndFinishedActivityNodeModelList(pdId, piId, actAndFinActivityNames);
        List<FlowTaskNodeModel> unStartedActivityList = this.getUnStartedActivitiesNodeModelList(pdId, piId, actAndFinActivityNames);

        retVal.addAll(activeAndFinishedNodeModelList);
        retVal.addAll(unStartedActivityList);
        return retVal;
    }


    /**
     * 根据pdId获取JPDL文件流
     * <p>
     * <p>getFlowJPDLContentAsStream</p>
     *
     * @param pdId
     * @return InputStream
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-14 下午04:56:46 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-14 下午04:56:46
     * @see
     */
    public InputStream getFlowJPDLContentAsStream(String pdId) {

        RepositoryService repositoryService = processEngine
                .getRepositoryService();
        //pdId or pdName
        if (pdId.indexOf(WorkFlowConstants.PROCESS_NAME_VERSION_CONNECTER) == -1) {
            //获取最新流程定义
            List<ProcessDefinition> queryList = repositoryService.createProcessDefinitionQuery().processDefinitionName(pdId).orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION).list();
            if (CommonTools.isEmptyList(queryList)) {
                throw new OrientBaseAjaxException("", "未找到流程定义为【" + pdId + "】的流程信息");
            } else {
                pdId = queryList.get(0).getId();
            }
        }
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery().processDefinitionId(pdId)
                .uniqueResult();
        String deployId = processDefinition.getDeploymentId();
        Set<String> deployResourceNames = repositoryService
                .getResourceNames(deployId);

        String jpdlFileName = "";
        for (String deployItem : deployResourceNames) {
            if (deployItem.endsWith(WorkFlowConstants.JPDLFILE_SUFFIX)) {
                if (FlowTypeHelper.getFlowType(processDefinition.getName()).equals(FlowType.Audit)) {
                    String pdName = deployItem.substring(0, deployItem.indexOf(WorkFlowConstants.JPDLFILE_SUFFIX));
                    if (pdName.indexOf(WorkFlowConstants.PROCESS_MAIN_SUB_CONNECTER) != -1) {
                        pdName = deployItem.substring(deployItem.indexOf(WorkFlowConstants.PROCESS_MAIN_SUB_CONNECTER) + 1, deployItem.indexOf(WorkFlowConstants.JPDLFILE_SUFFIX));
                    }
                    if (pdName.equals(processDefinition.getName())) {
                        jpdlFileName = deployItem;
                        break;
                    }
                } else {
                    jpdlFileName = deployItem;
                    break;
                }
            }
        }

        return repositoryService.getResourceAsStream(deployId, jpdlFileName);
    }


    /**
     * 根据flowTaskID 获取流程定义的JPDL文件流
     * <p>
     * <p>getFlowJPDLContentAsStreamByFlowTaskId</p>
     *
     * @param flowTaskId
     * @return InputStream
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-16 上午11:17:44 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-16 上午11:17:44
     * @see
     */
    public InputStream getFlowJPDLContentAsStreamByFlowTaskId(String flowTaskId) {

        String piId = this.prcInstBusiness.getPrcInstIdByHisFlowTaskId(flowTaskId);
        String pdId = this.prcDefBusiness.getPrcDefByPrcInstId(piId).getId();
        return this.getFlowJPDLContentAsStream(pdId);
    }


    /**
     * 根据piId 获取流程定义的JPDL文件流
     * <p>
     * <p>getFlowJPDLContentAsStreamByPiId</p>
     *
     * @param piId
     * @return InputStream
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-16 上午11:26:44 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-16 上午11:26:44
     * @see
     */
    public InputStream getFlowJPDLContentAsStreamByPiId(String piId) {
        String pdId = this.prcDefBusiness.getPrcDefByPrcInstId(piId).getId();
        return this.getFlowJPDLContentAsStream(pdId);
    }


    /**
     * 根据流程定义和流程实例，获取后期会执行的任务
     * <p>
     * <p>getFlowTrackInfos</p>
     *
     * @param pdId
     * @param piId
     * @return List<FlowTaskTrackInfo>
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-15 上午10:15:40 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-15 上午10:15:40
     * @see
     */
    public List<FlowTaskTrackInfo> getFlowTrackInfos(String pdId, String piId) {
        List<FlowTaskTrackInfo> trackInfos = UtilFactory.newArrayList();
        HistoryService hisSvc = processEngine.getHistoryService();
        HistoryTaskQuery historyTaskQuery = hisSvc.createHistoryTaskQuery();

        List<HistoryActivityInstance> hisActivityInstanceList = hisSvc
                .createHistoryActivityInstanceQuery().processInstanceId(piId)
                .orderAsc(HistoryActivityInstanceQuery.PROPERTY_STARTTIME)
                .list();
        // for(HistoryActivityInstance hisActivity: hisActivityInstanceList){
        for (int index = 0; index < hisActivityInstanceList.size(); index++) {
            HistoryActivityInstance hisActivity = hisActivityInstanceList
                    .get(index);
            HistoryActivityInstanceImpl hisActivityImpl = (HistoryActivityInstanceImpl) hisActivity;
            String activityType = hisActivityImpl.getType();
            if (!WorkFlowConstants.ACTIVITY_TYPE_TASK.equals(activityType)
                    && !WorkFlowConstants.ACTIVITY_TYPE_CUSTOM.equals(activityType)) {
                continue;
            }

            // now fill the return model
            FlowTaskTrackInfo taskTrackInfo = new FlowTaskTrackInfo();

            String activityName = hisActivityImpl.getActivityName();
            taskTrackInfo.setTaskName(activityName);
            taskTrackInfo.setStartTime(hisActivity.getStartTime());
            taskTrackInfo.setEndTimeValue(CommonTools
                    .Obj2String(hisActivityImpl.getEndTime()));
            taskTrackInfo.setIndex(index);

            List<String> transitionNames = hisActivityImpl.getTransitionNames();
            for (String transName : transitionNames) {
                String destName = this.prcDefBusiness.getDestNameByStartNameAndTransitionName(pdId,
                        activityName, transName);
                FlowTaskTrackInfo.TransitionInfo transInfo = new FlowTaskTrackInfo.TransitionInfo();
                transInfo.setDestName(destName);
                transInfo.setTransName(transName);

                if (WorkFlowConstants.ACTIVITY_TYPE_TASK.equals(activityType)) {
                    String actHisDBId = String.valueOf(hisActivityImpl
                            .getDbid());
                    String flowTaskId = flowTaskDAO
                            .getFlowTaskIdByHisActivityDBId(actHisDBId);
                    HistoryTask historyTask = historyTaskQuery.taskId(
                            flowTaskId).uniqueResult();
                    String taskAssignee = historyTask.getAssignee();
                    String realName = baseDAO
                            .getUserAllNameFromUserName(taskAssignee);
                    SimpleDateFormat sdf = new SimpleDateFormat();
                    sdf.applyPattern("yyyy-MM-dd' 'HH:mm");
                    transInfo.setTransInfo("步骤" + index + ":" + realName
                            + "于\r\n" + sdf.format(historyTask.getEndTime())
                            + "提交");
                }
                // destInfo.setTransInfo(transName);
                taskTrackInfo.getTransitionInfos().add(transInfo);
            }
            trackInfos.add(taskTrackInfo);
        }
        return trackInfos;
    }

    /**
     * 获取已完成（最后一次执行）和正在执行的流程任务基本信息
     * <p>
     * <p>getLatestActiveAndFinishedActivityNodeModelList</p>
     *
     * @param pdId
     * @param piId
     * @param outActAndFinActivityNames，该参数是输出参数，输出所有的名称
     * @return List<FlowTaskNodeModel>
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-14 下午02:22:25 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-14 下午02:22:25
     * @see
     */
    public List<FlowTaskNodeModel> getLatestActiveAndFinishedActivityNodeModelList(
            String pdId, String piId, List<String> outActAndFinActivityNames) {

        Map<String, FlowTaskNodeModel> templTaskNameInfoMap = UtilFactory
                .newHashMap();

        HistoryService hisSvc = processEngine.getHistoryService();
        HistoryTaskQuery historyTaskQuery = hisSvc.createHistoryTaskQuery();

        List<HistoryActivityInstance> hisActivityInstanceList = hisSvc
                .createHistoryActivityInstanceQuery().processInstanceId(piId)
                .orderAsc(HistoryActivityInstanceQuery.PROPERTY_STARTTIME).list();
        for (HistoryActivityInstance hisActivity : hisActivityInstanceList) {
            HistoryActivityInstanceImpl hisActivityImpl = (HistoryActivityInstanceImpl) hisActivity;
            String activityType = hisActivityImpl.getType();
            if (!WorkFlowConstants.ACTIVITY_TYPE_TASK.equals(activityType)
                    && !WorkFlowConstants.ACTIVITY_TYPE_CUSTOM.equals(activityType)) {
                continue;
            }

            String activityName = hisActivity.getActivityName();
            // only keep the latest task
            if (templTaskNameInfoMap.containsKey(activityName)) {
                FlowTaskNodeModel tempMdl = templTaskNameInfoMap
                        .get(activityName);
                String tempStartTime = tempMdl.getStartTime();
                if (!"".equals(tempStartTime)) {
                    tempStartTime = tempStartTime.indexOf(".") > 0 ? tempStartTime.substring(0, tempStartTime.indexOf(".")) : tempStartTime;
                }
                if (hisActivity instanceof HistoryTaskInstanceImpl) {
                    HistoryTaskInstanceImpl historyTaskInstance = (HistoryTaskInstanceImpl) hisActivity;
                    if (historyTaskInstance.getType().equals(WorkFlowConstants.ACTIVITY_TYPE_CUSTOM)) {
                        //如果是会签 则只保留父任务
                        if (historyTaskInstance.getHistoryTask().getAssignee() != null) {
                            continue;
                        }
                    }
                } else if (hisActivity.getStartTime().before(CommonTools.str2Date3(tempStartTime))) {
                    continue;
                } else {
                    continue;
                }
            }

            FlowTaskNodeModel nodeMdl = new FlowTaskNodeModel();
            nodeMdl.setName(activityName);
            String startTime = hisActivity.getStartTime().toString();
            if (!"".equals(startTime)) {
                startTime = startTime.indexOf(".") > 0 ? startTime.substring(0, startTime.indexOf(".")) : startTime;
            }
            nodeMdl.setStartTime(startTime);
            if (WorkFlowConstants.ACTIVITY_TYPE_TASK.equals(activityType)) {
                String actHisDBId = String.valueOf(hisActivityImpl.getDbid());
                String flowTaskId = flowTaskDAO
                        .getFlowTaskIdByHisActivityDBId(actHisDBId);
                HistoryTask historyTask = historyTaskQuery.taskId(flowTaskId)
                        .uniqueResult();
                if (historyTask.getState() == null && !hisActivityImpl.getHistoryProcessInstance().getState().equals("ended")) {
                    nodeMdl.setStatus(FlowTaskNodeModel.STATUS_PROCESSING);
                    nodeMdl.setEndTime("");
                } else {
                    nodeMdl.setStatus(FlowTaskNodeModel.STATUS_COMPLETED);
                    String endTime = hisActivity.getEndTime() == null ? "" : hisActivity.getEndTime().toString();
                    if (!"".equals(endTime)) {
                        endTime = endTime.indexOf(".") > 0 ? endTime.substring(0, endTime.indexOf(".")) : endTime;
                    }
                    nodeMdl.setEndTime(endTime);
                }

                String taskAssignee = null;
                //当前正在进行中的任务从历史任务中无法获取执行人，只能从当前任务中获取
                if(FlowTaskNodeModel.STATUS_PROCESSING.equals(nodeMdl.getStatus())) {
                    TaskService taskService = processEngine.getTaskService();
                    Task task = taskService.createTaskQuery().executionId(historyTask.getExecutionId()).uniqueResult();
                    taskAssignee = task.getAssignee();
                }
                else {
                    taskAssignee = historyTask.getAssignee();
                }
                if (taskAssignee == null) {
                    StringBuilder assigneeName = new StringBuilder();
                    StringBuilder assigneeRealName = new StringBuilder();
                    List<Participation> participations = processEngine.getTaskService().getTaskParticipations(flowTaskId);
                    for (Participation participation : participations) {
                        if (StringUtil.isEmpty(participation.getUserId())) {
                            //工具中指定的为角色
                            String roleName = participation.getGroupId();
                            Role role = roleEngine.getRoleModel(false).getRoleById(roleName);
                            if (null != role) {
                                List<String> userIds = role.getAllUsers().stream().map(iUser -> iUser.getId()).collect(Collectors.toList());
                                List<String> showNames = role.getAllUsers().stream().map(iUser -> iUser.getAllName()).collect(Collectors.toList());
                                if (!CommonTools.isEmptyList(userIds)) {
                                    assigneeName.append(CommonTools.list2String(userIds)).append(",");
                                    assigneeRealName.append(CommonTools.list2String(showNames)).append(",");
                                }
                            }
                        } else {
                            assigneeName.append(participation.getUserId()).append(",");
                            assigneeRealName.append(baseDAO.getUserAllNameFromUserName(participation.getUserId())).append(",");
                        }
                    }
                    taskAssignee = assigneeName.length() > 0 ? assigneeName.deleteCharAt(assigneeName.length() - 1).toString() : "";
                    nodeMdl.setAssRealName(assigneeRealName.length() > 0 ? assigneeRealName.deleteCharAt(assigneeRealName.length() - 1).toString() : "");

                } else {
                    String realName = baseDAO.getUserAllNameFromUserName(taskAssignee);
                    nodeMdl.setAssRealName(realName);
                }
                nodeMdl.setAssignee(taskAssignee);
                nodeMdl.setTaskId(flowTaskId);
            } else if (WorkFlowConstants.ACTIVITY_TYPE_CUSTOM.equals(activityType)) {
                String actHisDBId = String.valueOf(hisActivityImpl.getDbid());
                String flowTaskId = flowTaskDAO
                        .getFlowTaskIdByHisActivityDBId(actHisDBId);
                HistoryTaskImpl historyTask = (HistoryTaskImpl) historyTaskQuery.taskId(flowTaskId)
                        .uniqueResult();
                if (historyTask.getState() == null && !hisActivityImpl.getHistoryProcessInstance().getState().equals("ended")) {
                    nodeMdl.setStatus(FlowTaskNodeModel.STATUS_PROCESSING);
                    nodeMdl.setEndTime("");
                } else {
                    nodeMdl.setStatus(FlowTaskNodeModel.STATUS_COMPLETED);
                    String endTime = hisActivity.getEndTime() == null ? "" : hisActivity.getEndTime().toString();
                    if (!"".equals(endTime)) {
                        endTime = endTime.indexOf(".") > 0 ? endTime.substring(0, endTime.indexOf(".")) : endTime;
                    }
                    nodeMdl.setEndTime(endTime);
                }
                //执行人从历史变量中获取
                List<String> taskAssignee = new ArrayList<>();
                List<String> realAssignee = new ArrayList<>();
                List<HistoryTaskImpl> subTasks = processEngine.execute(new GetHisSubTask(historyTask.getId()));
                subTasks.forEach(subHisTask -> {
                    String assign = subHisTask.getAssignee();
                    if (!StringUtil.isEmpty(assign)) {
                        taskAssignee.add(assign);
                        realAssignee.add(baseDAO.getUserAllNameFromUserName(assign));
                    }
                });
                nodeMdl.setAssignee(CommonTools.list2String(taskAssignee));
                nodeMdl.setAssRealName(CommonTools.list2String(realAssignee));
                nodeMdl.setTaskId(flowTaskId);
            }
            templTaskNameInfoMap.put(activityName, nodeMdl);
        }

        outActAndFinActivityNames.addAll(templTaskNameInfoMap.keySet());
        List<FlowTaskNodeModel> flowDiagNodeList = UtilFactory.newArrayList();
        for (String taskName : templTaskNameInfoMap.keySet()) {
            flowDiagNodeList.add(templTaskNameInfoMap.get(taskName));
        }
        return flowDiagNodeList;
    }


    /**
     * 获取还未开始的TaskActivie
     * <p>
     * <p>getUnStartedActivitiesNodeModelList</p>
     *
     * @param pdId
     * @param piId
     * @param actAndFinActivityNames ：正在执行和完成的Activity
     * @return List<FlowTaskNodeModel>
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-14 下午04:49:27 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-14 下午04:49:27
     * @see
     */
    public List<FlowTaskNodeModel> getUnStartedActivitiesNodeModelList(
            String pdId, String piId, List<String> actAndFinActivityNames) {

        ExecutionService executionService = processEngine.getExecutionService();

        ProcessDefinitionImpl prcDefImpl = (ProcessDefinitionImpl) processEngine
                .getRepositoryService().createProcessDefinitionQuery()
                .processDefinitionId(pdId).uniqueResult();
        ExecutionImpl processInstance = (ExecutionImpl) this.processEngine.getExecutionService()
                .createProcessInstanceQuery().processInstanceId(piId).uniqueResult();
        Map<String, AssignUser> taskAssignUser = UtilFactory.newHashMap();
        if (processInstance != null) {
            taskAssignUser = (Map<String, AssignUser>) executionService.getVariable(piId, JBPMInfo.DynamicUserAssign);
        }

        List<FlowTaskNodeModel> unStartedList = UtilFactory.newArrayList();
        List<ActivityImpl> allActivity = (List<ActivityImpl>) prcDefImpl.getActivities();
        // find the activity not begin yet
        for (ActivityImpl activity : allActivity) {
            String activityNameUn = activity.getName();
            if (!actAndFinActivityNames.contains(activityNameUn)
                    && (activity.getType()
                    .equals(WorkFlowConstants.ACTIVITY_TYPE_TASK) || activity.getType()
                    .equals(WorkFlowConstants.ACTIVITY_TYPE_CUSTOM))) {
                FlowTaskNodeModel nodeMdl = new FlowTaskNodeModel();

                nodeMdl.setStatus(FlowTaskNodeModel.STATUS_UNSTARTED);
                nodeMdl.setName(activityNameUn);
                nodeMdl.setStartTime("");
                nodeMdl.setEndTime("");

                //assignee rule:
                // 1) if the user were set, it will stay in flow variable
                // 2) if none exist, for collab flow, get from node

                if (taskAssignUser != null && !taskAssignUser.isEmpty()) {
                    AssignUser assignUser = taskAssignUser.get(activity.getName());
                    if (null == assignUser) {
                        nodeMdl.setAssignee("");
                        nodeMdl.setAssRealName("");
                    } else {
                        StringBuilder assigneeName = new StringBuilder();
                        StringBuilder assigneeRealName = new StringBuilder();

                        if (!CommonTools.isNullString(assignUser.getCandidateUsers()) && assignUser.getCandidateUsers().length() > 0) {
                            for (String participation : assignUser.getCandidateUsers().split(AssignUser.DELIMITER)) {
                                assigneeName.append(participation).append(",");
                                assigneeRealName.append(baseDAO.getUserAllNameFromUserName(participation)).append(",");
                            }
                            nodeMdl.setAssignee(assigneeName.length() > 0 ? assigneeName.deleteCharAt(assigneeName.length() - 1).toString() : "");
                            nodeMdl.setAssRealName(assigneeRealName.length() > 0 ? assigneeRealName.deleteCharAt(assigneeRealName.length() - 1).toString() : "");
                        } else {
                            nodeMdl.setAssignee(assignUser.getCurrentUser());
                            nodeMdl.setAssRealName(baseDAO.getUserAllNameFromUserName(assignUser.getCurrentUser()));
                        }
                    }
                } else {
                    if(activity.getType().equals(WorkFlowConstants.ACTIVITY_TYPE_CUSTOM)){
                        UserCodeActivityBehaviour userCodeActivityBehaviour = (UserCodeActivityBehaviour) activity.getActivityBehaviour();
                        UserCodeReference userCodeReference = userCodeActivityBehaviour.getCustomActivityReference();
                        userCodeReference.setDescriptor(userCodeReference.getDescriptor());
                        EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
                        EnvironmentImpl env = null;
                        try {
                            env = environmentFactory.openEnvironment();
                            Object usedObject = userCodeReference.getObject(activity.getProcessDefinition());
                            CounterSignActivity customActivity = (CounterSignActivity) usedObject;
                            String[] usernames = customActivity.getCSUsers();
                            nodeMdl.setAssignee(CommonTools.array2String(usernames));
                            nodeMdl.setAssRealName(nodeMdl.getAssignee());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            System.out.println("Exception test ===========" + ex);
                        } finally {
                            if (env != null)
                                env.close();
                        }
                    }else{
                        TaskDefinitionImpl tdi = prcDefImpl.getTaskDefinition(activityNameUn);
                        String defaultUserNames = "", defaultRoleName = "";
                        if (null != tdi) {
                            //获取默认流程 节点 用户定义
                            if (tdi.getAssigneeExpression() != null) {
                                defaultUserNames = tdi.getAssigneeExpression().getExpressionString();
                            } else if (tdi.getCandidateUsersExpression() != null) {
                                defaultUserNames = tdi.getCandidateUsersExpression().getExpressionString();
                            } else if (tdi.getCandidateGroupsExpression() != null) {
                                defaultRoleName = tdi.getCandidateGroupsExpression().getExpressionString();
                            }
                            //用户转换，单用户、用户组、角色
                            if (!CommonTools.isNullString(defaultRoleName)) {
                                //角色支持
                                String userName = "";
                                List<IUser> userList = roleEngine.getRoleModel(false).getRoleById(defaultRoleName).getAllUsers();
                                for (IUser user : userList) {
                                    userName += "," + user.getUserName();
                                }
                                if (!"".equals(userName)) {
                                    userName = userName.substring(1);
                                    defaultUserNames = userName;
                                }
                            }
                        }
                        nodeMdl.setAssignee(defaultUserNames);
                        nodeMdl.setAssRealName(baseDAO.getUserAllNameFromUserName(defaultUserNames));
                    }


                }
                unStartedList.add(nodeMdl);
            }
        }
        return unStartedList;
    }
}
