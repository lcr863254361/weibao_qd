package com.orient.collab.business.projectCore.extensions.interceptors;

import com.orient.collab.business.projectCore.constant.ProcessType;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessInterceptor;
import com.orient.collab.business.projectCore.extensions.mng.CollabProcessMarker;
import com.orient.collab.config.CollabConstants;

/**
 * DeleteFlowExamineInterceptor
 *
 * @author Seraph
 *         2016-08-16 下午3:16
 */
@CollabProcessMarker(order = 1, processType={ProcessType.CLOSE}, models={CollabConstants.PLAN, CollabConstants.TASK})
public class CloseFlowExamineInterceptor extends SuspendFlowExamineInterceptor implements CollabProcessInterceptor {
}
