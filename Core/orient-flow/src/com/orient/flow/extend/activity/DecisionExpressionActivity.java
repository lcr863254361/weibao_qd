package com.orient.flow.extend.activity;

import com.orient.flow.extend.extensions.FlowDecisionEventListener;
import com.orient.flow.extend.extensions.FlowDecisionEventListenerMng;
import com.orient.workflow.tools.WorkflowCommonTools;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.JbpmException;
import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.Transition;
import org.jbpm.jpdl.internal.activity.JpdlActivity;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;

import java.util.List;


/**
 * @author zhulc@cssrc.com.cn
 * @ClassName DecisionExpressionActivity
 * 判断节点的业务处理，采用js的脚本引擎处理表达式
 * @date 2012-6-6
 */

public class DecisionExpressionActivity extends JpdlActivity {

    private static final long serialVersionUID = 1L;

    protected String expression;

    private List<FlowDecisionEventListener> flowDecisionEventListeners;

    public void execute(ActivityExecution execution) {
        execute((ExecutionImpl) execution);
    }

    @SuppressWarnings({"unchecked"})
    public void execute(ExecutionImpl execution) {
        Activity activity = execution.getActivity();
        flowDecisionEventListeners = FlowDecisionEventListenerMng.getInstance()
                .getListenersForDecision(execution.getProcessInstance().getId(), execution.getProcessDefinition().getName(),
                        activity.getName());
        flowDecisionEventListeners.forEach(flowDecisionEventListener -> {
            flowDecisionEventListener.doDecide(execution, activity);
        });
        ExecutionImpl mainExecution = WorkflowCommonTools.getMainLineExecution(execution);
        ExecutionService executionService = EnvironmentImpl.getFromCurrent(ExecutionService.class);
        String result = (String) executionService.getVariable(mainExecution.getId(), activity.getName());
        String transitionName = result;
        Transition transition = activity.getOutgoingTransition(transitionName);
        if (transition == null) {
            throw new JbpmException("expression '" + expression
                    + "' in decision '" + activity.getName()
                    + "' returned unexisting outgoing transition name: "
                    + transitionName);
        }
        execution.historyDecision(transitionName);
        execution.take(transition);
    }

    public void setExpression(String expr) {
        this.expression = expr;
    }
}
