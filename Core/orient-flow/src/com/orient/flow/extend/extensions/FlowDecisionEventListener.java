package com.orient.flow.extend.extensions;

import org.jbpm.api.model.Activity;
import org.jbpm.pvm.internal.model.ExecutionImpl;

/**
 * Created by DuanDuanPan on 2016/9/8 0008.
 * 流程判断节点扩展
 */
public interface FlowDecisionEventListener {

    void doDecide(ExecutionImpl execution, Activity activity);
}
