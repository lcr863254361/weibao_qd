
package com.orient.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.orient.sysmodel.domain.workflow.JbpmCounterSignInfo;
import com.orient.sysmodel.domain.workflow.JbpmCounterSignInfoDAO;
import com.orient.sysmodel.operationinterface.IRole;
import com.orient.sysmodel.operationinterface.IRoleModel;
import com.orient.sysmodel.operationinterface.IUser;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.workflow.service.CounterSignService;
import org.springframework.stereotype.Service;

@Service
public class CounterSignServiceImpl implements CounterSignService{
	@Autowired
	private IRoleUtil roleEngine;
	@Autowired
	JbpmCounterSignInfoDAO jbpmCounterSignInfoDAO;
	
	public CounterSignServiceImpl(){
	}

	/**
	 * 通过角色取得用户，去除已经添加的用户
	 * @param roleNames
	 *        角色名称
	 * @param users
	 *        不包括用户名       
	 * @return List
	 */
	public List<String> getRole2Users(String roleNames, String users){
		IRoleModel roleModel = roleEngine.getRoleModel(false);
	    List<String> userList = new ArrayList<String>();
	    String[] roleNameArr = roleNames.split(",");
	    String[] existedUserArr = users.split(",");
	    for(String roleName:roleNameArr){
	    	IRole role = roleModel.getIRoleByName(roleName);
	    	List<IUser> uList = role.getAllValidUsers();
	    	for(IUser user:uList){
	    		boolean  isIn = false;
	    		for(String userName:existedUserArr){
	    			if(user.getUserName().equalsIgnoreCase(userName)){
	    				isIn = true;
	    				break;
	    			}
		    	}
	    		if(!isIn&&!userList.contains(user.getUserName())){
	    			userList.add(user.getUserName());
	    		}    			
    		}
	    	
	    }
	    return userList;
 		 
	}
	
	/**
	 * 将会签数据保存到扩展表中
	 * @param info
	 *        会签数据
	 * @return 
	 */ 
	public void addCounterSign(JbpmCounterSignInfo info) {
		jbpmCounterSignInfoDAO.attachDirty(info); 
	}

	public IRoleUtil getRoleEngine() {
		return roleEngine;
	}

	public void setRoleEngine(IRoleUtil roleEngine) {
		this.roleEngine = roleEngine;
	}

	public JbpmCounterSignInfoDAO getJbpmCounterSignInfoDAO() {
		return jbpmCounterSignInfoDAO;
	}

	public void setJbpmCounterSignInfoDAO(
			JbpmCounterSignInfoDAO jbpmCounterSignInfoDAO) {
		this.jbpmCounterSignInfoDAO = jbpmCounterSignInfoDAO;
	}
		
	
	
}
