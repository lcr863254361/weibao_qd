package com.orient.flow.util;

import com.edm.nio.util.CommonTools;
import com.orient.flow.model.FlowTaskWithAssigner;
import com.orient.sysmodel.operationinterface.IUser;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.sysmodel.service.flow.IFlowCommissionService;
import com.orient.workflow.bean.AssignUser;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-08-08 15:24
 */
@Component
public class FlowCommissionHelper {
    @Autowired
    protected IRoleUtil roleEngine;

    @Autowired
    protected ProcessEngine processEngine;

    @Autowired
    protected IFlowCommissionService flowCommissionService;

    public List<FlowTaskWithAssigner> reconfigFlowTaskWithAssignerList(String pdid, List<FlowTaskWithAssigner> flowTaskWithAssignerList) {
        if(flowTaskWithAssignerList==null || flowTaskWithAssignerList.size()==0) {
            return flowTaskWithAssignerList;
        }

        for(FlowTaskWithAssigner flowTaskWithAssigner : flowTaskWithAssignerList) {
            Set<String> newAssignerSet = getAllTaskUsers(getPdNameByPdId(pdid), flowTaskWithAssigner.getTaskAssignerNames());
            List<String> newAssignerNameList = new ArrayList<>(newAssignerSet);
            List<String> newAssignerIdList = new ArrayList<>();
            List<String> newAssignerDisplayNameList = new ArrayList<>();
            for(String newAssignerName : newAssignerNameList) {
                IUser user = roleEngine.getRoleModel(false).getUserByUserName(newAssignerName);
                newAssignerIdList.add(user.getId());
                newAssignerDisplayNameList.add(user.getAllName());
            }
            flowTaskWithAssigner.setTaskAssignerIds(newAssignerIdList);
            flowTaskWithAssigner.setTaskAssignerNames(newAssignerNameList);
            flowTaskWithAssigner.setTaskAssignerDisplayNames(newAssignerDisplayNameList);
        }
        return flowTaskWithAssignerList;
    }


    public Map<String, AssignUser> reconfigTaskAssignMap(String pdid, Map<String, AssignUser> taskAssignMap) {
        if(taskAssignMap==null || taskAssignMap.size()==0) {
            return taskAssignMap;
        }

        for(String key : taskAssignMap.keySet()) {
            AssignUser assignUser = taskAssignMap.get(key);
            AssignUser newAssignUser = reconfigAssignUser(getPdNameByPdId(pdid), assignUser);
            taskAssignMap.put(key, newAssignUser);
        }
        return taskAssignMap;
    }

    /********************************************************************************************************/
    private String getPdNameByPdId(String pdid) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(pdid).list().get(0);
        String pdName = processDefinition.getName();
        return pdName;
    }

    private AssignUser reconfigAssignUser(String pdid, AssignUser assignUser) {
        String currentUser = assignUser.getCurrentUser();
        if(currentUser!=null && !"".equals(currentUser)) {
            String result = CommonTools.set2String(getAllTaskUsers(pdid, currentUser));
            assignUser.setCurrentUser(result);
        }

        String candidateUser = assignUser.getCandidateUsers();
        if(candidateUser!=null && !"".equals(candidateUser)) {
            String result = CommonTools.set2String(getAllTaskUsers(pdid, candidateUser));
            assignUser.setCandidateUsers(result);
        }

        return assignUser;
    }

    private Set<String> getAllTaskUsers(String pdid, String userNames) {
        String[] userNameArr = userNames.split(",");
        List<String> userNameList = Arrays.asList(userNameArr);
        return getAllTaskUsers(pdid, userNameList);
    }

    private Set<String> getAllTaskUsers(String pdid, Collection<String> userNames) {
        Set<String> retVal = new HashSet<>();
        for(String userName : userNames) {
            retVal.add(userName);
            Set<String> slaveUserNames = flowCommissionService.getSlaveUsers(pdid, userName);
            retVal.addAll(slaveUserNames);
        }
        return retVal;
    }
}
