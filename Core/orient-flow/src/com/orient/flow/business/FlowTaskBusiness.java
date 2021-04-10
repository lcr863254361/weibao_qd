package com.orient.flow.business;

import com.orient.flow.config.FlowType;
import com.orient.flow.util.FlowCommissionHelper;
import com.orient.flow.util.FlowTypeHelper;
import com.orient.sysmodel.dao.flow.FlowTaskDAO;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.CommonResponseData;
import com.orient.web.util.UserContextUtil;
import com.orient.workflow.bean.AssignUser;
import com.orient.workflow.bean.JBPMInfo;
import com.orient.workflow.cmd.TakeTaskCmd;
import com.orient.workflow.cmd.TaskDelegateCmd;
import org.jbpm.api.*;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.history.HistoryTaskQuery;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 处理流程任务的基础类
 * <p>
 * <p>detailed commentFlowTaskBusiness</p>
 *
 * @author [创建人]  mengbin <br/>
 *         [创建时间] 2016-1-12 上午10:32:53 <br/>
 *         [修改人] mengbin <br/>
 *         [修改时间] 2016-1-12 上午10:32:53
 * @see
 */
@Service
public class FlowTaskBusiness extends BaseBusiness {


    @Autowired
    protected ProcessEngine processEngine;

    @Autowired
    protected FlowTaskDAO flowTaskDao;

    @Autowired
    private ProcessDefinitionBusiness processDefinitionBusiness;

    @Autowired
    protected FlowCommissionHelper flowCommissionHelper;


    /**
     * 完成流程任务
     * <p>
     * <p>completeTaskWithAssign</p>
     *
     * @param taskId
     * @param outCome
     * @param userName void
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-12 上午10:36:59 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-12 上午10:36:59
     * @see
     */
    public void completeTask(String taskId, String outCome, String userName) {
        TaskService taskService = processEngine.getTaskService();
        userName = StringUtil.isEmpty(userName) ? UserContextUtil.getUserName() : userName;
        String hisAssign = processEngine.getHistoryService().createHistoryTaskQuery().taskId(taskId).uniqueResult().getAssignee();
        if (StringUtil.isEmpty(hisAssign) && !StringUtil.isEmpty(userName)) {
            processEngine.execute(new TaskDelegateCmd(taskId, userName));
        }
        taskService.completeTask(taskId, outCome);
    }

    public void completeTaskWithAssign(String taskId, String outCome, Map<String, AssignUser> nextTasksAssign) {
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.getTask(taskId);

        ExecutionService executionService = processEngine.getExecutionService();

        Map<String, AssignUser> assignUserMap = (Map<String, AssignUser>) executionService.getVariable(task.getExecutionId(), JBPMInfo.DynamicUserAssign);
        if (assignUserMap == null) {
            Map<String, Object> variable = UtilFactory.newHashMap();
            variable.put(JBPMInfo.DynamicUserAssign, nextTasksAssign);
            executionService.setVariables(task.getExecutionId(), variable);
        } else if (null != nextTasksAssign) {
            assignUserMap.putAll(nextTasksAssign);
        }
        String userName = StringUtil.isEmpty(task.getAssignee()) ? UserContextUtil.getUserName() : task.getAssignee();
        String hisAssign = processEngine.getHistoryService().createHistoryTaskQuery().taskId(taskId).uniqueResult().getAssignee();
        if (StringUtil.isEmpty(hisAssign) && !StringUtil.isEmpty(userName)) {
            processEngine.execute(new TaskDelegateCmd(taskId, userName));
        }
        taskService.completeTask(taskId, outCome);
    }

    /**
     * 获取根据任务名称和当前的流程实例Id，获取任务。
     * <p>
     * <p>getCurTaskByName</p>
     *
     * @param piId
     * @param taskName
     * @return Task
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-12 上午10:38:38 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-12 上午10:38:38
     * @see
     */
    public Task getCurTaskByName(String piId, String taskName) {
        TaskService taskService = processEngine.getTaskService();
        List<Task> currentTasks = taskService.createTaskQuery().processInstanceId(piId).list();

        Task theTask = null;
        for (Task task : currentTasks) {
            if (task.getName().equals(taskName)) {
                theTask = task;
                break;
            }
        }
        return theTask;
    }


