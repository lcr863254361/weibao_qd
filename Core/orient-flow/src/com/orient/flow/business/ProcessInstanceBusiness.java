/**
 * 
 */
package com.orient.flow.business;

import com.orient.sysmodel.dao.flow.ProcessInstanceDAO;
import com.orient.web.base.BaseBusiness;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.jbpm.api.*;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jbpm.api.history.HistoryProcessInstanceQuery.PROPERTY_STARTTIME;

/**
 * 主要处理流程实例和历史流程实例的业务
 *
 * <p>detailed commentProcessInstanceBusiness</p>
 * @author [创建人]  mengbin <br/> 
 * 		   [创建时间] 2016-1-12 下午01:48:22 <br/> 
 * 		   [修改人] mengbin <br/>
 * 		   [修改时间] 2016-1-12 下午01:48:22
 * @see
 */

@Repository
public class ProcessInstanceBusiness extends BaseBusiness {

	@Autowired
	protected ProcessEngine processEngine;
	
	@Autowired
	protected ProcessInstanceDAO processInstanceDao;

	public HistoryProcessInstance getLatestProcessInstanceByPdId(String pdId){
		List<HistoryProcessInstance> historyProcessInstanceList = processEngine.getHistoryService().createHistoryProcessInstanceQuery().processDefinitionId(pdId)
				.orderDesc(PROPERTY_STARTTIME).list();
		if(historyProcessInstanceList.size() == 0){
			return null;
		}

		return historyProcessInstanceList.get(0);
	}

	public ProcessInstance getLatestActiveProcessInstanceByPdId(String pdId){
		List<HistoryProcessInstance> historyProcessInstanceList = processEngine.getHistoryService().createHistoryProcessInstanceQuery().processDefinitionId(pdId)
				.state(HistoryProcessInstance.STATE_ACTIVE).orderDesc(PROPERTY_STARTTIME).list();
		if(historyProcessInstanceList.size() == 0){
			return null;
		}

		return this.getCurrentProcessInstanceById(historyProcessInstanceList.get(0).getProcessInstanceId());
	}

	/**
	 * 根据流程实例Id获取流程实例
	 *
	 * <p>getCurrentProcessInstanceById</p>
	 * @author [创建人]  mengbin <br/> 
	 * 		   [创建时间] 2016-1-12 下午01:51:54 <br/> 
	 * 		   [修改人] mengbin <br/>
	 * 		   [修改时间] 2016-1-12 下午01:51:54
	 * @param piId
	 * @return
	 * ProcessInstance
	 * @see
	 */
	public ProcessInstance getCurrentProcessInstanceById(String piId){
		return processEngine.getExecutionService().createProcessInstanceQuery().processInstanceId(piId)
				.uniqueResult();
	}
	
	/**
	 * 根据流程实例id获取历史流程实例
	 *
	 * <p>getHistoryProcessInstanceById</p>
	 * @author [创建人]  mengbin <br/> 
	 * 		   [创建时间] 2016-1-12 下午01:52:18 <br/> 
	 * 		   [修改人] mengbin <br/>
	 * 		   [修改时间] 2016-1-12 下午01:52:18
	 * @param piId
	 * @return
	 * HistoryProcessInstance
	 * @see
	 */
	public HistoryProcessInstance getHistoryProcessInstanceById(String piId){
		return processEngine.getHistoryService().createHistoryProcessInstanceQuery().processInstanceId(piId)
				.uniqueResult();
	}
	
	/**
	 * 根据当前的流程任务，获取其根的流程实例Id。
	 * 该方法中递归调用了查找父流程的executionId，主要考虑了会签任务，分支任务等。
	 *
	 * <p>getPrcInstIdByCurFlowTask</p>
	 * @author [创建人]  mengbin <br/> 
	 * 		   [创建时间] 2016-1-12 下午01:57:52 <br/> 
	 * 		   [修改人] mengbin <br/>
	 * 		   [修改时间] 2016-1-12 下午01:57:52
	 * @param curFlowTask
	 * @return
	 * String
	 * @see
	 */
	public String getPrcInstIdByCurFlowTask(Task curFlowTask) {
		return this.getProcessInstanceByCurFlowTask(curFlowTask).getId();
	}

