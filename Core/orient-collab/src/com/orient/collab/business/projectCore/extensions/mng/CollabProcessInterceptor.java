package com.orient.collab.business.projectCore.extensions.mng;

import com.orient.collab.business.projectCore.constant.ProcessType;
import com.orient.collab.model.StatefulModel;

/**
 * CollabProcessInterceptor
 *
 * @author Seraph
 *         2016-08-12 下午2:27
 */
public interface CollabProcessInterceptor {

    boolean preHandle(StatefulModel statefulModel, String modelName, ProcessType processType) throws Exception;

    void afterCompletion(StatefulModel statefulModel, String modelName, ProcessType processType, Object processResult) throws Exception;
}
