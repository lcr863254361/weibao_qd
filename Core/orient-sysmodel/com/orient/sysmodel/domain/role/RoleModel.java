/**
 * RoleModel.java
 * com.orient.sysmodel.domain.role
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2012-4-1 		zhang yan
 * <p>
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.orient.sysmodel.domain.role;

import com.orient.sysmodel.domain.tbom.Tbom;
import com.orient.sysmodel.domain.tbom.TbomDir;
import com.orient.sysmodel.domain.user.Department;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.operationinterface.*;

import java.util.*;

//import com.google.gwt.dev.util.collect.HashSet;

/**
 * ClassName:RoleModel
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author zhang yan
 * @Date 2012-4-1		下午03:55:13
 * @see
 * @since Ver 1.1
 */
@SuppressWarnings("unchecked")
public class RoleModel extends com.orient.sysmodel.domain.BaseBean implements IRoleModel {


    /**
     * 功能点
     */
    Map<String, Function> functions;

    /**
     * 用户
     */
    Map<String, User> users;

    /**
     * 角色
     */
    Map<String, Role> roles;

    /**
     * 部门
     */
    Map<Integer, Department> departments;

    /**
     * 操作
     */
    Map<String, Operation> operations;

    /**
     * Tbom树目录
     */
    Map<String, TbomDir> tbomDirs;

    /**
     * Tbom树根节点
     */
    List<ITbom> tbomRoots;


    List<ITbom> staticTboms;


    /**
     * 初始化缓存数据
     */
    public void initCache() {
        roles = this.getRoleDAOFactory().getRoleDAO().findAllRoles();

        functions = this.getRoleDAOFactory().getFunctionDAO().findAllFuns();

        operations = this.getRoleDAOFactory().getOperationDAO().findAllOperations();

        users = this.getUserDAOFactory().getUserDAO().findAllUsers();

        departments = this.getUserDAOFactory().getDepartDAO().findAllDeparts();

        tbomDirs = this.getTbomDAOFactory().getTbomDirDAO().findAllTboms();

        tbomRoots = this.getTbomDAOFactory().getTbomDAO().findTbomRoots();


        for (Iterator<String> it2Users = users.keySet().iterator(); it2Users.hasNext(); ) {
            User user = users.get(it2Users.next());

            if (user.getDept() != null) {
                Department dept = departments.get(Integer.valueOf(user.getDept().getId()));
                user.setDept(dept);

                dept.deleteUser(user);
                dept.getUsers().add(user);
            }

        }

        for (Iterator<String> it2Roles = roles.keySet().iterator(); it2Roles.hasNext(); ) {
            Role role = roles.get(it2Roles.next());

            for (Iterator<RoleUser> it2RoleUser = role.getRoleUsers().iterator(); it2RoleUser.hasNext(); ) {
                RoleUser roleUser = it2RoleUser.next();
                User user = users.get(roleUser.getUser().getId());
                if (user!=null){
                    roleUser.setUser(user);
                    user.findRoleUser(roleUser.getId()).setRole(role);
                }
            }

            for (Iterator<IRoleFunctionTbom> it2RoleFunTbom = role.getRoleFunctionTboms().iterator(); it2RoleFunTbom.hasNext(); ) {
                RoleFunctionTbom roleFunTbom = (RoleFunctionTbom) it2RoleFunTbom.next();
                Function function = functions.get(roleFunTbom.getFunction().getFunctionid().toString());
                TbomDir tbomDir = tbomDirs.get(roleFunTbom.getTbomDir().getId());

                roleFunTbom.setFunction(function);
                roleFunTbom.setTbomDir(tbomDir);

                RoleFunctionTbom funTbomRole = function.findRoleFunTbom(roleFunTbom.getId());
                funTbomRole.setRole(role);
                funTbomRole.setTbomDir(tbomDir);

                RoleFunctionTbom tbomFunRole = tbomDir.findRoleFunTbom(roleFunTbom.getId());
                tbomFunRole.setRole(role);
                tbomFunRole.setFunction(function);

            }

        }
    }


