package com.orient.collab.business.projectCore.extensions.interceptors;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.business.projectCore.constant.ProcessType;
import com.orient.collab.business.projectCore.exception.CollabFlowControlException;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessInterceptor;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessMarker;
import com.orient.collab.business.projectCore.extensions.util.ThreadParamaterHolder;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.StatefulModel;
import com.orient.collab.model.Task;
import com.orient.flow.business.ProcessDefinitionBusiness;
import com.orient.flow.business.ProcessInstanceBusiness;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Set;

import static com.orient.collab.config.CollabConstants.COLLAB_PD_NAME_SPERATOR;
import static com.orient.collab.config.CollabConstants.PLAN;

/**
 * when start a collab flow, examine the pd and pi
 *
 * @author Seraph
 *         2016-08-15 上午9:22
 */
@CollabProcessMarker(order = 1, processType = {ProcessType.START}, models = {CollabConstants.PLAN, CollabConstants.TASK})
public class StartFlowExamineInterceptor implements CollabProcessInterceptor {

    @Override
    public boolean preHandle(StatefulModel statefulModel, String modelName, ProcessType processType) throws Exception {
        String pdKey = modelName + COLLAB_PD_NAME_SPERATOR + statefulModel.getId();
        List<ProcessDefinition> processDefinitionList = processDefinitionBusiness.getAllPrcDefsWithPdKeyOrNameDescByVersion(pdKey, true);

        if (processDefinitionList.size() == 0) {
//            throw new CollabFlowControlException("未部署流程定义");
            ThreadParamaterHolder.put(ProcessDefinition.class, null);
            return true;
        }

        ProcessDefinition processDefinition = processDefinitionList.get(0);
        ProcessInstance pi = processInstanceBusiness.getLatestActiveProcessInstanceByPdId(processDefinition.getId());
        if (pi != null) {
            throw new CollabFlowControlException("已有流程正在运行,无法重复启动");
        }

        String consistencyInfo = checkConsistencyBetweenFlowAndCollabTask(processDefinition, statefulModel, modelName);
        if (!CommonTools.isNullString(consistencyInfo)) {
            throw new CollabFlowControlException(consistencyInfo);
        }

        ThreadParamaterHolder.put(ProcessDefinition.class, processDefinition);
        return true;
    }

    @Override
    public void afterCompletion(StatefulModel statefulModel, String modelName, ProcessType processType, Object processResult) throws Exception {

    }

    private String checkConsistencyBetweenFlowAndCollabTask(ProcessDefinition processDefinition, StatefulModel statefulModel, String modelName) {
        StringBuilder sb = new StringBuilder();

        List<ActivityImpl> allActivity = (List<ActivityImpl>) ((ProcessDefinitionImpl) processDefinition).getActivities();
        List<Task> collabTasks = this.sqlEngine.getTypeMappingBmService().get(Task.class,
                new CustomerFilter(PLAN.equals(modelName) ? Task.PAR_PLAN_ID : Task.PAR_TASK_ID, EnumInter.SqlOperation.Equal, statefulModel.getId()));

        Set<String> collabTaskInfo = UtilFactory.newHashSet();
        for (Task collabTask : collabTasks) {
            collabTaskInfo.add(collabTask.getName());
        }

        Set<String> flowTaskInfo = UtilFactory.newHashSet();
        for (ActivityImpl activity : allActivity) {
            if (!activity.getType().equals("task") && !activity.getType().equals("custom")) {
                continue;
            }

            flowTaskInfo.add(activity.getName());
            if (collabTaskInfo.contains(activity.getName())) {
                continue;
            }
            sb.append("流程中任务'").append(activity.getName()).append("'不存在;");

        }

        for (Task collabTask : collabTasks) {
            if (!flowTaskInfo.contains(collabTask.getName())) {
                sb.append("任务'").append(collabTask.getName()).append("'不存在于流程中;");
            }
        }

        return sb.toString();
    }


    @Autowired
    private ISqlEngine sqlEngine;
    @Autowired
    @Qualifier("processInstanceBusiness")
    private ProcessInstanceBusiness processInstanceBusiness;
    @Autowired
    @Qualifier("processDefinitionBusiness")
    private ProcessDefinitionBusiness processDefinitionBusiness;
}
