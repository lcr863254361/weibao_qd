/**
 * IRoleModel.java
 * com.orient.sysmodel.domain.role
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2012-4-16 		zhang yan
 * <p>
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.orient.sysmodel.operationinterface;

import com.orient.sysmodel.domain.role.Function;
import com.orient.sysmodel.domain.role.Operation;
import com.orient.sysmodel.domain.role.Role;
import com.orient.sysmodel.domain.tbom.Tbom;
import com.orient.sysmodel.domain.tbom.TbomDir;
import com.orient.sysmodel.domain.user.Department;
import com.orient.sysmodel.domain.user.User;

import java.util.List;
import java.util.Map;

/**
 * ClassName:IRoleModel
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author zhang yan
 * @version
 * @since Ver 1.1
 * @Date 2012-4-16		上午11:18:02
 *
 * @see
 */
public interface IRoleModel {

    /**
     *

     * @Method: getRoles

     * 取得所有的角色信息

     * @return

     * @return Map<String,Role>

     * @throws
     */
    public abstract Map<String, Role> getRoles();

    /**
     *

     * @Method: getRoleById

     * 根据角色id取得角色信息

     * @param roleId
     * @return

     * @return Role

     * @throws
     */
    public abstract Role getRoleById(String roleId);

    /**
     *

     * @Method: getIRoleById

     * 根据角色id取得角色操作接口

     * @param roleId
     * @return

     * @return IRole

     * @throws
     */
    public abstract IRole getIRoleById(String roleId);

    /**
     *

     * @Method: getRoleByName

     * 根据角色名称取得角色信息

     * @param roleName
     * @return

     * @return Role

     * @throws
     */
    public abstract Role getRoleByName(String roleName);

    /**
     *

     * @Method: getIRoleByName

     * 根据角色名称取得角色操作接口

     * @param roleName
     * @return

     * @return IRole

     * @throws
     */
    public abstract IRole getIRoleByName(String roleName);

    /**
     *

     * @Method: getRolesOfUser

     * 返回用户所属的角色列表

     * @param userId
     * @return

     * @return List<IRole>

     * @throws
     */
    public abstract List<IRole> getRolesOfUser(String userId);


    /**
     * 返回用户实例
     * @Method: getUserById
     * @param userId 用户ID
     * @return
     */
    public IUser getUserById(String userId);

    /**
     * 返回用户实例
     * @Method: getUserByUserName
     * @param username 用户登陆名
     * @return
     */
    public IUser getUserByUserName(String username);


    public List<ITbom> getStaticTboms();

    public Map<String, Function> getFunctions();

    public Map<String, User> getUsers();

    public Map<Integer, Department> getDepartments();

    public Map<String, Operation> getOperations();

    public Map<String, TbomDir> getTbomDirs();

    public void initRoleTboms();

    public void deleteTbom(TbomDir tbomDir);

    public void freshTbomRoot(TbomDir tbomDir, Tbom rootNode);

    /**
     * 获取Tbom节点信息
     * @param treeId TBOM id
     */
    ITbom getTbomById(String treeId);

    List<ITbom> getTbomRoots();
}
