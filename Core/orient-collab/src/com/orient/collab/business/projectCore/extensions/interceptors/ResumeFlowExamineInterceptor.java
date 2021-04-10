package com.orient.collab.business.projectCore.extensions.interceptors;

import com.orient.collab.business.projectCore.constant.ProcessType;
import com.orient.collab.business.projectCore.exception.CollabFlowControlException;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessInterceptor;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessMarker;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.Plan;
import com.orient.collab.model.StatefulModel;
import com.orient.collab.model.Task;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.CommonTools;

import static com.orient.collab.config.CollabConstants.PLAN;
import static com.orient.collab.config.CollabConstants.STATUS_SUSPENDED;
import static com.orient.collab.config.CollabConstants.TASK;

/**
 * check when resume a collab flow
 *
 * @author Seraph
 *         2016-08-16 上午9:07
 */
@CollabProcessMarker(order = 1, processType={ProcessType.RESUME}, models={CollabConstants.PLAN, TASK})
public class ResumeFlowExamineInterceptor implements CollabProcessInterceptor {

    @Override
    public boolean preHandle(StatefulModel statefulModel, String modelName, ProcessType processType) throws Exception {
        if(PLAN.equals(modelName)){
            return true;
        }

        ISqlEngine sqlEngine = OrientContextLoaderListener.Appwac.getBean(ISqlEngine.class);
        Task task = (Task) statefulModel;

        String parState;
        if(!CommonTools.isNullString(task.getParPlanId())){
            Plan parPlan = sqlEngine.getTypeMappingBmService().getById(Plan.class, task.getParPlanId());
            parState = parPlan.getStatus();
        }else{
            Task parTask = sqlEngine.getTypeMappingBmService().getById(Task.class, task.getParTaskId());
            parState = parTask.getStatus();
        }

        if(STATUS_SUSPENDED.equals(parState)){
            throw new CollabFlowControlException("无法恢复,因父节点已暂停");
        }
        return true;
    }

    @Override
    public void afterCompletion(StatefulModel statefulModel, String modelName, ProcessType processType, Object processResult) throws Exception {

    }
}
