package com.orient.collab.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.businessmodel.service.impl.QueryOrder;
import com.orient.collab.business.strategy.DefaultTeamRole;
import com.orient.collab.model.CollabFlowInfo;
import com.orient.collab.model.CollabFlowJpdlInfo;
import com.orient.collab.model.Task;
import com.orient.collab.util.JPDLTool;
import com.orient.flow.business.FlowDiagramContentBusiness;
import com.orient.flow.business.ProcessDefinitionBusiness;
import com.orient.flow.business.ProcessInstanceBusiness;
import com.orient.flow.config.FlowType;
import com.orient.flow.model.FlowTaskNodeModel;
import com.orient.jpdl.model.Process;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.sysmodel.domain.flow.FlowDataRelation;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.collab.impl.CollabRoleService;
import com.orient.sysmodel.service.flow.impl.FlowDataRelationService;
import com.orient.sysmodel.service.user.UserService;
import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import com.orient.workflow.bean.AssignUser;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.hibernate.criterion.Restrictions;
import org.jbpm.api.*;
import org.jbpm.api.history.HistoryProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.orient.collab.config.CollabConstants.*;
import static com.orient.sysmodel.domain.collab.CollabRole.NODE_ID;
import static com.orient.sysmodel.domain.collab.CollabRole.MODEL_NAME;
import static com.orient.sysmodel.domain.flow.FlowDataRelation.MAIN_TYPE;
import static com.orient.sysmodel.domain.flow.FlowDataRelation.PI_ID;

/**
 * the collab flow information business
 *
 * @author Seraph
 *         2016-08-09 上午10:38
 */
@Component
public class CollabFlowInfoBusiness extends BaseBusiness {

    public CollabFlowInfo getCurrentFlowInfoByModelNameAndDataId(String modelName, String dataId) {
        CollabFlowInfo flowInfo = new CollabFlowInfo();

        String pdKey = modelName + COLLAB_PD_NAME_SPERATOR + dataId;
        List<ProcessDefinition> processDefinitionList = this.processDefinitionBusiness.getAllPrcDefsWithPdKeyOrNameDescByVersion(pdKey, true);
        if (processDefinitionList.size() == 0) {
            return flowInfo;
        }

        ProcessDefinition latestPd = processDefinitionList.get(0);
        flowInfo.setPdId(latestPd.getId());

        HistoryProcessInstance historyProcessInstance = this.processInstanceBusiness.getLatestProcessInstanceByPdId(latestPd.getId());
        if (historyProcessInstance == null) {
            return flowInfo;
        }

        flowInfo.setPiId(historyProcessInstance.getProcessInstanceId());
        String state = historyProcessInstance.getState();
        if (HistoryProcessInstance.STATE_ACTIVE.endsWith(state)) {
            ProcessInstance pi = this.processInstanceBusiness.getCurrentProcessInstanceById(historyProcessInstance.getProcessInstanceId());
            state = pi.getState();
            if (Execution.STATE_SUSPENDED.equals(state)) {
                flowInfo.setFlowStatus(STATUS_SUSPENDED);
            } else {
                flowInfo.setFlowStatus(STATUS_PROCESSING);
            }
        } else {
            flowInfo.setFlowStatus(STATUS_FINISHED);
        }
        return flowInfo;
    }

