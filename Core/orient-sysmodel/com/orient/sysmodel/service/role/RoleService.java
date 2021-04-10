/**
 * RoleService.java
 * com.sysmodel.service.role
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-3-16 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.service.role;

import com.orient.metamodel.metadomain.Schema;
import com.orient.sysmodel.domain.arith.Arith;
import com.orient.sysmodel.domain.form.ModelBtnTypeEntity;
import com.orient.sysmodel.domain.role.Role;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.operationinterface.IOperation;
import com.orient.sysmodel.operationinterface.IOverAllOperations;

import java.util.List;

/**
 * ClassName:RoleService
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-3-16		下午02:55:52
 *
 * @see 	 
 */
public interface RoleService {

	/**
	 * 查找所有的角色
	 *  @Enclosing_Method  : findAll
	 *  @Version           : v1.00
	 *
	 */
	public List<Role> findAll();
	
	/**
	 * 查找所有的角色用户显示
	 *  @Enclosing_Method  : findAllForShow
	 *  @Version           : v1.00
	 *
	 */
	public List<Role> findAllForShow();
	
	/**
	 * 查找角色信息
	
	 * @Method: findById 
	
	 * TODO
	
	 * @param roleId
	 * @return
	
	 * @return Role
	
	 * @throws
	 */
	public Role findById(String roleId);
	
	/**
	 * 按角色名称查找角色信息
	
	 * @Method: findByRoleName 
	
	 * TODO
	
	 * @param roleName
	 * @return
	
	 * @return Role
	
	 * @throws
	 */
	public Role findByRoleName(String roleName);

	public List findByExampleLike(Role instance);
	
	/**
	 * 保存角色信息,并初始化角色权限
	
	 * @Method: createRole 
	
	 * TODO
	
	 * @param role
	
	 * @return void
	
	 * @throws
	 */
	public void createRole(Role role);
	
	/**
	 * 更新角色信息
	
	 * @Method: updateRole 
	
	 * TODO
	
	 * @param role
	
	 * @return void
	
	 * @throws
	 */
	public void updateRole(Role role);
	
	/**
	 * 删除角色,及其关联权限信息
	
	 * @Method: deleteRole 
	
	 * TODO
	
	 * @param roleIds
	
	 * @return void
	
	 * @throws
	 */
	public void deleteRole(String[] roleIds);
	
	/**
	 * 根据名称模糊查找角色信息
	
	 * @Method: queryRoleList 
	
	 * TODO
	
	 * @param roleName
	 * @return
	
	 * @return List<Role>
	
	 * @throws
	 */
	public List<Role> queryRoleList(String roleName);
	
	/**
	 * 取得角色的用户信息(不包括特殊权限用户)
	
	 * @Method: getRoleUsers 
	
	 * TODO
	
	 * @param roleIds
	 * @return
	
	 * @return List<User>
	
	 * @throws
	 */
	public List<User> getRoleUsers(String roleIds);
	
	/**
	 * 查找没有分配给指定角色的算法
	
	 * @Method: getArithNotAssignedRoleId 
	
	 * TODO
	
	 * @param roleId
	 * @param arithName
	 * @return
	
	 * @return List<Arith> 
	
	 * @throws
	 */
	public List<Arith> getArithNotAssignedRoleId(String roleId, String arithName);
	
	/**
	 * 查找已经分配给指定角色的算法
	
	 * @Method: getArithAssignedRoleId 
	
	 * TODO
	
	 * @param roleId
	 * @param arithName
	 * @return
	
	 * @return List<Arith> 
	
	 * @throws
	 */
	public List<Arith> getArithAssignedRoleId(String roleId, String arithName);
	
	/**
	 * 保存角色算法信息
	
	 * @Method: createRoleAriths 
	
	 * TODO
	
	 * @param roleId
	 * @param arithIds
	
	 * @return void
	
	 * @throws
	 */
	public void createRoleAriths(String roleId, String arithIds);
	
	/**
	 * 删除角色算法信息
	
	 * @Method: deleteRoleAriths 
	
	 * TODO
	
	 * @param roleId
	 * @param arithIds
	
	 * @return void
	
	 * @throws
	 */
	public void deleteRoleAriths(String roleId, String arithIds);

	public List<Schema> getAssignedSchemasByRoleId(String roleId);
	public List<Schema> getUnassignedSchemasByRoleId(String roleId);
	public void createRoleSchemas(String roleId, String schemaIds);
	public void deleteRoleSchemas(String roleId, String schemaIds);
	
	/**
	 * 查找已经分配给指定角色的用户
	
	 * @Method: getAssignedUsersByRoleId
	
	 * TODO
	
	 * @param roleId
	 * @return
	
	 * @return List<Map> ID, USERNAME, ALLNAME
	
	 * @throws
	 */
	public List<User> getAssignedUsersByRoleId(String roleId,String name, String department);
	
	/**
	 *  查找未分配给指定角色的用户
	
	 * @Method: getUsersNotAssignedRoleId 
	
	 * TODO
	
	 * @param roleId
	 * @param
	 * @return
	
	 * @return List<Map>
	
	 * @throws
	 */
	public List<User> getUnassignedUsersByRoleId(String roleId, String name, String department);
	
	/**
	 * 保存角色分配的用户信息
	
	 * @Method: createRoleUsers 
	
	 * TODO
	
	 * @param roleId
	 * @param userIds 用户的Id集合,用","号隔开
	
	 * @return void 
	
	 * @throws
	 */
	public void createRoleUsers(String roleId, String userIds);
	
	/**
	 * 删除角色用户信息
	
	 * @Method: deleteRoleUsers 
	
	 * TODO
	
	 * @param roleId
	 * @param userIds
	
	 * @return void
	
	 * @throws
	 */
	public void deleteRoleUsers(String roleId, String userIds);

	public List<ModelBtnTypeEntity> getBtnTypesByRoleId(String roleId,Boolean assigned);

	
	/**
	 * 更新角色操作权限信息
	
	 * @Method: updateOverAllOperations 
	
	 * TODO
	
	 * @param roleId
	 * @param operationIds
	
	 * @return void
	
	 * @throws
	 */
	public void updateOverAllOperations(String roleId, String operationIds);
	
	/**
	 * 保存分配的业务功能点信息操作
	
	 * @Method: updateRoleFunctions 
	
	 * TODO
	
	 * @param roleId
	 * @param funIds 功能点和Tbom树功能的Id集合
	 * @return
	
	 * @return String 功能点的id集合
	
	 * @throws
	 */
	public String updateRoleFunctions(String roleId, String[] funIds);
	
	List<IOperation> getOperations(IOverAllOperations iOverAllOperation);
}

