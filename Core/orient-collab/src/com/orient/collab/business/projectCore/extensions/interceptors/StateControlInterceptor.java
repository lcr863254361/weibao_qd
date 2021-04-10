package com.orient.collab.business.projectCore.extensions.interceptors;

import com.orient.collab.business.projectCore.constant.ProcessType;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessInterceptor;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessMarker;
import com.orient.collab.business.projectCore.strategy.StateControlStrategy;
import com.orient.collab.model.StatefulModel;


/**
 * StateControlInterceptor
 *
 * @author Seraph
 *         2016-08-12 下午3:29
 */
@CollabProcessMarker(order = 0)
public class StateControlInterceptor implements CollabProcessInterceptor {

    @Override
    public boolean preHandle(StatefulModel statefulModel, String modelName, ProcessType processType) throws Exception {

        switch (processType){
            case START:
                StateControlStrategy.fromString(statefulModel.getStatus()).tryStart(statefulModel);
                break;
            case SUBMIT:
                StateControlStrategy.fromString(statefulModel.getStatus()).trySubmit(statefulModel);
                break;
            case SUSPEND:
                StateControlStrategy.fromString(statefulModel.getStatus()).trySuspend(statefulModel);
                break;
            case RESUME:
                StateControlStrategy.fromString(statefulModel.getStatus()).tryResume(statefulModel);
                break;
            case CLOSE:
                StateControlStrategy.fromString(statefulModel.getStatus()).tryDeleteFlow(statefulModel);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void afterCompletion(StatefulModel statefulModel, String modelName, ProcessType processType, Object processResult) throws Exception {

    }
}