    public synchronized CollabFlowJpdlInfo generateOrUpdateFlowJpdl(String modelName, String dataId) {
        IBusinessModel rootBm = this.businessModelService.getBusinessModelBySName(modelName, COLLAB_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        Map<String, String> rootData = this.orientSqlEngine.getBmService().createModelQuery(rootBm).findById(dataId);
        CollabFlowJpdlInfo retV = new CollabFlowJpdlInfo(modelName, dataId, rootData.get("NAME_" + rootBm.getId()));

        String pdKey = modelName + COLLAB_PD_NAME_SPERATOR + dataId;
        List<ProcessDefinition> processDefinitionList = this.processDefinitionBusiness.getAllPrcDefsWithPdKeyOrNameDescByVersion(pdKey, true);

        List<Task> tasks = this.orientSqlEngine.getTypeMappingBmService()
                .get(Task.class, QueryOrder.asc("TO_NUMBER(ID)"), new CustomerFilter(PLAN.equals(modelName) ? "parPlanId" : "parTaskId", EnumInter.SqlOperation.Equal, dataId));

        for (Task task : tasks) {
            String principal = task.getPrincipal();
            if (!CommonTools.isNullString(principal)) {
                if (principal.contains(AssignUser.DELIMITER)) {
                    String[] principals = task.getPrincipal().split(AssignUser.DELIMITER);
                    StringBuilder principalSb = new StringBuilder(50);
                    for (String userId : principals) {
                        User user = this.UserService.findById(userId);
                        principalSb.append(user.getUserName()).append(AssignUser.DELIMITER);
                    }
                    task.setPrincipal(principalSb.deleteCharAt(principalSb.length() - 1).toString());
                } else {
                    User user = this.UserService.findById(task.getPrincipal());
                    task.setPrincipal(user.getUserName());
                }
            }
        }

        String jpdlString;
        if (processDefinitionList.size() == 0) {
            JPDLTool jpdlTool = JPDLTool.getInstance();
            jpdlString = jpdlTool.createJpdlContentFromCollabTasks(tasks, modelName + COLLAB_PD_NAME_SPERATOR + dataId, JPDLTool.MAIN_FLOW);
            retV.setSuccess(true);
            retV.setJpdl(jpdlString);
        } else {
            jpdlString = updateFlowJpdl(tasks, processDefinitionList.get(0).getId(), processDefinitionList.get(0).getName());
            if (CommonTools.isNullString(jpdlString)) {
                retV.setJpdl("找不到原始文件");
            } else {
                retV.setSuccess(true);
                retV.setJpdl(jpdlString);
            }
        }

        return retV;
    }

    public String updateFlowJpdl(List<Task> tasks, String pdId, String pdName) {
        JPDLTool jpdlTool = JPDLTool.getInstance();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        String dpId = repositoryService.createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult().getDeploymentId();
        Set<String> resourceNames = repositoryService.getResourceNames(dpId);
        for (Iterator<String> it = resourceNames.iterator(); it.hasNext(); ) {
            String filename = it.next();
            if (filename.endsWith(pdName + ".jpdl.xml")) {
                InputStream inputStream = repositoryService.getResourceAsStream(dpId, filename);
                try {
                    Reader reader = new InputStreamReader(inputStream, "UTF-8");
                    Process process = Process.unmarshal(reader);
                    inputStream.close();

                    return jpdlTool.updateJpdlContentWithCollabTasks(process, tasks);
                } catch (MarshalException e) {
                    e.printStackTrace();
                } catch (ValidationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        return "";
    }

    public List<FlowTaskNodeModel> getLatestFlowDiagMonitorModelByFlowTaskId(String flowTaskId) {
        String piId = this.processInstanceBusiness.getPrcInstIdByHisFlowTaskId(flowTaskId);
        return getLatestFlowDiagMonitorModelByPiId(piId);
    }

    public List<FlowTaskNodeModel> getLatestFlowDiagMonitorModelByPiId(String piId) {
        List<FlowTaskNodeModel> retVal = UtilFactory.newArrayList();

        String pdId = this.processDefinitionBusiness.getPrcDefByPrcInstId(piId).getId();
        List<String> actAndFinActivityNames = UtilFactory.newArrayList();
        List<FlowTaskNodeModel> activeAndFinishedNodeModelList = this.flowDiagramContentBusiness.getLatestActiveAndFinishedActivityNodeModelList(pdId, piId, actAndFinActivityNames);
        List<FlowTaskNodeModel> unStartedActivityList = this.getUnStartedActivitiesNodeModelList(pdId, piId, actAndFinActivityNames);

        retVal.addAll(activeAndFinishedNodeModelList);
        retVal.addAll(unStartedActivityList);
        return retVal;
    }

    private List<FlowTaskNodeModel> getUnStartedActivitiesNodeModelList(String pdId, String piId, List<String> actAndFinActivityNames) {
        FlowDataRelation flowDataRelation = flowDataRelationService.list(Restrictions.eq(PI_ID, piId),
                Restrictions.eq(MAIN_TYPE, FlowType.Collab.toString())).get(0);
        String parentModelName = flowDataRelation.getTableName();
        String parentDataId = flowDataRelation.getDataId();

        List<FlowTaskNodeModel> unStartedActivitiesNodeMode = this.flowDiagramContentBusiness.getUnStartedActivitiesNodeModelList(pdId, piId, actAndFinActivityNames);
        for (FlowTaskNodeModel nodeMdl : unStartedActivitiesNodeMode) {
            if (CommonTools.isNullString(nodeMdl.getAssignee())) {
                CollabRole collabRole = this.collabRoleService.get(Restrictions.eq(MODEL_NAME, parentModelName),
                        Restrictions.eq(NODE_ID, parentDataId),
                        Restrictions.eq(CollabRole.NAME, DefaultTeamRole.Executor.toString()));
                if (collabRole == null) {
                    continue;
                }

                StringBuilder assigneeName = new StringBuilder();
                StringBuilder assigneeRealName = new StringBuilder();
                for (CollabRole.User user : collabRole.getUsers()) {
                    assigneeName.append(user.getUserName()).append(",");
                    assigneeRealName.append(user.getAllName()).append(",");
                }
                nodeMdl.setAssignee(assigneeName.length() > 0 ? assigneeName.deleteCharAt(assigneeName.length() - 1).toString() : "");
                nodeMdl.setAssRealName(assigneeRealName.length() > 0 ? assigneeRealName.deleteCharAt(assigneeRealName.length() - 1).toString() : "");
            }
        }
        return unStartedActivitiesNodeMode;
    }

    @Autowired
    private CollabRoleService collabRoleService;
    @Autowired
    private ProcessInstanceBusiness processInstanceBusiness;
    @Autowired
    private ProcessDefinitionBusiness processDefinitionBusiness;
    @Autowired
    protected ProcessEngine processEngine;

    @Autowired
    protected UserService UserService;


    @Autowired
    private FlowDiagramContentBusiness flowDiagramContentBusiness;
    @Autowired
    private FlowDataRelationService flowDataRelationService;

}