    /**
     * 初始化角色Tbom信息
     */
    public void initRoleTboms() {
        staticTboms = new ArrayList<ITbom>();

        for (Iterator<String> it = tbomDirs.keySet().iterator(); it.hasNext(); ) {
            TbomDir dir = tbomDirs.get(it.next());
            if (!dir.getId().equals(" ") && 2 == dir.getType()) {

                for (ITbom tbomRoot : tbomRoots) {
                    if (tbomRoot.getName().equals(dir.getName())
                            && tbomRoot.getSchema().getId().equals(dir.getSchemaid())) {
                        staticTboms.add(tbomRoot);
                        break;
                    }
                }
            }
        }

        for (Iterator<String> it = roles.keySet().iterator(); it.hasNext(); ) {
            //loopSchema = repairSchema(loopSchema);
            //add zhy 2012-10-25 将角色相关的顶层TBOM节点也存在内存中
            Role role = roles.get(it.next());
            List<ITbom> retList = new ArrayList<ITbom>();
            List<ITbomDir> tbomDirList = role.getAllTbomDirs();
            if (tbomDirList != null && tbomDirList.size() > 0) {
                for (int i = 0; i < tbomDirList.size(); i++) {

                    ITbomDir tbomDir = tbomDirList.get(i);

                    if (!tbomDir.getId().equals(" ")) {
                        for (ITbom tbomRoot : tbomRoots) {
                            if (tbomRoot.getName().equals(tbomDir.getName())
                                    && tbomRoot.getSchema().getId().equals(tbomDir.getSchemaid())) {
                                retList.add(tbomRoot);
                                break;
                            }
                        }
                    }
                }
            }
            role.setAllTboms(retList);
            //end
        }
    }


    public void deleteTbom(TbomDir tbomDir) {
        for (Iterator<String> it = roles.keySet().iterator(); it.hasNext(); ) {
            Role role = roles.get(it.next());
            List<ITbom> tbList = role.getAllTboms();
            List<IRoleFunctionTbom> allfunctionbomTrees = role.getAllFunctionTboms();
            Set<IRoleFunctionTbom> roleFunctionTbom = new HashSet(0);
            if (!tbomDir.getId().equals(" ")) {
                for (ITbom tbomRoot : tbomRoots) {
                    if (tbomRoot.getName().equals(tbomDir.getName())
                            && tbomRoot.getSchema().getId().equals(tbomDir.getSchemaid())) {
                        tbList.remove(tbomRoot);
                        break;
                    }
                }
            }
            role.setAllTboms(tbList);
            tbomDirs.remove(tbomDir.getId());
            for (IRoleFunctionTbom rTbom : allfunctionbomTrees) {
                if (!rTbom.getTbomDir().getId().equals(tbomDir.getId())) {
                    roleFunctionTbom.add(rTbom);
                }
            }
            role.setRoleFunctionTboms(roleFunctionTbom);
        }
    }

    public void freshTbomRoot(TbomDir tbomdir, Tbom tbomNode) {
        if (tbomDirs.get(tbomdir.getId()) != null) {
            tbomDirs.remove(tbomdir.getId());
        }
        tbomDirs.put(tbomdir.getId(), tbomdir);

        for (ITbom tb : tbomRoots) {
            if (tb.getId().equals(tbomNode.getId())) {
                tbomRoots.remove(tb);
                break;
            }
        }
        tbomRoots.add(tbomNode);
        for (Iterator<String> it2Roles = roles.keySet().iterator(); it2Roles.hasNext(); ) {
            //刷新角色bom关系
            String roleId = it2Roles.next();
            Role role = roles.get(roleId);
            List<ITbom> tbList = role.getAllTboms();
            for (ITbom tb : tbList) {
                if (tb.getId().equals(tbomNode.getId())) {
                    tbList.remove(tb);
                    break;
                }
            }
            tbList.add(tbomNode);
            role.setAllTboms(tbList);
            //刷新角色bom列表信息
            Set<IRoleFunctionTbom> roleFunctionTbomSet = role.getRoleFunctionTboms();
            for (IRoleFunctionTbom roleFunctionTbom : roleFunctionTbomSet) {
                if (roleFunctionTbom.getTbomDir().getId().equals(tbomdir.getId())) {
                    roleFunctionTbom.setTbomDir(tbomdir);
                }
                roleFunctionTbomSet.add(roleFunctionTbom);
            }
            role.setRoleFunctionTboms(roleFunctionTbomSet);
            roles.put(roleId, role);

        }
    }

    /**
     * 初始化角色信息
     *
     * @return boolean
     * @throws
     * @Method: initRoleModel
     * <p>
     * TODO
     */
    public boolean initRoleModel() {

        initCache();

        initRoleTboms();

        return true;
    }

    /**
     * @return
     * @Method: getRoles
     * <p>
     * TODO
     * @see com.orient.sysmodel.operationinterface.IRoleModel#getRoles()
     */

    public Map<String, Role> getRoles() {
        return roles;
    }

