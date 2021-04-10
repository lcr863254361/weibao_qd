/**
 * 
 */
package com.orient.flow.business;

import java.util.Collection;
import java.util.Map;

import com.orient.flow.util.FlowCommissionHelper;
import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import com.orient.workflow.bean.AssignUser;
import com.orient.workflow.bean.JBPMInfo;
import com.orient.flow.extend.activity.EdmStartActivity;

/**
 * 流程执行相关的业务类
 *
 * <p>detailed commentFlowExecutionBusiness</p>
 * @author [创建人]  mengbin <br/> 
 * 		   [创建时间] 2016-1-12 上午11:26:42 <br/> 
 * 		   [修改人] mengbin <br/>
 * 		   [修改时间] 2016-1-12 上午11:26:42
 * @see
 */
@Repository
public class FlowExecutionBusiness  extends BaseBusiness{

	
	@Autowired
	protected ProcessEngine processEngine;

	@Autowired
	protected FlowCommissionHelper flowCommissionHelper;
	
	/**
	 * 根据任务获取流程执行实例
	 * @author [创建人]  cxk <br/> 
	 * 		   [创建时间] 2014-6-14 上午10:41:31 <br/> 
	 * 		   [修改人] cxk <br/>
	 * 		   [修改时间] 2014-6-14 上午10:41:31
	 * @param task
	 * @return
	 * @see
	 */
	public Execution getExecutionByTask(Task task){
		return processEngine.getExecutionService().findExecutionById(task.getExecutionId());
	}
	
	
	/**
	 * 根据流程任务Id获取执行Id，该流程任务Id可以是历史任务Id，
	 * 该方法主要用于历史任务来获取未完成的流程
	 *
	 * <p>getExecutionIdByFlowTaskId</p>
	 * @author [创建人]  mengbin <br/> 
	 * 		   [创建时间] 2016-1-12 下午01:44:29 <br/> 
	 * 		   [修改人] mengbin <br/>
	 * 		   [修改时间] 2016-1-12 下午01:44:29
	 * @param flowTaskId
	 * @return
	 * String
	 * @see
	 */
	public String getExecutionIdByFlowTaskId(String flowTaskId) {
		HistoryService HisSvc = processEngine.getHistoryService();
		HistoryTask historyTask = HisSvc.createHistoryTaskQuery()
				.taskId(flowTaskId).uniqueResult();
		String executionId = historyTask.getExecutionId();
		return executionId;
	}
	
	
	/**
	 * start a process instance by the given process definition id.
	 *
	 * @author [创建人]  spf <br/> 
	 * 		   [创建时间] 2014-8-5 下午3:14:25 <br/> 
	 * 		   [修改人] spf <br/>
	 * 		   [修改时间] 2014-8-5 下午3:14:25
	 * @param pdId
	 * @param taskAssignMap: 节点的执行人Map,可以为null
	 * @param customStartTransition: 启动后走的路径,如果不指定,则设置为null
	 * @return the process instance started
	 * @see
	 */
	public ProcessInstance startPrcInstanceByPrcDefId(String pdId, Map<String,AssignUser> taskAssignMap,String customStartTransition, Map<String, Object> flowVariables){
		//流程实例处理Service
		ExecutionService executionService = processEngine.getExecutionService();
		// 根据流程定义id启动实例
		Map<String, Object> variables = UtilFactory.newHashMap();
		if(customStartTransition != null && !customStartTransition.equals("")){
			variables.put(EdmStartActivity.START_TRANSITION, customStartTransition);
		}		
		if(taskAssignMap != null){
			taskAssignMap = flowCommissionHelper.reconfigTaskAssignMap(pdId, taskAssignMap);
			variables.put(JBPMInfo.DynamicUserAssign, taskAssignMap);
		}
		if(flowVariables != null){
			variables.putAll(flowVariables);
		}
		ProcessInstance processInstance = executionService.startProcessInstanceById(pdId, variables);
		return processInstance;
	}
	 
	