    public List<Task> getCurrentTaskListOfType(String userName, FlowType flowType, String activityName) {
        List<Task> retV = UtilFactory.newArrayList();

        List<Task> tasks = this.getCurrentTaskList(userName, activityName);
        for (Task task : tasks) {
            TaskImpl taskImpl = (TaskImpl) task;
            String pdName = this.processDefinitionBusiness.getPdNameByExecutionId(taskImpl.getExecution().getProcessInstance().getId());
            boolean isTheFlowType = FlowTypeHelper.getFlowType(pdName) == flowType;
            if (!isTheFlowType) {
                continue;
            }

            retV.add(task);
        }

        tasks.sort((o1, o2) -> {
            if (o1.getCreateTime().before(o2.getCreateTime())) {
                return 1;
            } else {
                return -1;
            }
        });
        return retV;
    }

    /**
     * 根据用户过滤当前正在执行的流程任务，包括指定给个人和组的任务。
     * <p>
     * <p>getCurrentTaskList</p>
     *
     * @param userName
     * @return List<Task>
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-12 上午10:50:28 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-12 上午10:50:28
     * @see
     */
    public List<Task> getCurrentTaskList(String userName, String activityName) {
        TaskService taskService = processEngine.getTaskService();

        TaskQuery taskQuery = taskService.createTaskQuery().assignee(userName).notSuspended()
                .orderDesc("execution");
        if (!CommonTools.isNullString(activityName)) {
            taskQuery.activityName(activityName);
        }

        List<Task> taskList = taskQuery.list();

        taskQuery = taskService.createTaskQuery().candidate(userName).notSuspended()
                .orderDesc("execution");
        if (!CommonTools.isNullString(activityName)) {
            taskQuery.activityName(activityName);
        }

        List<Task> candidateTasks = taskQuery.list();

        taskList.addAll(candidateTasks);
        return taskList;
    }

    /**
     * 根据流程实例Id，获取任务列表
     * <p>
     * <p>getCurrentTaskByPiId</p>
     *
     * @param piId
     * @return List<Task>
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-12 上午10:54:34 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-12 上午10:54:34
     * @see
     */
    public List<Task> getCurrentTaskByPiId(String piId) {
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(piId).list();
        return taskList;
    }


    /**
     * 根据历史任务id查找历史活动名称
     * <p>
     * <p>getHistoryActivityName</p>
     *
     * @param taskId
     * @return String
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-12 上午11:02:54 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-12 上午11:02:54
     * @see
     */
    public String getHistoryActivityName(String taskId) {
        return flowTaskDao.getFlowTaskNameByHisTaskId(taskId);
    }

    public String getHistoryActivityId(String taskId) {
        return flowTaskDao.getFlowTaskIdByHisTaskId(taskId);
    }


    /**
     * 根据用户名获取所有的历史任务，根据完成时间排序。
     * <p>
     * <p>getHistoryTaskList</p>
     *
     * @param userName
     * @return List<HistoryTask>
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-12 上午11:23:09 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-12 上午11:23:09
     * @see
     */
    public List<HistoryTask> getHistoryTaskList(String userName, int maxQueryResult) {
        //历史服务
        HistoryService historyService = processEngine.getHistoryService();
        //获取历史任务信息
        List<HistoryTask> historyTasks = historyService.createHistoryTaskQuery().assignee(userName).page(0, maxQueryResult)
                .orderDesc(HistoryTaskQuery.PROPERTY_ENDTIME).state(HistoryTask.STATE_COMPLETED).list();

        return historyTasks;
    }


    /**
     * 获取用户历史任务数量
     *
     * @param userName
     * @return
     */
    public long getHistoryTaskCount(String userName) {
        HistoryService historyService = processEngine.getHistoryService();
        return historyService.createHistoryTaskQuery().assignee(userName).count();
    }

