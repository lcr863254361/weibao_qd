package com.orient.workflow.service.impl;

import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.sysmodel.domain.workflow.JbpmConfigUser;
import com.orient.sysmodel.operationinterface.IRole;
import com.orient.sysmodel.operationinterface.IRoleModel;
import com.orient.sysmodel.operationinterface.IUser;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.utils.CommonTools;
import com.orient.workflow.bean.AgencyWorkflow;
import com.orient.workflow.bean.DeployProperty;
import com.orient.workflow.bean.FlowInfo;
import com.orient.workflow.cmd.PropertiesCommand;
import com.orient.workflow.service.ProcessInformationService;
import com.orient.workflow.tools.WorkflowCommonTools;
import org.jbpm.api.*;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.model.Transition;
import org.jbpm.jpdl.internal.activity.TaskActivity;
import org.jbpm.pvm.internal.history.HistoryEvent;
import org.jbpm.pvm.internal.history.events.VariableCreate;
import org.jbpm.pvm.internal.history.model.HistoryProcessInstanceImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.task.SwimlaneDefinitionImpl;
import org.jbpm.pvm.internal.type.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
public class ProcessInformationServiceImpl implements ProcessInformationService {

    @Autowired
    protected MetaDAOFactory metaDaoFactory;
    /**
     * @Fields processEngine :
     */
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private IRoleUtil roleEngine;

    public ProcessInformationServiceImpl() {
    }

    /**
     * @return
     * @Method: getAllProcessDefinitionKeys
     * @see com.orient.workflow.service.ProcessInformationService#getAllProcessDefinitionKeys()
     */
    public List<ProcessDefinition> getAllProcessDefinitionKeys() {
        return processEngine.getRepositoryService().createProcessDefinitionQuery().list();
    }

    /**
     * @return
     * @Method: getAllOpenExecutions
     * @see com.orient.workflow.service.ProcessInformationService#getAllOpenExecutions()
     */
    public List<ProcessInstance> getAllOpenExecutions() {
        return processEngine.getExecutionService().createProcessInstanceQuery().list();
    }

    public List<ProcessDefinition> getUserAllProcess(String userId, String roleName) {
        List<ProcessDefinition> processDefinitionList = new ArrayList<ProcessDefinition>();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 得到所有的流程定义列表
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().notSuspended().list();
        Set<String> processDefinitionKeys = new HashSet<String>();
        Map<String, ProcessDefinition> processDefinitionMap = new TreeMap<String, ProcessDefinition>();
        for (ProcessDefinition processDefinition : processDefinitions) {

            // 查询流程定义中的子流程结点 然后取得子流程定义的key（工作流编辑器中子流程文件的key与子流程结点名称相同）
            ProcessDefinitionImpl processDefinitionImpl = (ProcessDefinitionImpl) processDefinition;
            //过滤子流程 只保留主流程定义 主流程_子流程.png
            String imgName = processDefinitionImpl.getImageResourceName();
            if (imgName.split("_").length > 1) {
                continue;
            }
            // 对已经发布的流程进行过滤 过滤掉低版本的流程对象
            if (processDefinitionKeys.contains(processDefinition.getKey())) {
                ProcessDefinition pd = processDefinitionMap.get(processDefinition.getKey());
                if (pd.getVersion() < processDefinition.getVersion()) {
                    processDefinitionMap.put(processDefinition.getKey(), processDefinition);
                }
            } else {
                processDefinitionKeys.add(processDefinition.getKey());
                processDefinitionMap.put(processDefinition.getKey(), processDefinition);
            }
        }
        // 遍历流程定义文件中结点
        Iterator<String> keys = processDefinitionMap.keySet().iterator();
        while (keys.hasNext()) {
            ProcessDefinitionImpl processDefinition = (ProcessDefinitionImpl) processDefinitionMap.get(keys.next());
            // 首先得到当前流程的开始结点
            ActivityImpl activityImpl = processDefinition.getInitial();
            // 得到开始结点后的所有的跳转对象
            List<? extends Transition> transitions = activityImpl.getOutgoingTransitions();
            // 遍历跳转结点对象 得到工作流开始的任务活动结点，然后根据判断流程中第一个任务当前用户是否能看到
            for (Transition transition : transitions) {
                ActivityImpl activity = (ActivityImpl) transition.getDestination();
                if (activity.getType().equals("task")) {
                    ActivityImpl taskActivity = (ActivityImpl) activity;
                    TaskActivity task = (TaskActivity) taskActivity.getActivityBehaviour();
                    String taskAssignee = task.getTaskDefinition().getAssigneeExpression().getExpressionString();
                    String candidateUsers = task.getTaskDefinition().getCandidateUsersExpression().getExpressionString();
                    String taskGroups = task.getTaskDefinition().getCandidateGroupsExpression().getExpressionString();
                    SwimlaneDefinitionImpl swimlaneDefinitionImpl = task.getTaskDefinition().getSwimlaneDefinition();
                    if (swimlaneDefinitionImpl != null) {
                        taskAssignee = swimlaneDefinitionImpl.getAssigneeExpression().getExpressionString();
                        candidateUsers = swimlaneDefinitionImpl.getCandidateUsersExpression().getExpressionString();
                        taskGroups = swimlaneDefinitionImpl.getCandidateGroupsExpression().getExpressionString();
                    }
                    if (taskAssignee != null && taskAssignee.equals(userId)) {
                        processDefinitionList.add(processDefinition);
                    } else if (taskGroups != null) {
                        String[] roles = taskGroups.split(",");
                        for (int i = 0; i < roles.length; i++) {
                            if (CommonTools.ifInStr(roles[i], roleName) && !processDefinitionList.contains(processDefinition)) {
                                processDefinitionList.add(processDefinition);
                            }
                        }
                    } else if (candidateUsers != null) {
                        String[] users = candidateUsers.split(",");
                        for (int i = 0; i < users.length; i++) {
                            if (users[i].equals(userId) && !processDefinitionList.contains(processDefinition)) {
                                processDefinitionList.add(processDefinition);
                            }
                        }
                    }
                }
            }
        }
        return processDefinitionList;
    }

