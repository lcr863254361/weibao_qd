/**
 * DepartmentService.java
 * com.sysmodel.service.user
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-3-13 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.service.user;

import java.util.List;

import com.orient.sysmodel.domain.user.Department;

/**
 * ClassName:DepartmentService
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-3-13		下午02:19:32
 *
 * @see 	 
 */
public interface DepartmentService {

	/**
	 * 新增部门信息
	
	 * @Method: createDept 
	
	 * TODO
	
	 * @param dept
	
	 * @return void
	
	 * @throws
	 */
	public void createDept(Department dept); 
	
	/**
	 * 更新部门信息
	
	 * @Method: updateDept 
	
	 * TODO
	
	 * @param dept
	
	 * @return void
	
	 * @throws
	 */
	public void updateDept(Department dept);
	
	/**
	 * 删除部门及其子部门
	
	 * @Method: deleteDept 
	
	 * TODO
	
	 * @param dept
	
	 * @return void
	
	 * @throws
	 */
	public void deleteDept(Department dept);
	
	/**
	 * 批量删除部门及其子部门
	
	 * @Method: deleteDept 
	
	 * TODO
	
	 * @param deptIds
	
	 * @return void
	
	 * @throws
	 */
	public void deleteDept(String deptIds);
	
	/**
	 * 查询所有的部门信息
	
	 * @Method: findAll 
	
	 * TODO
	
	 * @return
	
	 * @return List<Department>
	
	 * @throws
	 */
	public List<Department> findAll();
	
	/**
	
	 * @Method: findByPid 
	
	 * TODO 查询子部门信息
	
	 * @return 
	
	 * @see com.orient.sysmodel.service.user.DepartmentService#findByPid() 
	
	 */
	public List<Department> findByPid(String pid);
	
	/**
	 * 取得部门信息
	
	 * @Method: findById 
	
	 * TODO
	
	 * @param id
	 * @return
	
	 * @return Department
	
	 * @throws
	 */
	public Department findById(String id);
	
	/**
	 * 根据部门名称查找部门信息
	
	 * @Method: findByName 
	
	 * TODO
	
	 * @param deptName
	 * @return
	
	 * @return Department
	
	 * @throws
	 */
	public Department findByName(String deptName);
	
}

