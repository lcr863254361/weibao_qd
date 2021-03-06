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
        // ?????????????????????????????????
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().notSuspended().list();
        Set<String> processDefinitionKeys = new HashSet<String>();
        Map<String, ProcessDefinition> processDefinitionMap = new TreeMap<String, ProcessDefinition>();
        for (ProcessDefinition processDefinition : processDefinitions) {

            // ??????????????????????????????????????? ??????????????????????????????key??????????????????????????????????????????key?????????????????????????????????
            ProcessDefinitionImpl processDefinitionImpl = (ProcessDefinitionImpl) processDefinition;
            //??????????????? ???????????????????????? ?????????_?????????.png
            String imgName = processDefinitionImpl.getImageResourceName();
            if (imgName.split("_").length > 1) {
                continue;
            }
            // ???????????????????????????????????? ?????????????????????????????????
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
        // ?????????????????????????????????
        Iterator<String> keys = processDefinitionMap.keySet().iterator();
        while (keys.hasNext()) {
            ProcessDefinitionImpl processDefinition = (ProcessDefinitionImpl) processDefinitionMap.get(keys.next());
            // ???????????????????????????????????????
            ActivityImpl activityImpl = processDefinition.getInitial();
            // ?????????????????????????????????????????????
            List<? extends Transition> transitions = activityImpl.getOutgoingTransitions();
            // ???????????????????????? ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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

            //?????????????????????Service
            RepositoryService repositoryService = processEngine.getRepositoryService();
            //???????????????????????????????????????
            List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().notSuspended().list();
            //????????????
            Map<String, ProcessDefinition> processDefinitionMap = new LinkedHashMap<String, ProcessDefinition>();
            //?????????????????????????????????
            Set<String> processDefinitionKeys = new HashSet<String>();
            for (ProcessDefinition processDefinition : processDefinitionList) {
                //??????????????????????????????
                ProcessDefinitionImpl processDefinitionImpl = (ProcessDefinitionImpl) processDefinition;
                //??????????????? ???????????????????????? ?????????_?????????.png
                String imgName = processDefinitionImpl.getImageResourceName();
                if (imgName.split("_").length > 1) {
                    continue;
                }
                //?????????????????????
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
            //????????????ID??????
            Iterator<String> keys = processDefinitionMap.keySet().iterator();
            while (keys.hasNext()) {
                ProcessDefinitionImpl processDefinition = (ProcessDefinitionImpl) processDefinitionMap.get(keys.next());
                List<DeployProperty> propertys = (List<DeployProperty>) processEngine.execute(new PropertiesCommand(processDefinition.getDeploymentId()));
                //???????????????projectId
                String processProjectId = WorkflowCommonTools.getValueByObjNameAndKey(propertys, "USERPARA", "projectId");
                if (processProjectId.equals(prjId)) {
                    mainPd = processDefinition;
                }
            }
            if (mainPd != null && !isProject) {
                //?????????????????????????????????
                List<ProcessDefinition> pds = repositoryService.createProcessDefinitionQuery().deploymentId(mainPd.getDeploymentId()).list();
                //????????????????????????
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
     * @param piId ????????????????????????ID
     * @return ????????????????????????
     */
    public List<FlowInfo> getMainAndSubPIs(String piId) {
        List<FlowInfo> retVal = new ArrayList<>();
        //???????????????ID
        String mainPiId = getMainPiId(piId);
        //???????????????ID ???????????????
        List<Long> existDBIDs = new ArrayList<>();
        StringBuffer querySql = new StringBuffer();
        querySql.append("SELECT SUP_DBID_ SUPDBID,SUP_EXC_ID_ SUPEXCID,SUB_DBID_ SUBDBID,SUB_EXC_ID_ SUBEXCID ");
        querySql.append("FROM  JBPM4_FLOW_RELATION  ");
        querySql.append("START WITH SUP_EXC_ID_ = ? ");
        querySql.append("CONNECT BY PRIOR SUB_DBID_ = SUP_DBID_");
        List<Map<String, Object>> queryList = metaDaoFactory.getJdbcTemplate().queryForList(querySql.toString(), new Object[]{mainPiId});
        //???????????????
        for (Map<String, Object> map : queryList) {
            Long supDBID = ((BigDecimal) map.get("SUPDBID")).longValue();
            Long subDBID = ((BigDecimal) map.get("SUBDBID")).longValue();
            String superPiId = CommonTools.Obj2String(map.get("SUPEXCID"));
            String suberPiId = CommonTools.Obj2String(map.get("SUBEXCID"));
            if(suberPiId.indexOf(superPiId) == -1){
                //????????????
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
        //?????????????????????
        StringBuffer querySql = new StringBuffer();
        querySql.append("SELECT SUP_DBID_ SUPDBID,SUP_EXC_ID_ SUPEXCID ");
        querySql.append("FROM  JBPM4_FLOW_RELATION  ");
        querySql.append("START WITH SUB_DBID_ = ? ");
        querySql.append("CONNECT BY SUB_DBID_ = PRIOR SUP_DBID_");
        List<Map<String, Object>> queryList = metaDaoFactory.getJdbcTemplate().queryForList(querySql.toString(), new Object[]{dbId});
        //??????????????????????????? ???????????????  ??????????????????????????????
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