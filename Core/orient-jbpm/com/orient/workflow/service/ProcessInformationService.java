package com.orient.workflow.service;

import com.orient.workflow.bean.AgencyWorkflow;
import com.orient.workflow.bean.FlowInfo;
import org.jbpm.api.Execution;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;

import java.util.List;

/**
 * @ClassName ProcessInformationService 流程定义的服务对象
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-1
 */

public interface ProcessInformationService {

	/**
	 * 返回所有的流程定义列表
	 * 
	 * @Method: getAllProcessDefinitionKeys
	 * @return
	 */
	public abstract List<ProcessDefinition> getAllProcessDefinitionKeys();

	/**
	 * 返回所有正在执行的流程实例
	 * 
	 * @Method: getAllOpenExecutions
	 * @return
	 */
	public abstract List<ProcessInstance> getAllOpenExecutions();

	/**
	 * 获取用户所能发起的所有流程
	 * 
	 * @Method: getUserAllProcess
	 * @param userId
	 *            用户登录ID
	 * @param roleName
	 *            用户角色名称,多個角色用逗号分隔
	 * @return 用户能够访问的流程定义对象
	 */
	public List<ProcessDefinition> getUserAllProcess(String userId,
			String roleName);

	/**
	 * 如果自己被指定成代办人员，则需要得到指定人的所有流程
	 * 
	 * @Method: getConfigUserAllProcess
	 * @param userId
	 *            用户ID
	 * @param flag
	 *            是否获取代办的流程启动
	 * @return
	 * @see com.orient.workflow.service.ProcessInformationService#getConfigUserAllProcess(java.lang.String,
	 *      java.lang.Boolean)
	 */ 
	public List<AgencyWorkflow> getConfigUserAllProcess(String userId, Boolean flag);

	/**
	 * 保存流程运行过程中的变量
	 * 
	 * @Method: saveTaskVar
	 * @param execution
	 *            执行环境
	 * @param key
	 *            名
	 * @param value
	 *            值
	 * @see com.orient.workflow.service.ProcessInformationService#saveTaskVar(org.jbpm.api.Execution,
	 *      java.lang.String, java.lang.String)
	 */
	public void saveTaskVar(final Execution execution, String key, String value);
	
	@Deprecated
	public ProcessDefinition getProcessDefinitionByProjectIdOrTaskId(boolean isProject,String id, String prjId);

	public List<FlowInfo> getMainAndSubPIs(String piId);

	String getProcessDefinitionIdByHisPiId(String processInstanceId);
}