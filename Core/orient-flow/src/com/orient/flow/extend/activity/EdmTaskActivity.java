package com.orient.flow.extend.activity;

import com.orient.flow.config.FlowType;
import com.orient.flow.extend.extensions.FlowTaskExecutionEventListener;
import com.orient.flow.extend.extensions.FlowTaskExecutionEventListenerMng;
import com.orient.flow.util.FlowTypeHelper;
import com.orient.workflow.ext.mail.EdmNotificationEvent;
import org.jbpm.api.JbpmException;
import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.model.Transition;
import org.jbpm.jpdl.internal.activity.JpdlExternalActivity;
import org.jbpm.pvm.internal.cal.Duration;
import org.jbpm.pvm.internal.el.Expression;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.history.HistoryEvent;
import org.jbpm.pvm.internal.history.events.TaskActivityStart;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.task.*;

import java.util.List;
import java.util.Map;



/**
 * @ClassName EdmTaskActivity
 * 扩展任务节点，根据该任务表单表述 
 * 触发相应的事件
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class EdmTaskActivity extends JpdlExternalActivity {
	private static final long serialVersionUID = 1L;

	protected TaskDefinitionImpl taskDefinition;

	private List<FlowTaskExecutionEventListener> flowTaskExecutionEventListeners;

	public void execute(ActivityExecution execution) {
		execute((ExecutionImpl) execution);
	}

	public void execute(ExecutionImpl execution) {

		DbSession dbSession = EnvironmentImpl.getFromCurrent(DbSession.class);
		TaskImpl task = dbSession.createTask();
		task.setTaskDefinition(taskDefinition);
		task.setExecution(execution);
		task.setProcessInstance(execution.getProcessInstance());
		task.setSignalling(true);

		// initialize the name
		if (taskDefinition.getName() != null) {
			task.setName(taskDefinition.getName());
		} else {
			task.setName(execution.getActivityName());
		}

		Expression descriptionExpression = taskDefinition.getDescription();
		if (descriptionExpression != null) {
			String description = (String) descriptionExpression.evaluate(task);
			task.setDescription(description);
		}
		task.setPriority(taskDefinition.getPriority());
		task.setFormResourceName(taskDefinition.getFormResourceName());

		// calculate the due date of the task based on the due date duration
		String dueDateDescription = taskDefinition.getDueDateDescription();
		if (dueDateDescription != null) {
			task.setDuedate(Duration.calculateDueDate(dueDateDescription));
		}
	
		// save task so that TaskDbSession.findTaskByExecution works for assign
		// event listeners
		dbSession.save(task);

		SwimlaneDefinitionImpl swimlaneDefinition = taskDefinition
				.getSwimlaneDefinition();
		if (swimlaneDefinition != null) {
			SwimlaneImpl swimlane = execution
					.getInitializedSwimlane(swimlaneDefinition);
			task.setSwimlane(swimlane);

			// copy the swimlane assignments to the task
			task.setAssignee(swimlane.getAssignee());
			for (ParticipationImpl participant : swimlane.getParticipations()) {
				task.addParticipation(participant.getUserId(),
						participant.getGroupId(), participant.getType());
			}
		}

		//execution.initializeAssignments(taskDefinition, task);



		HistoryEvent.fire(new TaskActivityStart(task), execution);

		boolean isAuditFlow = FlowTypeHelper.getFlowType(execution.getProcessDefinition().getName()) == FlowType.Audit;
		if(!isAuditFlow){
			/*判断是否要进行notify该活动*/
			HistoryEvent.fire(new EdmNotificationEvent(task), execution);
		}

		execution.waitForSignal();

		flowTaskExecutionEventListeners = FlowTaskExecutionEventListenerMng.getInstance()
				.getListenersForTask(execution.getProcessInstance().getId(), execution.getProcessDefinition().getName(),
						taskDefinition.getName() == null? execution.getActivityName():taskDefinition.getName());
		for(FlowTaskExecutionEventListener listener : flowTaskExecutionEventListeners){
			listener.triggered(execution, task, taskDefinition);
		}
	}

	public void signal(ActivityExecution execution, String signalName,
			Map<String, ?> parameters) throws Exception {
		signal((ExecutionImpl) execution, signalName, parameters);
	}

	public void signal(ExecutionImpl execution, String signalName,
			Map<String, ?> parameters) throws Exception {


		ActivityImpl activity = execution.getActivity();
		if (parameters != null) {
			execution.setVariables(parameters);
		}
		
		//change status
		DbSession taskDbSession = EnvironmentImpl.getFromCurrent(DbSession.class);
//		TaskQuery taskQuery = taskDbSession.createTaskQuery();
//		String piId = execution.getProcessInstance().getId();
//		TaskImpl relatedTask = (TaskImpl)taskQuery.processInstanceId(piId).activityName(activity.getName()).uniqueResult();

		flowTaskExecutionEventListeners = FlowTaskExecutionEventListenerMng.getInstance()
				.getListenersForTask(execution.getProcessInstance().getId(), execution.getProcessDefinition().getName(),
						taskDefinition.getName() == null? execution.getActivityName():taskDefinition.getName());
		for(FlowTaskExecutionEventListener listener : flowTaskExecutionEventListeners){
			listener.left(execution, activity.getName(), taskDefinition,signalName);
		}

		execution.fire(signalName, activity);

		TaskImpl task = taskDbSession.findTaskByExecution(execution);
		if (task != null) {
			task.setSignalling(false);
		}
		Transition transition = null;
		List<? extends Transition> outgoingTransitions = activity.getOutgoingTransitions();

		if (outgoingTransitions != null && !outgoingTransitions.isEmpty()) {
			// Lookup the outgoing transition
			boolean noOutcomeSpecified = TaskConstants.NO_TASK_OUTCOME_SPECIFIED
					.equals(signalName);
			if (noOutcomeSpecified
					&& activity.findOutgoingTransition(signalName) == null) {
				// When no specific outcome was specified, the unnamed
				// transition
				// is looked up (name is null). If a null outcome was
				// specifically
				// used, then the else clause will be used (but the result is
				// the same)
				// Note: the second part of the if clause is to avoid the
				// situation
				// where the user would have chosen the same name as the
				// constant
				transition = activity.findOutgoingTransition(null);
			} else {
				transition = activity.findOutgoingTransition(signalName);
			}

			// If no transition has been found, we check if we have a special
			// case
			// in which we can still deduce the outgoing transition
			if (transition == null) {
				// no unnamed transition found
				if (signalName == null) {
					// null was explicitly given as outcome
					throw new JbpmException("No unnamed transitions were found for the task '"+ getTaskDefinition().getName() + "'");
				} else if (noOutcomeSpecified) {
					// Special case: complete(id)
					if (outgoingTransitions.size() == 1) { // If only 1  transition, take  that one
						transition = outgoingTransitions.get(0);
					} else {
						throw new JbpmException(
								"No unnamed transitions were found for the task '"
										+ getTaskDefinition().getName() + "'");
					}
				} else {
					// Likely a programmatic error.
					throw new JbpmException("No transition named '"
							+ signalName + "' was found.");
				}
			}

			if (task != null && !task.isCompleted()) {
				// task should be skipped since it is not completed yet !!!
				task.skip(transition.getName());
			}

			if (transition != null) {
				execution.take(transition);
			}
			
		}
	}

	public TaskDefinitionImpl getTaskDefinition() {
		return taskDefinition;
	}

	public void setTaskDefinition(TaskDefinitionImpl taskDefinition) {
		this.taskDefinition = taskDefinition;
	}
}
