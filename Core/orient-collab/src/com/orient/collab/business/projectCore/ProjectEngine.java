package com.orient.collab.business.projectCore;

import com.orient.collab.business.projectCore.exception.CollabFlowControlException;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Project;
import com.orient.collab.model.StatefulModel;
import com.orient.collab.model.Task;
import com.orient.web.base.CommonResponseData;

/**
 * the project engine to control the flow of project or task
 *
 * @author Seraph
 *         2016-07-20 下午4:20
 */
public interface ProjectEngine {

    CommonResponseData startProject(Project project) throws CollabFlowControlException;
    CommonResponseData submitProject(Project project) throws CollabFlowControlException;
    CommonResponseData submitPlan(Plan plan) throws CollabFlowControlException;
    CommonResponseData submitTask(Task task, String transition) throws CollabFlowControlException;
    CommonResponseData startCollabFlow(StatefulModel statefulModel) throws CollabFlowControlException;
    CommonResponseData suspendCollabFlow(StatefulModel statefulModel)throws CollabFlowControlException;
    CommonResponseData resumeCollabFlow(StatefulModel statefulModel) throws CollabFlowControlException;
    CommonResponseData closeCollabFlow(StatefulModel statefulModel) throws CollabFlowControlException;
}
