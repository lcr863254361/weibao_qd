package com.orient.flow.extend.activity;

import com.orient.utils.JavaScriptManager;
import com.orient.utils.StringUtil;
import com.orient.workflow.bean.JBPMInfo;
import com.orient.workflow.cmd.SyncMainProcessBindCommand;
import org.jbpm.api.JbpmException;
import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.model.Activity;
import org.jbpm.jpdl.internal.activity.SubProcessActivity;
import org.jbpm.jpdl.internal.activity.SubProcessInParameterImpl;
import org.jbpm.jpdl.internal.activity.SubProcessOutParameterImpl;
import org.jbpm.pvm.internal.client.ClientProcessDefinition;
import org.jbpm.pvm.internal.el.Expression;
import org.jbpm.pvm.internal.env.Context;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.env.ExecutionContext;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.repository.RepositoryServiceImpl;
import org.jbpm.pvm.internal.session.RepositorySession;
import org.jbpm.pvm.internal.task.SwimlaneImpl;

import javax.faces.el.PropertyNotFoundException;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class EdmSubProcessActivity extends SubProcessActivity {

    private static final long serialVersionUID = 1L;

    public void execute(ActivityExecution execution) {
        ExecutionImpl executionImpl = (ExecutionImpl) execution;
        RepositorySession repositorySession = EnvironmentImpl.getFromCurrent(RepositorySession.class);
        ClientProcessDefinition processDefinition = null;

        if (!StringUtil.isEmpty(subProcessId)) {
            Expression subProcessKeyExpression = Expression.create(subProcessId, Expression.LANGUAGE_UEL_VALUE);
            String subProcessIdEval = (String) subProcessKeyExpression.evaluate(execution);
            processDefinition = repositorySession.findProcessDefinitionById(subProcessIdEval);

            if (processDefinition == null) {
                throw new JbpmException("cannot find process definition by id: ["
                        + subProcessId + "(" + subProcessIdEval + ")" + "]");
            }
        } else {
            String subProcessKeyEval = null;
            try {
                Expression subProcessKeyExpression = Expression.create(subProcessKey, Expression.LANGUAGE_UEL_VALUE);
                subProcessKeyEval = (String) subProcessKeyExpression.evaluate(execution);
                if (subProcessKeyEval == null) {
                    throw new JbpmException("Subprocess key '" + subProcessKey + "' resolved to null.");
                }
            } catch (PropertyNotFoundException e) {
                throw new JbpmException("Subprocess key '" + subProcessKey + "' could not be resolved.");
            }
            Set<String> pdIds = repositorySession.getDeployment(executionImpl.getProcessDefinition().getDeploymentId()).getProcessDefinitionIds();
            for (String pdId : pdIds) {
                ProcessDefinitionImpl temPdImpl = repositorySession.findProcessDefinitionById(pdId);
                if (temPdImpl.getKey().equals(subProcessKeyEval)) {
                    processDefinition = temPdImpl;
                    break;
                }
            }
            if (processDefinition == null) {
                throw new JbpmException("Subprocess '" + subProcessKeyEval + "' could not be found.");
            }

            if (processDefinition == null) {
                throw new JbpmException("cannot find process definition by key: ["
                        + subProcessKey + "(" + subProcessKeyEval + ")" + "]");
            }
        }

        ExecutionImpl subProcessInstance = (ExecutionImpl) processDefinition.createProcessInstance(null, execution);
        execution.getVariable("");
        for (String swimlaneName : swimlaneMappings.keySet()) {
            String subSwimlaneName = swimlaneMappings.get(swimlaneName);
            SwimlaneImpl subSwimlane = subProcessInstance.createSwimlane(subSwimlaneName);
            SwimlaneImpl swimlane = executionImpl.getSwimlane(swimlaneName);
            if (swimlane != null) {
                subSwimlane.initialize(swimlane);
            }
        }

        for (SubProcessInParameterImpl inParameter : inParameters) {
            inParameter.produce(executionImpl, subProcessInstance);
        }

        executionImpl.historyActivityStart();
        subProcessInstance.start();
        //同步父子流程绑定关系
        RepositoryServiceImpl repositoryService = EnvironmentImpl.getFromCurrent(RepositoryServiceImpl.class);
        repositoryService.getCommandService().execute(new SyncMainProcessBindCommand(subProcessInstance));
        execution.waitForSignal();

    }

    @SuppressWarnings({"unchecked"})
    public void signal(ExecutionImpl execution, String signalName, Map<String, ?> parameters) throws Exception {
        ExecutionImpl executionImpl = (ExecutionImpl) execution;

        ExecutionImpl subProcessInstance = executionImpl.getSubProcessInstance();

        String transitionName = null;

        ExecutionContext originalExecutionContext = null;
        ExecutionContext subProcessExecutionContext = null;
        EnvironmentImpl environment = EnvironmentImpl.getCurrent();
        if (environment != null) {
            originalExecutionContext = (ExecutionContext) environment.removeContext(Context.CONTEXTNAME_EXECUTION);
            subProcessExecutionContext = new ExecutionContext((ExecutionImpl) subProcessInstance);
            environment.setContext(subProcessExecutionContext);
        }

        try {
            subProcessInstance.setSuperProcessExecution(null);
            executionImpl.setSubProcessInstance(null);


            for (SubProcessOutParameterImpl outParameter : outParameters) {
                outParameter.consume(executionImpl, subProcessInstance);
            }

            Activity activity = execution.getActivity();
            String subProcessActivityName = subProcessInstance.getActivityName();

            if (outcomeExpression != null) {
                Object value = null;
                Map data = (Map) execution.getProcessInstance().getVariable(JBPMInfo.JBPM_FORM_DATA);
                if (data != null) {
                    value = JavaScriptManager.evaluate(outcomeExpression.getExpressionString(), data);
                } else {
                    value = activity.getOutgoingTransitions().get(0).getName();
                }
                // if the value is a String and matches the name of an outgoing transition
                if ((value instanceof String)
                        && (activity.hasOutgoingTransition(((String) value)))
                        ) {
                    // then take that one
                    transitionName = (String) value;
                } else {
                    // else see if there is a value mapping
                    transitionName = activity.getOutgoingTransitions().get(0).getName();
                }

            } else if (activity.hasOutgoingTransition(subProcessActivityName)) {
                transitionName = subProcessActivityName;
            }

        } finally {
            if (subProcessExecutionContext != null) {
                environment.removeContext(subProcessExecutionContext);
            }
            if (originalExecutionContext != null) {
                environment.setContext(originalExecutionContext);
            }
        }
        executionImpl.historyActivityEnd();
        if (transitionName != null) {
            execution.take(transitionName);
        } else {
            execution.takeDefaultTransition();
        }
    }
}
