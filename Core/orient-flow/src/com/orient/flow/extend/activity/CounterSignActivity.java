
package com.orient.flow.extend.activity;

import com.orient.flow.extend.extensions.FlowTaskExecutionEventListener;
import com.orient.flow.extend.extensions.FlowTaskExecutionEventListenerMng;
import com.orient.sysmodel.domain.workflow.JbpmCounterSignInfo;
import com.orient.sysmodel.domain.workflow.JbpmCounterSignInfoId;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.workflow.bean.AssignUser;
import com.orient.workflow.bean.JBPMInfo;
import com.orient.workflow.ext.mail.EdmNotificationEvent;
import com.orient.workflow.service.CounterSignService;
import com.orient.workflow.tools.WorkflowCommonTools;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskService;
import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.activity.ExternalActivityBehaviour;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.history.HistoryEvent;
import org.jbpm.pvm.internal.history.events.TaskActivityStart;
import org.jbpm.pvm.internal.history.model.HistoryTaskImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.jbpm.pvm.internal.util.Clock;

import java.util.*;


/**
 * @author zhulc@cssrc.com.cn
 * @ClassName CounterSignActivity
 * 会签任务
 * @date 2012-6-5
 */

public class CounterSignActivity implements ExternalActivityBehaviour {

    private static final long serialVersionUID = 461927487724802881L;
    public static final String COUNTERSIGN_INFO = "COUNTERSIGN_INFO";
    public static final String STRATEGY = "strategy";
    public static final String STRATEGY_PASS = "pass";
    public static final String STRATEGY_NOPASS = "nopass";

    // 会签人员
    private String counterSignUsers;
    /*
     * 50%:半数通过 100%:全部通过 1、2、3:数字，代表最小通过数(用户数)
     */
    private String strategy;
    // 会签流向
    private String passTransiton;

    private String noPassTransiton;
    /**
     * Task的名字，可以不设置，不设置时将采用Activity的名字
     */
    private String taskName;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 实现execute方法 (non-Javadoc)
     * 动态创建会签Task
     *
     * @see org.jbpm.api.activity.ActivityBehaviour#execute(org.jbpm.api.activity.ActivityExecution)
     */
    public void execute(ActivityExecution activityExecution) throws Exception {
        ExecutionImpl execution = (ExecutionImpl) activityExecution;
        if (StringUtils.isEmpty(this.taskName)) {
            this.setTaskName(execution.getActivityName());
        }
        String settedUsers = getsettedUsers(execution);
        if (StringUtils.isNotEmpty(counterSignUsers) || !StringUtil.isEmpty(settedUsers)) {

            String[] users = StringUtil.isEmpty(settedUsers) ? this.getCSUsers() : settedUsers.split(",");
            DbSession dbsession = EnvironmentImpl.getFromCurrent(DbSession.class);
            // 创建一个会签的任务.
            TaskImpl superTask = this.createCounterSignSuperTask(dbsession, execution);
            // 为每一个会签人员创建一个任务
            for (String user : users) {
                // 创建子任务,并将会签人员绑定在一起
                this.createCounterSignSubTask(dbsession, execution, superTask, user);
            }
            // 设置流程变量[会签相关信息]
            execution.setVariable(COUNTERSIGN_INFO, this.initCounterSignInfo(users, execution, superTask));
        }
        // 流程等待
        execution.waitForSignal();
    }

    private String getsettedUsers(ExecutionImpl execution) {
        ExecutionImpl mainExecution = WorkflowCommonTools.getMainLineExecution(execution);
        //從流程定義中獲取會簽設置人員信息
        ExecutionService executionService = EnvironmentImpl.getFromCurrent(ExecutionService.class);
        //向上寻找主流程 防止分支
        Map<String, AssignUser> taskAssignUser = (Map<String, AssignUser>) executionService.getVariable(mainExecution.getId(), JBPMInfo.DynamicUserAssign);
        String retVal = "";
        if (null != taskAssignUser) {
            AssignUser assignuser = taskAssignUser.get(this.taskName);
            if (null == assignuser) {

            } else {
                if (!CommonTools.isNullString(assignuser.getCurrentUser())) {
                    retVal = assignuser.getCurrentUser();
                }
                if (!CommonTools.isNullString(assignuser.getCurrentGroup())) {
                    retVal = assignuser.getCurrentGroup();
                }
                if (!CommonTools.isNullString(assignuser.getCandidateUsers())) {
                    retVal = assignuser.getCandidateUsers();
                }
                if (!CommonTools.isNullString(assignuser.getCandidateGroups())) {
                    retVal = assignuser.getCandidateGroups();
                }
            }
        }
        return retVal;
    }

