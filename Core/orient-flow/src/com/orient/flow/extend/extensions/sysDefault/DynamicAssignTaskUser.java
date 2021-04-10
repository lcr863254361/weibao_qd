package com.orient.flow.extend.extensions.sysDefault;

import com.orient.flow.config.FlowType;
import com.orient.flow.extend.annotation.CommentInfo;
import com.orient.flow.extend.annotation.FlowTaskExecutionEventMarker;
import com.orient.flow.extend.extensions.FlowTaskExecutionEventListener;
import com.orient.utils.CommonTools;
import com.orient.utils.ReflectUtil;
import com.orient.workflow.bean.AssignUser;
import com.orient.workflow.bean.JBPMInfo;
import org.jbpm.pvm.internal.history.HistoryEvent;
import org.jbpm.pvm.internal.history.events.TaskUpdated;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

import java.util.Map;
import java.util.StringTokenizer;

import static com.orient.flow.extend.extensions.FlowTaskExecutionEventListener.FLOW_END;
import static com.orient.flow.extend.extensions.FlowTaskExecutionEventListener.FLOW_START;

/**
 * 为活动设定人员，人员既可以在流程文件中设定 另外也可以在流程执行过程，由某些人设定
 *            人员设置的保存变量是JBPMInfo.DynamicUserAssign
 * 协同任务目前仅支持单用户，后期修改协同任务执行人需更改此方法
 * 审批任务支持单用户、用户组、角色等
 * @author Seraph
 *
 */
@CommentInfo(displayName = "DynamicAssignUserEvent", allowSelect = false)
@FlowTaskExecutionEventMarker(exceptTasks = {FLOW_START, FLOW_END,FlowTaskExecutionEventListener.FLOW_END_ERROR}, flowTypes = {FlowType.Audit}, order = -1)
public class DynamicAssignTaskUser implements FlowTaskExecutionEventListener {

	protected boolean setFlowTaskAssigneeFromFlowVariable(ExecutionImpl execution, TaskImpl task, TaskDefinitionImpl taskDefinition) {
		if(null != task && null != taskDefinition){
			ExecutionImpl mainExecution = execution.getProcessInstance();
			while (mainExecution.getProcessInstance().getSuperProcessExecution() != null) {
				mainExecution = mainExecution.getSuperProcessExecution()
						.getProcessInstance();
			}
			Map<String, AssignUser> taskAssignUser = (Map<String, AssignUser>) mainExecution
					.getProcessInstance().getVariable(JBPMInfo.DynamicUserAssign);
			if (null != taskAssignUser) {
				AssignUser assignuser = taskAssignUser.get(task.getName());
				if (null == assignuser) {
					return false;
				} else {
					if (!CommonTools.isNullString(assignuser.getCurrentUser())) {
						task.setAssignee(assignuser.getCurrentUser());
					}
					if (!CommonTools.isNullString(assignuser.getCurrentGroup())) {
						task.setAssignee(assignuser.getCurrentGroup());
					}

					if (!CommonTools.isNullString(assignuser.getCandidateUsers())) {
						StringTokenizer tokenizer = new StringTokenizer(
								assignuser.getCandidateUsers(), AssignUser.DELIMITER);
						while (tokenizer.hasMoreTokens()) {
							String candidateUser = tokenizer.nextToken().trim();
							task.addCandidateUser(candidateUser);
						}
					}
					if (!CommonTools.isNullString(assignuser.getCandidateGroups())) {
						StringTokenizer tokenizer = new StringTokenizer(
								assignuser.getCandidateGroups(), AssignUser.DELIMITER);
						while (tokenizer.hasMoreTokens()) {
							String candidateGroup = tokenizer.nextToken().trim();
							task.addCandidateGroup(candidateGroup);
						}
					}
					Boolean isNew = (Boolean)ReflectUtil.getFieldValue(task,"isNew");
					if(!isNew){
						HistoryEvent.fire(new TaskUpdated(task), task.getExecution());
					}
					//clear
//				taskAssignUser.put(task.getName(), null);
				}
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public void triggered(ExecutionImpl execution, TaskImpl task, TaskDefinitionImpl taskDefinition) {
		if(!setFlowTaskAssigneeFromFlowVariable(execution, task, taskDefinition)){
			execution.initializeAssignments(taskDefinition, task);
		}
	}

	@Override
	public void left(ExecutionImpl execution, String activityName, TaskDefinitionImpl taskDefinition, String signalName) {

	}
}