    @SuppressWarnings("unchecked")
    public ProcessDefinition getProcessDefinitionByProjectIdOrTaskId(boolean isProject, String name, String prjId) {
        ProcessDefinition mainPd = null;
        ProcessDefinition retPd = null;
        try {

            //管理流程定义的Service
            RepositoryService repositoryService = processEngine.getRepositoryService();
            //得到所有没有暂停的流程定义
            List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().notSuspended().list();
            //结果集合
            Map<String, ProcessDefinition> processDefinitionMap = new LinkedHashMap<String, ProcessDefinition>();
            //存放所有流程名称的集合
            Set<String> processDefinitionKeys = new HashSet<String>();
            for (ProcessDefinition processDefinition : processDefinitionList) {
                //强转为流程定义实现类
                ProcessDefinitionImpl processDefinitionImpl = (ProcessDefinitionImpl) processDefinition;
                //过滤子流程 只保留主流程定义 主流程_子流程.png
                String imgName = processDefinitionImpl.getImageResourceName();
                if (imgName.split("_").length > 1) {
                    continue;
                }
                //过滤低版本流程
                if (processDefinitionKeys.contains(processDefinition.getKey())) {
                    ProcessDefinition pd = processDefinitionMap.get(processDefinition.getKey());
                    if (pd.getVersion() < processDefinition.getVersion()) {
                        processDefinitionMap.put(processDefinition.getKey(), processDefinition);
                    }
                } else {
                    processDefinitionKeys.add(processDefinition.getKey());
                    processDefinitionMap.put(processDefinition.getKey(), processDefinition);
                }
            }
            //根据项目ID遍历
            Iterator<String> keys = processDefinitionMap.keySet().iterator();
            while (keys.hasNext()) {
                ProcessDefinitionImpl processDefinition = (ProcessDefinitionImpl) processDefinitionMap.get(keys.next());
                List<DeployProperty> propertys = (List<DeployProperty>) processEngine.execute(new PropertiesCommand(processDefinition.getDeploymentId()));
                //流程定义的projectId
                String processProjectId = WorkflowCommonTools.getValueByObjNameAndKey(propertys, "USERPARA", "projectId");
                if (processProjectId.equals(prjId)) {
                    mainPd = processDefinition;
                }
            }
            if (mainPd != null && !isProject) {
                //得到一起部署的所有流程
                List<ProcessDefinition> pds = repositoryService.createProcessDefinitionQuery().deploymentId(mainPd.getDeploymentId()).list();
                //根据流程名称过滤
                for (ProcessDefinition pd : pds) {
                    if (pd.getName().equals(name)) {
                        retPd = pd;
                        break;
                    }
                }
            } else if (isProject) {
                retPd = mainPd;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retPd;
    }

    @SuppressWarnings({"unchecked"})
    public List<AgencyWorkflow> getConfigUserAllProcess(String userId, Boolean flag) {
        IRoleModel roleModel = roleEngine.getRoleModel(false);
        IUser configuser = roleModel.getUserByUserName(userId);
        Set<JbpmConfigUser> jcUserSet = (Set<JbpmConfigUser>) configuser.getJbpmConfigUserSet();
        List<AgencyWorkflow> awList = new ArrayList<AgencyWorkflow>();
        StringBuilder sb = new StringBuilder();
        for (JbpmConfigUser jcUser : jcUserSet) {
            sb.setLength(0);
            AgencyWorkflow aw = new AgencyWorkflow();
            IUser user = roleModel.getUserById(jcUser.getId().getUserid());
            aw.setConfigUser(configuser);
            aw.setUser(user);
            List<IRole> roleList = roleModel.getRolesOfUser(user.getId());
            for (IRole role : roleList) {
                sb.append(",").append(role.getName());
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(0);
            }
            aw.setPds(this.getUserAllProcess(user.getUserName(), sb.toString()));
        }

        return awList;
    }


    @SuppressWarnings({"unchecked"})
    public void saveTaskVar(Execution execution, final String key, final String value) {
        final ExecutionImpl executionImpl = (ExecutionImpl) execution;
        processEngine.execute(new Command() {
            private static final long serialVersionUID = 1L;

            public Object execute(Environment env) {
                executionImpl.createVariable(key, value, "string", false);
                Variable variable = executionImpl.getVariableObject(key);
                HistoryEvent.fire(new VariableCreate(variable));
                return null;
            }
        });
    }

    /**
     * @param piId 任意层级流程实例ID
     * @return 获取父子流程集合
     */
    public List<FlowInfo> getMainAndSubPIs(String piId) {
        List<FlowInfo> retVal = new ArrayList<>();
        //获取主流程ID
        String mainPiId = getMainPiId(piId);
        //根据主流程ID 获取子流程
        List<Long> existDBIDs = new ArrayList<>();
        StringBuffer querySql = new StringBuffer();
        querySql.append("SELECT SUP_DBID_ SUPDBID,SUP_EXC_ID_ SUPEXCID,SUB_DBID_ SUBDBID,SUB_EXC_ID_ SUBEXCID ");
        querySql.append("FROM  JBPM4_FLOW_RELATION  ");
        querySql.append("START WITH SUP_EXC_ID_ = ? ");
        querySql.append("CONNECT BY PRIOR SUB_DBID_ = SUP_DBID_");
        List<Map<String, Object>> queryList = metaDaoFactory.getJdbcTemplate().queryForList(querySql.toString(), new Object[]{mainPiId});
        //拼接返回值
        for (Map<String, Object> map : queryList) {
            Long supDBID = ((BigDecimal) map.get("SUPDBID")).longValue();
            Long subDBID = ((BigDecimal) map.get("SUBDBID")).longValue();
            String superPiId = CommonTools.Obj2String(map.get("SUPEXCID"));
            String suberPiId = CommonTools.Obj2String(map.get("SUBEXCID"));
            if(suberPiId.indexOf(superPiId) == -1){
                //去除会签
                if (!existDBIDs.contains(supDBID)) {
                    existDBIDs.add(supDBID);
                    retVal.add(new FlowInfo(superPiId, supDBID));
                }
                if (!existDBIDs.contains(subDBID)) {
                    existDBIDs.add(subDBID);
                    retVal.add(new FlowInfo(suberPiId, supDBID));

                }
            }
        }
        if (retVal.isEmpty()) {
            retVal.add(new FlowInfo(piId, 0l));
        }
        return retVal;
    }

    private String getMainPiId(String piId) {
        HistoryService historyService = processEngine.getHistoryService();
        HistoryProcessInstanceImpl historyProcessInstance = (HistoryProcessInstanceImpl) historyService.createHistoryProcessInstanceQuery().processInstanceId(piId).uniqueResult();
        String mainPiId = piId;
        Long dbId = historyProcessInstance.getDbid();
        //拿到根节点信息
        StringBuffer querySql = new StringBuffer();
        querySql.append("SELECT SUP_DBID_ SUPDBID,SUP_EXC_ID_ SUPEXCID ");
        querySql.append("FROM  JBPM4_FLOW_RELATION  ");
        querySql.append("START WITH SUB_DBID_ = ? ");
        querySql.append("CONNECT BY SUB_DBID_ = PRIOR SUP_DBID_");
        List<Map<String, Object>> queryList = metaDaoFactory.getJdbcTemplate().queryForList(querySql.toString(), new Object[]{dbId});
        //最后一个值为主流程 根据主流程  拿到所有此次流程信息
        if (null != queryList && queryList.size() > 0) {
            mainPiId = (String) queryList.get(queryList.size() - 1).get("SUPEXCID");
        }
        return mainPiId;
    }

    @Override
    public String getProcessDefinitionIdByHisPiId(String processInstanceId) {
        return processEngine.getHistoryService().createHistoryProcessInstanceQuery().processInstanceId(processInstanceId).uniqueResult().getProcessDefinitionId();
    }

    public void setProcessEngine(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }


    public IRoleUtil getRoleEngine() {
        return roleEngine;
    }

    public void setRoleEngine(IRoleUtil roleEngine) {
        this.roleEngine = roleEngine;
    }
}