    /**
     * 实现signal方法 (non-Javadoc)
     *
     * @see org.jbpm.api.activity.ExternalActivityBehaviour#signal(org.jbpm.api.activity.ActivityExecution,
     * java.lang.String, java.util.Map)
     */
    public void signal(ActivityExecution activityExecution, String signalName, Map<String, ?> parms) throws Exception {
        ExecutionImpl execution = (ExecutionImpl) activityExecution;
        // 得到会签策略
        CounterSignInfo info = (CounterSignInfo) execution.getVariable(COUNTERSIGN_INFO);
        // 如果该Execution变量被设置成true，该节点被视为自动结束(这在会签时为了解决通过之后还有Execution和Task没有结束的情况下用)
        if (info.isAutoCompleteTask()) {
            // 结束本流程(自task绑定的execution)
            execution.end("counter_sign_sub_execution_end");
            return;
        }
        TaskService taskService = EnvironmentImpl.getFromCurrent(TaskService.class);
        // 查看目前还有多少任务未完成
        ProcessInstance pi = (ProcessInstance) execution.getProcessInstance();
        List<Task> superTask = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
        // 会签策略逻辑计算
        String result = this.getComputeStrategy(info);
        //
        if (!"".equals(result)) {
            ExecutionImpl superExecution = execution.getParent();
            Activity activity = superExecution.getActivity();
            Transition transition = null;
            if ((signalName == null)
                    || ((Task.STATE_COMPLETED.equals(signalName)) && (activity.getOutgoingTransitions() != null) && (activity
                    .getOutgoingTransitions().size() == 1))) {
                // 外出流向是唯一的一个
                transition = activity.getOutgoingTransitions().get(0);
            } else {
                // 外出流向是多个的时候[决定走向的地方，可以根据会签策略来决定走向]
                if ("pass".equals(result)) {
                    transition = activity.findOutgoingTransition(passTransiton);
                } else {
                    transition = activity.findOutgoingTransition(noPassTransiton);
                }
            }

            TaskImpl parTask = (TaskImpl) superTask.get(0);
            // 会签通过之后，需要将没有结束的Execution和Task全部结束
            this.doEndExecutionAndTask(superExecution, parTask, taskService);
            // 结束本流程(自task绑定的execution)
            execution.end("counter_sign_sub_execution_end");

            List<FlowTaskExecutionEventListener> flowTaskExecutionEventListeners =  FlowTaskExecutionEventListenerMng.getInstance()
                    .getListenersForTask(execution.getProcessInstance().getId(), execution.getProcessDefinition().getName(), FlowTaskExecutionEventListener.COUNTER_SIGN);
            TaskDefinitionImpl taskDefinition = new TaskDefinitionImpl();
            taskDefinition.setName(parTask.getName());
            for(FlowTaskExecutionEventListener listener : flowTaskExecutionEventListeners){
                listener.left(execution.getProcessInstance(), parTask.getName(), taskDefinition, signalName);
            }
            // 结束父task
            taskService.completeTask(superTask.get(0).getId(), transition.getName());
            // 将父流程的流向转移
            superExecution.take(transition);
        } else {
            // 还有会签人员没有做任务，流程继续等待
            execution.waitForSignal();
            // 结束本流程(自task绑定的execution)
            execution.end("counter_sign_sub_execution_end");
        }
    }

