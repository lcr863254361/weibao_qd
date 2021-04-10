package com.orient.webservice.collab.impl;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collab.config.CollabConstants;
import com.orient.flow.business.FlowTaskBusiness;
import com.orient.flow.config.FlowType;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.flow.TaskDataRelation;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.sysmodel.service.flow.impl.TaskDataRelationService;
import com.orient.utils.XmlCastToModel;
import com.orient.webservice.collab.ICollab;
import com.orient.webservice.collab.model.CollabTask;
import com.orient.webservice.collab.model.TaskInfo;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.orient.collab.config.CollabConstants.TASK;
import static com.orient.sysmodel.domain.flow.TaskDataRelation.PI_ID;
import static com.orient.sysmodel.domain.flow.TaskDataRelation.TABLE_NAME;
import static com.orient.sysmodel.domain.flow.TaskDataRelation.TASK_NAME;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-09-26 16:24
 */
public class CollabImpl implements ICollab {
    private static final String mappingPath = CollabImpl.class.getResource("/com/orient/webservice/collab/model/map_collab.xml").getPath();

    @Autowired
    private IRoleUtil roleEngine;

    @Autowired
    private FlowTaskBusiness flowTaskBusiness;

    @Autowired
    private ISqlEngine orientSqlEngine;

    @Autowired
    private IBusinessModelService businessModelService;

    @Autowired
    private TaskDataRelationService taskDataRelationService;
    
    @Override
    @Transactional
    public String getTaskList(String userName, String prjId) {
        String schemaId = CollabConstants.COLLAB_SCHEMA_ID;
        IBusinessModel taskBm = businessModelService.getBusinessModelBySName(CollabConstants.TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel prjBm = businessModelService.getBusinessModelBySName(CollabConstants.PROJECT, schemaId, EnumInter.BusinessModelEnum.Table);
        String taskBmId = taskBm.getId();
        String prjBmId = prjBm.getId();

        List<Task> tasks = this.flowTaskBusiness.getCurrentTaskListOfType(userName, FlowType.Collab, null);
        TaskInfo taskInfo = new TaskInfo();
        List<CollabTask> collabTasks = taskInfo.getTasks();
        for(Task task : tasks) {
            String taskId = getRealTaskId(task);
            Map<String, String> taskMap = getTaskByTaskId(taskId);
            CollabTask collabTask = new CollabTask();
            collabTask.setTaskId(taskMap.get("ID"));
            collabTask.setTaskName(taskMap.get("NAME_" + taskBmId));
            collabTask.setStatus(taskMap.get("STATUS_"+taskBmId));
            collabTask.setCreateTime(null);
            collabTask.setNote(null);

            Map<String, String> prjMap = getProjectByTaskId(taskId);
            collabTask.setProjectId(prjMap.get("ID"));
            collabTask.setProjectName(prjMap.get("NAME_"+prjBmId));
            collabTask.setProjectCreator(null);

            String principalId = prjMap.get("PRINCIPAL_" + prjBmId);
            if(principalId!=null && !"".equals(principalId)) {
                String principalName = roleEngine.getRoleModel(false).getUserById(principalId).getUserName();
                collabTask.setProjectDesigner(principalName);
            }

            collabTasks.add(collabTask);
            if(prjId!=null && !"".equals(prjId) && !prjId.equals(collabTask.getProjectId())) {
                collabTasks.remove(collabTask);
            }
        }
        String retVal = new XmlCastToModel<TaskInfo>().beanToXml(taskInfo, mappingPath);
        return retVal;
    }

    private String getRealTaskId(Task task) {
        TaskImpl taskImpl = (TaskImpl) task;
        String piId = taskImpl.getExecution().getProcessInstance().getId();
        List<TaskDataRelation> taskDataRelations = taskDataRelationService.list(new Criterion[]{Restrictions.eq(TASK_NAME, task.getName()),
                Restrictions.eq(TABLE_NAME, TASK), Restrictions.eq(PI_ID, piId)}, Order.desc(TaskDataRelation.CREATE_TIME));
        if (taskDataRelations==null || taskDataRelations.size()==0) {
            return null;
        }
        return taskDataRelations.get(0).getDataId();
    }

    private Map<String, String> getProjectByTaskId(String taskId) {
        String schemaId = CollabConstants.COLLAB_SCHEMA_ID;

        Map<String, String> task = getTaskByTaskId(taskId);
        while(task.get("CB_PLAN_"+schemaId+"_ID")==null) {
            taskId = task.get(("CB_TASK_"+schemaId+"_ID"));
            task = getTaskByTaskId(taskId);
        }

        String planId = task.get("CB_PLAN_"+schemaId+"_ID");
        Map<String, String> plan = getPlanByPlanId(planId);
        while(plan.get("CB_PROJECT_"+schemaId+"_ID")==null) {
            planId = plan.get(("CB_PLAN_"+schemaId+"_ID"));
            plan = getPlanByPlanId(planId);
        }

        String projectId = plan.get("CB_PROJECT_"+schemaId+"_ID");
        Map<String, String> project = getProjByProjId(projectId);

        return project;
    }

    private Map<String, String> getTaskByTaskId(String taskId) {
        String schemaId = CollabConstants.COLLAB_SCHEMA_ID;
        IBusinessModel bm = businessModelService.getBusinessModelBySName(CollabConstants.TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        bm.setReserve_filter(" AND ID="+taskId);
        List<Map<String,String>> list = orientSqlEngine.getBmService().createModelQuery(bm).list();
        if(list!=null && list.size()==1) {
            return list.get(0);
        }
        return null;
    }

    private Map<String, String> getPlanByPlanId(String planId) {
        String schemaId = CollabConstants.COLLAB_SCHEMA_ID;
        IBusinessModel bm = businessModelService.getBusinessModelBySName(CollabConstants.PLAN, schemaId, EnumInter.BusinessModelEnum.Table);
        bm.setReserve_filter(" AND ID="+planId);
        List<Map<String,String>> list = orientSqlEngine.getBmService().createModelQuery(bm).list();
        if(list!=null && list.size()==1) {
            return list.get(0);
        }
        return null;
    }

    private Map<String, String> getProjByProjId(String projId) {
        String schemaId = CollabConstants.COLLAB_SCHEMA_ID;
        IBusinessModel bm = businessModelService.getBusinessModelBySName(CollabConstants.PROJECT, schemaId, EnumInter.BusinessModelEnum.Table);
        bm.setReserve_filter(" AND ID="+ projId);
        List<Map<String,String>> list = orientSqlEngine.getBmService().createModelQuery(bm).list();
        if(list!=null && list.size()==1) {
            return list.get(0);
        }
        return null;
    }
}
