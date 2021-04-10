package com.orient.sysmodel.service.user;

import java.util.List;

import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.domain.user.UserColumn;

public interface UserColumnService {
	
	/**
	 * 查找用户表所有字段信息
	
	 * @Method: findAllUserColumn 
	
	 * TODO
	
	 * @return
	
	 * @return List<UserColumn>
	
	 * @throws
	 */
	public List<UserColumn> findAllUserColumn();
	
	/**
	 * 根据用户字段名查找用户字段信息
	
	 * @Method: findByUserColumnName 
	
	 * TODO
	
	 * @param userColumnName
	 * @return
	
	 * @return UserColumn
	
	 * @throws
	 */
	public UserColumn findByUserColumnName(String userColumnName);
	
	/**
	 * 查找所有做为检索条件的用户信息字段
	
	 * @Method: findUserColumnForSearch 
	
	 * TODO
	
	 * @return
	
	 * @return List<UserColumn>
	
	 * @throws
	 */
	public List<UserColumn> findUserColumnForSearch();

	/**
	 * 查找所有做为查询条件的用户信息字段
	
	 * @Method: findUserColumnForInfoSearch 
	
	 * TODO
	
	 * @return
	
	 * @return List<UserColumn>
	
	 * @throws
	 */
	public List<UserColumn> findUserColumnForInfoSearch();
	
	/**
	 * 查找所有做为列表显示的用户信息字段
	
	 * @Method: findUserColumnForDispalyShow 
	
	 * TODO
	
	 * @return
	
	 * @return List<UserColumn>
	
	 * @throws
	 */
	public List<UserColumn> findUserColumnForDispalyShow();
	
	/**
	 * 查找所有做为明细显示的用户信息字段
	
	 * @Method: findUserColumnForViewShow 
	
	 * TODO
	
	 * @return
	
	 * @return List<UserColumn>
	
	 * @throws
	 */
	public List<UserColumn> findUserColumnForViewShow();

}
