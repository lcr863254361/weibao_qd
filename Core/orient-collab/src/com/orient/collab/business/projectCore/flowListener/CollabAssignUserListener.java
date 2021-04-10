package com.orient.collab.business.projectCore.flowListener;

import com.orient.collab.business.strategy.DefaultTeamRole;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.Task;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.config.FlowType;
import com.orient.flow.extend.annotation.CommentInfo;
import com.orient.flow.extend.annotation.FlowTaskExecutionEventMarker;
import com.orient.flow.extend.extensions.FlowTaskExecutionEventListener;
import com.orient.flow.extend.extensions.sysDefault.DynamicAssignTaskUser;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.sysmodel.domain.flow.TaskDataRelation;
import com.orient.sysmodel.service.collab.impl.CollabRoleService;
import com.orient.sysmodel.service.flow.ITaskDataRelationService;
import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseDAO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

import java.util.List;
import java.util.Set;

import static com.orient.collab.config.CollabConstants.TASK;
import static com.orient.flow.extend.extensions.FlowTaskExecutionEventListener.*;
import static com.orient.sysmodel.domain.collab.CollabRole.NODE_ID;
import static com.orient.sysmodel.domain.collab.CollabRole.MODEL_NAME;
import static com.orient.sysmodel.domain.flow.TaskDataRelation.*;

/**
 * CollabAssignUserListener
 *
 * @author Seraph
 *         2016-08-16 上午11:22
 */
@CommentInfo(displayName = "DynamicAssignUserEvent", allowSelect = false)
@FlowTaskExecutionEventMarker(exceptTasks = {FLOW_START, FLOW_END, FLOW_END_ERROR}, flowTypes = {FlowType.Collab}, order = 0)
public class CollabAssignUserListener extends DynamicAssignTaskUser implements FlowTaskExecutionEventListener {

    @Override
    public void triggered(ExecutionImpl execution, TaskImpl task, TaskDefinitionImpl taskDefinition) {

        if (!super.setFlowTaskAssigneeFromFlowVariable(execution, task, taskDefinition)) {
            String oldAssignee = taskDefinition.getAssigneeExpression() == null ?
                    "" : taskDefinition.getAssigneeExpression().getExpressionString();
            setCollabFlowTaskAssignee(execution, task, taskDefinition, oldAssignee);
        }
    }

    @Override
    public void left(ExecutionImpl execution, String activityName, TaskDefinitionImpl taskDefinition, String signalName) {

    }

    private void setCollabFlowTaskAssignee(ExecutionImpl execution, TaskImpl task, TaskDefinitionImpl taskDefinition, String oldAssignee) {
        ITaskDataRelationService taskDataRelationService = OrientContextLoaderListener.Appwac.getBean(ITaskDataRelationService.class);
        List<TaskDataRelation> taskDataRelations = taskDataRelationService.list(
                new Criterion[]{Restrictions.eq(TABLE_NAME, TASK), Restrictions.eq(PI_ID, execution.getProcessInstance().getId()), Restrictions.eq(TASK_NAME, task.getName())},
                Order.desc(TaskDataRelation.CREATE_TIME));
        if (!CommonTools.isEmptyList(taskDataRelations)) {
            TaskDataRelation taskDataRelation = taskDataRelations.get(0);
            ISqlEngine sqlEngine = OrientContextLoaderListener.Appwac.getBean(ISqlEngine.class);
            Task collabTask = sqlEngine.getTypeMappingBmService().getById(Task.class, taskDataRelation.getDataId());
            if (CollabConstants.TASK_TYPE_COUNTERSIGN.equals(collabTask.getType())) {
                return;
            } else {

                CollabRoleService collabRoleService = OrientContextLoaderListener.Appwac.getBean(CollabRoleService.class);
                CollabRole collabRole = collabRoleService.get(Restrictions.eq(MODEL_NAME, TASK),
                        Restrictions.eq(NODE_ID, collabTask.getId()),
                        Restrictions.eq(CollabRole.NAME, DefaultTeamRole.Executor.toString()));

                if (collabRole.getUsers().size() > 0 || !CommonTools.isNullString(collabTask.getPrincipal())) {

                    Set<String> userNames = UtilFactory.newHashSet();
                    for (CollabRole.User user : collabRole.getUsers()) {
                        userNames.add(user.getUserName());
                    }

                    if (!CommonTools.isNullString(collabTask.getPrincipal())) {
                        BaseDAO baseDao = OrientContextLoaderListener.Appwac.getBean("baseDAO", BaseDAO.class);
                        if (collabTask.getPrincipal().contains(CollabConstants.USERID_SPERATOR)) {
                            String[] assigneeIds = collabTask.getPrincipal().split(CollabConstants.USERID_SPERATOR);
                            for (String assigneeId : assigneeIds) {
                                String curAssignee = baseDao.getUserLoginNameByUserId(assigneeId);
                                userNames.add(curAssignee);
                            }
                        } else {
                            String curAssignee = baseDao.getUserLoginNameByUserId(collabTask.getPrincipal());
                            userNames.add(curAssignee);
                        }
                    }

                    if (userNames.size() == 1) {
                        task.setAssignee(String.valueOf(userNames.toArray()[0]));
                    } else {
                        userNames.forEach(userName -> task.addCandidateUser(userName));
                    }
                } else {
                    execution.initializeAssignments(taskDefinition, task);
                }
            }
        }

    }

}