    /**
     * 得到会签人员
     *
     * @return String[]
     */
    public String[] getCSUsers() {
        String[] group = counterSignUsers.split(";;;");
        Set<String> list = new HashSet<>();
        // 0::::jbpm4;;;1::::aaa,080099,jbpm1,jbpm2,user;;;2::::工厂用户,高级用户
        for (String g : group) {
            String sign = g.substring(0, 1);
            if ("0".equals(sign)) {
                // 单用户
                list.add(g.substring(5));
            } else if ("1".equals(sign)) {
                // 用户组
                String[] gTmp = g.substring(5).split(",");
                for (String s : gTmp) {
                    list.add(s);
                }
            } else if ("2".equals(sign)) {
                // 角色
                String notUsers = list.toString();
                notUsers = notUsers.substring(1);
                notUsers = notUsers.substring(0, notUsers.length() - 1);
                CounterSignService counterSignService = EnvironmentImpl.getFromCurrent(CounterSignService.class);
                List<String> userList = counterSignService.getRole2Users(g.substring(5), notUsers);
                for (String username : userList) {
                    list.add(username);
                }
            }
        }
        String[] users = new String[list.size()];
        return list.toArray(users);
    }

    /**
     * 初始化会签策略统计
     *
     * @param users
     * @return String
     */
    private CounterSignInfo initCounterSignInfo(String[] users, ExecutionImpl execution, TaskImpl task) {
        CounterSignInfo info = new CounterSignInfo();
        // 会签总人数
        info.setUserCount(users.length);
        // 会签设定的策略
        info.setStrategy(strategy);
        /*
         * 50%:半数通过 100%:全部通过 1、2、3.... :数字，代表最小通过数(用户数)
		 */
        String result = strategy;
        if ("50%".equals(strategy)) {
            result = String.valueOf(Math.round(users.length / 2f));
        } else if ("100%".equals(strategy)) {
            result = String.valueOf(users.length);
        } else {
            if (Integer.parseInt(strategy) > users.length) {
                result = String.valueOf(users.length);
            } else {
                result = strategy;
            }
        }
        // 根据会签设定的策略和会签总人数计算出的策略结果
        info.setStrategyValue(result);

        //   插入扩展表
        CounterSignService counterSignService = EnvironmentImpl.getFromCurrent(CounterSignService.class);
        JbpmCounterSignInfoId counterID = new JbpmCounterSignInfoId();
        counterID.setExecutionId(execution.getId());
        counterID.setTaskname(task.getActivityName());
        counterID.setUsercount(String.valueOf(users.length));
        counterID.setStrategy(strategy);
        counterID.setStrategyValue(result);
        JbpmCounterSignInfo signInfo = new JbpmCounterSignInfo(counterID);
        counterSignService.addCounterSign(signInfo);
        return info;
    }


    private String getComputeStrategy(CounterSignInfo info) {
        String result = "";
        // 会签总人数
        int userCount = info.getUserCount();
        // 最小通过数(用户数)
        int strategyValue = Integer.parseInt(info.getStrategyValue());
        int users = 0;
        int pass = 0;
        // 统计用户同意、否决数
        List<Map<String, String>> list = info.getCounterSignList();
        for (Map<String, String> map : list) {
            // 累计:同意、否决、弃权数
            if ("pass".equalsIgnoreCase(map.get(STRATEGY))) {
                pass++;
            }
            users++;
        }
        if (pass >= strategyValue) {
            // 总的同意数 >= 最小通过数(用户数)
            result = "pass";
        } else {
            if (userCount == users) {
                // 会签总人数 == 参数会签人数[会签人员全部参与，但是同意总数仍然小于最小通过数(用户数)]
                result = "nopass";
            } else {
                // 未参加人总数
                int syUsers = userCount - users;
                if ((pass + syUsers) < strategyValue) {
                    // 已参加会签人员的同意数 + 未参加会签的人员数(假如都同意) < 最小通过数(用户数)，此种情况则视为否决大于通过
                    result = "nopass";
                } else {
                    //
                    result = "";
                }
            }
        }
        return result;
    }


    private TaskImpl createCounterSignSuperTask(DbSession dbsession, ExecutionImpl execution) {
        TaskImpl task = (TaskImpl) dbsession.createTask();
        task.setName(this.getTaskName());
        task.setSignalling(false);
        task.setExecution(execution);
        task.setProcessInstance((ExecutionImpl) execution.getProcessInstance());
        TaskDefinitionImpl taskDefinition = new TaskDefinitionImpl();
        taskDefinition.setName(task.getName());
        task.setTaskDefinition(taskDefinition);
        dbsession.save(task);

        // 触发事件
        HistoryEvent.fire(new TaskActivityStart(task), execution);
        return task;
    }