	/**
	 *暂停流程
	 *
	 * <p>suspendPrcInstByPrcInstId</p>
	 * @author [创建人]  mengbin <br/> 
	 * 		   [创建时间] 2016-1-12 上午11:43:50 <br/> 
	 * 		   [修改人] mengbin <br/>
	 * 		   [修改时间] 2016-1-12 上午11:43:50
	 * @param piId
	 * @return
	 * Boolean
	 * @see
	 */
	public Boolean suspendPrcInstByPrcInstId(String piId){
		try{
			ExecutionService executionService = processEngine.getExecutionService();
			Execution mainExecution = executionService.findExecutionById(piId);
			if(mainExecution!=null){//current
				suspendExecutionCascade((ExecutionImpl)mainExecution);
			}
		}catch(Exception ex){
			return false;
		}
		return true;
	}
	
	/**
	 * 暂停流程的子流程，主要针对subflow，会签等节点产生的子流程。
	 *
	 * <p>suspendExecutionCascade</p>
	 * @author [创建人]  mengbin <br/> 
	 * 		   [创建时间] 2016-1-12 上午11:44:54 <br/> 
	 * 		   [修改人] mengbin <br/>
	 * 		   [修改时间] 2016-1-12 上午11:44:54
	 * @param execution
	 * void
	 * @see
	 */
	private void suspendExecutionCascade(ExecutionImpl execution){
		Collection<ExecutionImpl> childExecutions = execution.getExecutions();
	    if (childExecutions!=null) {
	      for (ExecutionImpl childExecution: childExecutions) {
	    	  suspendExecutionCascade(childExecution);
	    	  childExecution.suspend();
	      }
	    }
	    execution.suspend();
	}
	
	/**
	 * end a process instance's execution
	 *
	 * @author [创建人]  spf <br/> 
	 * 		   [创建时间] 2014-7-23 上午9:41:38 <br/> 
	 * 		   [修改人] spf <br/>
	 * 		   [修改时间] 2014-7-23 上午9:41:38
	 * @param piId
	 * @see
	 */
	public Boolean endProcessInstance(String piId){
		Boolean retVal = false;
		ExecutionService executionService = processEngine.getExecutionService();
		ProcessInstance pi = executionService
				.createProcessInstanceQuery().processInstanceId(piId).uniqueResult();
		if(pi!=null && !pi.isEnded()){
			executionService.endProcessInstance(piId, Execution.STATE_ENDED);
			retVal = true;
		}
		return retVal;
	}
	
	
	/**
	 * 删除流程实例
	 *
	 * <p>deleteProcessInstance</p>
	 * @author [创建人]  mengbin <br/> 
	 * 		   [创建时间] 2016-1-20 下午10:08:04 <br/> 
	 * 		   [修改人] mengbin <br/>
	 * 		   [修改时间] 2016-1-20 下午10:08:04
	 * @param piId
	 * @return
	 * Boolean
	 * @see
	 */
	public Boolean deleteProcessInstance(String piId){
		Boolean retVal = false;
		ExecutionService executionService = processEngine.getExecutionService();
		ProcessInstance pi = executionService
				.createProcessInstanceQuery().processInstanceId(piId).uniqueResult();
		if(pi!=null){
			executionService.deleteProcessInstanceCascade(piId);
			retVal = true;
		}
		return retVal;
	}
	
	/**
	 * 恢复流程
	 * 
	 * @author [创建人]  spf <br/> 
	 * 		   [创建时间] 2014-7-4 上午10:35:31 <br/> 
	 * 		   [修改人] spf <br/>
	 * 		   [修改时间] 2014-7-4 上午10:35:31
	 * @param piId
	 * @return
	 * @see
	 */
	public Boolean resumePrcInstByPrcInstId(String piId){
		try{
			ExecutionService executionService = processEngine.getExecutionService();
			Execution mainExecution = executionService.findExecutionById(piId);
			if(mainExecution!=null){//current
				resumeExecutionCascade((ExecutionImpl)mainExecution);
			}
		}catch(Exception ex){
			return false;
		}
		return true;

	}
	
	private void resumeExecutionCascade(ExecutionImpl execution){
		Collection<ExecutionImpl> childExecutions = execution.getExecutions();
	    if (childExecutions!=null) {
	      for (ExecutionImpl childExecution: childExecutions) {
	    	  suspendExecutionCascade(childExecution);
	    	  childExecution.resume();
	      }
	    }
	    execution.resume();
	}
	
}