    /**
     * 获取该时间段内所有的历史流程任务
     * <p>
     * <p>getHistoryTaskByAssignee_Time</p>
     *
     * @param assignee
     * @param startDate
     * @param endDate
     * @return List<HistoryTask>
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-18 下午04:33:19 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-18 下午04:33:19
     * @see
     */
    public List<HistoryTask> getHistoryTaskByAssignee_Time(String assignee, Date startDate, Date endDate) {
        //历史服务
        HistoryService historyService = processEngine.getHistoryService();

        List<HistoryTask> historyTasks;
        //获取历史任务信息
        if (startDate != null && endDate != null) {
            historyTasks = historyService.createHistoryTaskQuery().assignee(assignee).startedAfter(startDate).startedBefore(endDate).state(HistoryTask.STATE_COMPLETED)
                    .orderDesc(HistoryTaskQuery.PROPERTY_ENDTIME).list();

        } else if (startDate != null) {
            historyTasks = historyService.createHistoryTaskQuery().assignee(assignee).startedAfter(startDate).state(HistoryTask.STATE_COMPLETED)
                    .orderDesc(HistoryTaskQuery.PROPERTY_ENDTIME).list();
        } else if (endDate != null) {
            historyTasks = historyService.createHistoryTaskQuery().assignee(assignee).startedBefore(endDate).state(HistoryTask.STATE_COMPLETED)
                    .orderDesc(HistoryTaskQuery.PROPERTY_ENDTIME).list();
        } else {
            historyTasks = historyService.createHistoryTaskQuery().assignee(assignee).state(HistoryTask.STATE_COMPLETED)
                    .orderDesc(HistoryTaskQuery.PROPERTY_ENDTIME).list();
        }

        if (endDate != null) {
            return historyTasks.stream().filter(historyTask -> {
                if (historyTask.getEndTime() == null) {
                    return true;
                } else {
                    return !historyTask.getEndTime().after(endDate);
                }
            }).collect(Collectors.toList());
        } else {
            return historyTasks;
        }
    }

    /**
     * 根据TaskId获取TASK
     * <p>
     * <p>getCurTaskById</p>
     *
     * @param taskId
     * @return
     * @author [创建人]  mengbin <br/>
     * [创建时间] 2016-1-12 上午11:33:33 <br/>
     * [修改人] mengbin <br/>
     * [修改时间] 2016-1-12 上午11:33:33
     * @see
     */
    public Task getCurTaskById(String taskId) {
        TaskService taskService = processEngine.getTaskService();
        return taskService.getTask(taskId);
    }

    public HistoryTask getHistoryTaskById(String taskId) {
        HistoryService historyService = processEngine.getHistoryService();
        return historyService.createHistoryTaskQuery().taskId(taskId).uniqueResult();
    }

    public CommonResponseData taskTask(String flowTaskId, String userAllName) {
        CommonResponseData retV = new CommonResponseData();

        Task task = this.getCurTaskById(flowTaskId);
        TaskImpl taskImpl = (TaskImpl) task;
        if (!StringUtil.isEmpty(taskImpl.getAssignee())) {
            retV.setSuccess(false);
            retV.setMsg("任务已被领取!");
        } else {
            processEngine.execute(new TakeTaskCmd(flowTaskId, UserContextUtil.getUserName()));
            retV.setMsg("领取成功!");
        }
        return retV;
    }

    public CommonResponseData setAssignee(String piId, String taskName, String assignee) {

        CommonResponseData retV = new CommonResponseData();
        ExecutionService executionService = processEngine.getExecutionService();
        ProcessInstance pi = executionService.findProcessInstanceById(piId);
        if (pi == null) {
            retV.setSuccess(false);
            retV.setMsg("流程未启动或已结束");
            return retV;
        }

        HistoryActivityInstance hisActivity = processEngine.getHistoryService()
                .createHistoryActivityInstanceQuery().processInstanceId(piId).activityName(taskName).uniqueResult();
        if (hisActivity != null) {
            retV.setSuccess(false);
            retV.setMsg("该任务正在处理或已结束");
            return retV;
        }

        Map<String, AssignUser> toAssignUserMap = UtilFactory.newHashMap();
        AssignUser assignUser = new AssignUser();
        if (assignee.contains(AssignUser.DELIMITER)) {
            assignUser.setCandidateUsers(assignee);
        } else {
            assignUser.setCurrentUser(assignee);
        }
        toAssignUserMap.put(taskName, assignUser);

        Map<String, AssignUser> assignUserMap = (Map<String, AssignUser>) executionService.getVariable(piId, JBPMInfo.DynamicUserAssign);
        Map<String, Map<String, AssignUser>> variable = UtilFactory.newHashMap();
        if (assignUserMap == null) {
            assignUserMap = new HashMap<>();
            executionService.setVariables(piId, assignUserMap);
        }
        assignUserMap.putAll(toAssignUserMap);
        variable.put(JBPMInfo.DynamicUserAssign, assignUserMap);
        executionService.setVariables(piId, variable);
        retV.setSuccess(true);
        return retV;
    }
}
