package com.orient.collab.business.projectCore.flowListener;

import com.orient.flow.config.FlowType;
import com.orient.flow.extend.annotation.FlowDecisionEventMarker;
import com.orient.flow.extend.extensions.FlowDecisionEventListener;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.model.Activity;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
@FlowDecisionEventMarker(decisions = {"测试判断"}, flowTypes = FlowType.Audit, flow = "复杂流程")
public class TestDecisionListener implements FlowDecisionEventListener {

    @Override
    public void doDecide(ExecutionImpl execution, Activity activity) {
        ExecutionService executionService = processEngine.getExecutionService();
        executionService.setVariable(execution.getId(), activity.getName(), "9");
    }

    @Autowired
    ProcessEngine processEngine;
}
