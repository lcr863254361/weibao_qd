package com.orient.flow.extend.extensions.userDefine;

import com.orient.flow.config.FlowType;
import com.orient.flow.extend.annotation.CommentInfo;
import com.orient.flow.extend.annotation.FlowTaskExecutionEventMarker;
import com.orient.flow.extend.extensions.FlowTaskExecutionEventListener;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

import static com.orient.flow.extend.extensions.FlowTaskExecutionEventListener.FLOW_END;
import static com.orient.flow.extend.extensions.FlowTaskExecutionEventListener.FLOW_START;

@CommentInfo(displayName = "CallTaskComponet", allowSelect = false)
@FlowTaskExecutionEventMarker(exceptTasks = {FLOW_START, FLOW_END,FlowTaskExecutionEventListener.FLOW_END_ERROR}, flowTypes = {FlowType.Collab})
public class CallTaskComponet implements FlowTaskExecutionEventListener{

	public void process(ExecutionImpl execution, String activityName) {
//		ProcessDefinition pd = execution.getProcessDefinition();
//		//String activityName = task.getActivityName();
//		//保存任务的变量
//		CollabFlowTaskBusiness collabFlowTaskBusiness = (CollabFlowTaskBusiness) OrientContextLoaderListener.Appwac.getBean("collabFlowTaskBusiness");
//		CollabFlowTaskDAO collabFlowTaskDao = (CollabFlowTaskDAO) OrientContextLoaderListener.Appwac.getBean("collabFlowTaskDAO");
//		ProjectDAO projectDao = (ProjectDAO) OrientContextLoaderListener.Appwac.getBean("projectDAO");
//		String orientTaskId = collabFlowTaskBusiness.getOrientTaskIdByPrcDefIdAndFlowTaskName(pd.getId(), activityName);
//		String hisFlowTaskId = collabFlowTaskDao.getFlowHisTaskIdByHisActivityName_ExecId(activityName, execution.getId());
//		String projId = projectDao.getProjectIdByTaskId(orientTaskId);
//
//		//获取该任务的组件信息
//		ComponentFlowDataBusiness componentFlowDataBusiness = (ComponentFlowDataBusiness)OrientContextLoaderListener.Appwac.getBean("componentFlowDataBusiness");;
//		//获取组件的的类名:测试环境中从配置文件中获取，真实环境从数据库中获取
//			try{
//				String className = componentFlowDataBusiness.getFlowTaskComponent(orientTaskId);
//				if("".equals(className)||className==null)
//				{
//					return ;
//				}
//				Class<?> componentInstance = Class.forName(className);
//				ComponentInterface  instance = (ComponentInterface) componentInstance.newInstance();
//				String josnString = ComponentDataManager.getComponentData(hisFlowTaskId);
//				String dashboardparams = ComponentDataManager.getComponentDashboardParam(hisFlowTaskId);
//				if(!CommonTools.isNullString(josnString))
//				{
//					instance.submitComponent(josnString, projId);
//				}
//				if(!CommonTools.isNullString(dashboardparams))
//				{
//					ComponentDashboardBusiness dashboardBusiness = (ComponentDashboardBusiness) OrientContextLoaderListener.Appwac.getBean("componentDashBoardBusiness");
//					dashboardBusiness.setDashborardParams(orientTaskId, projId, dashboardparams);
//
//				//	instance.submitDashboardParam(dashboardparams, projId, orientTaskId);
//				}
//
//
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
		
		
	}

	@Override
	public void triggered(ExecutionImpl execution, TaskImpl task, TaskDefinitionImpl taskDefinition) {

	}

	@Override
	public void left(ExecutionImpl execution, String activityName, TaskDefinitionImpl taskDefinition, String signalName) {
		process(execution, activityName);
	}
}