    /**
     * @param roleId
     * @return
     * @Method: getRoleById
     * <p>
     * TODO
     * @see com.orient.sysmodel.operationinterface.IRoleModel#getRoleById(java.lang.String)
     */

    public Role getRoleById(String roleId) {
        return roles.get(roleId);
    }

    /**
     * @param roleId
     * @return
     * @Method: getIRoleById
     * <p>
     * TODO
     * @see com.orient.sysmodel.operationinterface.IRoleModel#getIRoleById(java.lang.String)
     */

    public IRole getIRoleById(String roleId) {
        return roles.get(roleId);
    }

    /**
     * @param roleName
     * @return
     * @Method: getRoleByName
     * <p>
     * TODO
     * @see com.orient.sysmodel.operationinterface.IRoleModel#getRoleByName(java.lang.String)
     */

    public Role getRoleByName(String roleName) {
        for (Iterator iter = this.getRoles().keySet().iterator(); iter.hasNext(); ) {
            String key = (String) iter.next();
            Role role = this.getRoles().get(key);
            if (role.getName().compareTo(roleName) == 0) {
                return role;
            }
        }

        return null;
    }

    /**
     * @param roleName
     * @return
     * @Method: getIRoleByName
     * <p>
     * TODO
     * @see com.orient.sysmodel.operationinterface.IRoleModel#getIRoleByName(java.lang.String)
     */

    public IRole getIRoleByName(String roleName) {
        for (Iterator iter = this.getRoles().keySet().iterator(); iter.hasNext(); ) {
            String key = (String) iter.next();
            Role role = this.getRoles().get(key);
            if (role.getName().compareToIgnoreCase(roleName) == 0) {
                return role;
            }
        }

        return null;
    }

    /**
     * @param userId
     * @return
     * @Method: getRolesOfUser
     * <p>
     * TODO
     * @see com.orient.sysmodel.operationinterface.IRoleModel#getRolesOfUser(java.lang.String)
     */

    public List<IRole> getRolesOfUser(String userId) {
        if (roles != null && roles.size() > 0) {
            List<IRole> roleList = new ArrayList<IRole>();
            for (Map.Entry<String, Role> entry : roles.entrySet()) {
                Role role = entry.getValue();
                if (role.getRoleUsers() != null) {
                    Set<RoleUser> roleUsers = role.getRoleUsers();
                    for (RoleUser roleUser : roleUsers) {
                        if (roleUser.getId().getUserId().equalsIgnoreCase(userId)) {
                            roleList.add(role);
                            break;
                        }
                    }

                }

            }
            return roleList;

        } else {
            return null;
        }

		/*List<RoleUser> roleUserList   = (List<RoleUser>)this.getRoleDAOFactory().getRoleUserDAO().findByProperty("id.userId", userId);
        if(roleUserList==null || roleUserList.size()>0){
			List<IRole> roleList = new ArrayList<IRole>();
			for(int i=0;i<roleUserList.size();i++){
				roleList.add(getIRoleById(roleUserList.get(i).getId().getRoleId()));
			}
			return roleList;
		}else{
			return null;
		}*/

    }

    /**
     * TODO
     *
     * @param userId
     * @return
     * @Method: getUserById
     * @see com.orient.sysmodel.operationinterface.IRoleModel#getUserById(java.lang.String)
     */
    public IUser getUserById(String userId) {
        return users.get(userId);
    }

    public IUser getUserByUserName(String username) {

        return users.values().stream().filter(user -> user.getUserName().equals(username)).count() > 0 ? users.values().stream().filter(user -> user.getUserName().equals(username)).findFirst().get():null;
    }

    public List<ITbom> getStaticTboms() {
        return staticTboms;
    }


    /**
     * @return the functions
     */
    public Map<String, Function> getFunctions() {
        return functions;
    }


    /**
     * @return the users
     */
    public Map<String, User> getUsers() {
        return users;
    }


    /**
     * @return the departments
     */
    public Map<Integer, Department> getDepartments() {
        return departments;
    }

    /**
     * @return the operations
     */
    public Map<String, Operation> getOperations() {
        return operations;
    }


    /**
     * @return the tbomDirs
     */
    public Map<String, TbomDir> getTbomDirs() {
        return tbomDirs;
    }

    @Override
    public ITbom getTbomById(String treeId) {
        ITbom retVal = null;
        for (ITbom itbom : tbomRoots) {
            if (itbom.getId().equals(treeId)) {
                retVal = itbom;
                break;
            }
        }
        return retVal;
    }

    public List<ITbom> getTbomRoots() {
        return tbomRoots;
    }
}