	public String getPrcInstIdByCurFlowTask(String taskId){
		Task task = processEngine.getTaskService().getTask(taskId);
		if(null != task){
			return this.getPrcInstIdByCurFlowTask(task);
		}else
			throw new OrientBaseAjaxException("","未找到相关任务");
	}
	
	/**
	 * 根据当前的流程任务，获取其根的流程的流程执行。
	 * 该方法中递归调用了查找父流程的executionId，主要考虑了会签任务，分支任务等。
	 *	
	 * <p>getProcessInstanceByCurFlowTask</p>
	 * @author [创建人]  mengbin <br/> 
	 * 		   [创建时间] 2016-1-12 下午02:00:01 <br/> 
	 * 		   [修改人] mengbin <br/>
	 * 		   [修改时间] 2016-1-12 下午02:00:01
	 * @param curFlowTask
	 * @return
	 * Execution 
	 * @see
	 */
	public Execution getProcessInstanceByCurFlowTask(Task curFlowTask){
		ExecutionService executionSvc = processEngine.getExecutionService();
		Execution execution = executionSvc.findExecutionById(curFlowTask
					.getExecutionId());
		return getProcessInstanceByCurExecution(execution);
	}
	
	/**
	 * 该方法中递归调用了查找父流程，主要考虑了会签任务，分支任务等。
	 * 返回的是根的Execution
	 *
	 * <p>getProcessInstanceByCurExecution</p>
	 * @author [创建人]  mengbin <br/> 
	 * 		   [创建时间] 2016-1-12 下午02:01:01 <br/> 
	 * 		   [修改人] mengbin <br/>
	 * 		   [修改时间] 2016-1-12 下午02:01:01
	 * @param execution
	 * @return
	 * Execution
	 * @see
	 */
	public Execution getProcessInstanceByCurExecution(Execution execution){
		if (execution == null) {
			return null;
		}
		while (true) {
			Execution parentExecution = execution.getParent();
			if (parentExecution == null) {
				return execution;
			} else {
				execution = parentExecution;
			}
		}
	}


	/**
	 * 根据历史任务Id，获取流程实例Id，
	 * 该流程实例的Id为主流程的实例Id。
	 * 该方法支持子流程已经完成，主流程还未完成的情况。
	 * <p>getPrcInstIdByHisFlowTaskId</p>
	 * @author [创建人]  mengbin <br/> 
	 * 		   [创建时间] 2016-1-12 下午02:06:44 <br/> 
	 * 		   [修改人] mengbin <br/>
	 * 		   [修改时间] 2016-1-12 下午02:06:44
	 * @param hisFlowTaskId
	 * @return
	 * String
	 * @see
	 */
	public String getPrcInstIdByHisFlowTaskId(String hisFlowTaskId) {
		String executionId = getExecutionIdByFlowTaskId(hisFlowTaskId);
		return processInstanceDao.getPrcInstIdByHisExecution(executionId);
	}

	/**
	 * 根据历史任务Id，获取其ExecutionId。
	 * 
	 * <p>getExecutionIdByFlowTaskId</p>
	 * @author [创建人]  mengbin <br/> 
	 * 		   [创建时间] 2016-1-12 下午02:33:10 <br/> 
	 * 		   [修改人] mengbin <br/>
	 * 		   [修改时间] 2016-1-12 下午02:33:10
	 * @param hisFlowTaskId
	 * @return
	 * String
	 * @see
	 */
	public String getExecutionIdByFlowTaskId(String hisFlowTaskId) {
		HistoryService HisSvc = processEngine.getHistoryService();
		HistoryTask historyTask = HisSvc.createHistoryTaskQuery()
				.taskId(hisFlowTaskId).uniqueResult();
		String executionId = historyTask.getExecutionId();
		return executionId;
	}

	/**
	 * getPrcInstIdByHisExecutionId
	 * @param hisExecutionId
	 * @return
	 */
	public String getPrcInstIdByHisExecutionId(String hisExecutionId) {
		return processInstanceDao.getPrcInstIdByHisExecution(hisExecutionId);
	}

}
