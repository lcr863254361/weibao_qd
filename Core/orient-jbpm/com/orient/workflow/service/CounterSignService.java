
package com.orient.workflow.service;

import java.util.List;

import com.orient.sysmodel.domain.workflow.JbpmCounterSignInfo;


public interface CounterSignService{

	/**
	 * 通过角色取得用户，去除已经添加的用户
	 * @param roleNames
	 *        角色名称
	 * @param users
	 *        不包括用户名       
	 * @return List
	 */
	public List<String> getRole2Users(String roles, String users);

	/**
	 * 将会签数据保存到扩展表中
	 * @param map
	 *        会签数据
	 * @return 
	 */ 
	public void addCounterSign(JbpmCounterSignInfo info);
}