    private void createCounterSignSubTask(DbSession dbsession, ExecutionImpl execution, TaskImpl superTask, String user) {
        TaskImpl subTask = (TaskImpl) dbsession.createTask();
        subTask.setName(superTask.getName());

        // 会签人
        subTask.setAssignee(user);

        // 设置为false，自己手工处理execution的跳转。 设置为true，completeTaskWithAssign 会触发 signal，
        subTask.setSignalling(true);

        // 为每一个Task绑定一个Execution
        ExecutionImpl exec = execution.createExecution();
        exec.setActivity(execution.getActivity());
        exec.setState(Execution.STATE_ACTIVE_CONCURRENT);
        exec.setHistoryActivityStart(Clock.getTime());
        exec.setSuperProcessExecution(execution);
        subTask.setExecution(exec);

        //把会签子任务加入到父任务中
        superTask.addSubTask(subTask);

        TaskDefinitionImpl taskDefinition = new TaskDefinitionImpl();
        taskDefinition.setName(subTask.getName());
        subTask.setTaskDefinition(taskDefinition);

        //触发任务
        dbsession.save(subTask);

        HistoryEvent.fire(new TaskActivityStart(subTask), exec);
        //历史任务绑定父子关系
        HistoryTaskImpl historyTask = dbsession.get(HistoryTaskImpl.class, subTask.getDbid());
        HistoryTaskImpl superHistoryTask = dbsession.get(HistoryTaskImpl.class, subTask.getSuperTask().getDbid());
        superHistoryTask.addSubTask(historyTask);
        dbsession.save(superHistoryTask);

        List<FlowTaskExecutionEventListener> flowTaskExecutionEventListeners =  FlowTaskExecutionEventListenerMng.getInstance()
                .getListenersForTask(execution.getProcessInstance().getId(), execution.getProcessDefinition().getName(), FlowTaskExecutionEventListener.COUNTER_SIGN);
        for(FlowTaskExecutionEventListener listener : flowTaskExecutionEventListeners){
            listener.triggered(execution, subTask, subTask.getTaskDefinition());
        }
        HistoryEvent.fire(new EdmNotificationEvent(subTask), execution);
    }


    @SuppressWarnings("rawtypes")
    private void doEndExecutionAndTask(ExecutionImpl superExecution, TaskImpl superTask, TaskService taskService) {
        CounterSignInfo info = (CounterSignInfo) superExecution.getVariable(COUNTERSIGN_INFO);
        List<Map<String, String>> list = info.getCounterSignList();
        Iterator<Task> task = superTask.getSubTasks().iterator();
        boolean flag = true;
        while (task.hasNext()) {
            flag = true;
            Task t = task.next();
            //System.out.println("====>" + t.getExecutionId() + "、" + t.getActivityName() + "、" + t.getAssignee());
            for (Map map : list) {
                if (t.getAssignee().equals(map.get("user"))) {
                    // 如果该Task的分配者在流程变量中存在，则视为已经被completeTask提交过的，不再进行自动提交了
                    flag = false;
                    break;
                }
            }
            if (flag) {
                Map<String, String> mapInfo = new HashMap<String, String>();
                mapInfo.put("user", t.getAssignee());
                // 自动提交的Task全部视为'弃权'
                mapInfo.put("strategy", "disclaim");
                info.getCounterSignList().add(mapInfo);
                info.setAutoCompleteTask(true);
                // 封装流程变量
                superExecution.setVariable(COUNTERSIGN_INFO, info);
//                DbSession dbsession = EnvironmentImpl.getFromCurrent(DbSession.class);
//                TaskImpl taskImpl = (TaskImpl) t;
//                taskImpl.setSuperTask(null);
//                dbsession.update(taskImpl);
                taskService.completeTask(t.getId(), this.getNoPassTransiton());
            }
        }
    }

    public String getCounterSignUsers() {
        return counterSignUsers;
    }

    public void setCounterSignUsers(String counterSignUsers) {
        this.counterSignUsers = counterSignUsers;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getPassTransiton() {
        return passTransiton;
    }

    public void setPassTransiton(String passTransiton) {
        this.passTransiton = passTransiton;
    }

    public String getNoPassTransiton() {
        return noPassTransiton;
    }

    public void setNoPassTransiton(String noPassTransiton) {
        this.noPassTransiton = noPassTransiton;
    }
}
