/**
 * DepartmentServiceImpl.java
 * com.sysmodel.service.user
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2012-3-16 		zhang yan
 * <p>
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.orient.sysmodel.service.user;

import java.util.*;

import com.orient.sysmodel.domain.role.OverAllOperations;
import com.orient.sysmodel.domain.role.Role;
import com.orient.sysmodel.domain.role.RoleUser;
import com.orient.sysmodel.domain.user.Department;
import com.orient.sysmodel.domain.user.DepartmentDAO;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.domain.user.UserDAO;
import com.orient.sysmodel.domain.user.UserLoginHistory;
import com.orient.sysmodel.domain.user.UserLoginHistoryDAO;
import com.orient.sysmodel.operationinterface.IRoleModel;
import com.orient.sysmodel.roleengine.IRoleUtil;

/**
 * ClassName:DepartmentServiceImpl
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author zhang yan
 * @version
 * @since Ver 1.1
 * @Date 2012-3-16		下午04:24:26
 *
 * @see
 */
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentDAO dao;
    private UserDAO userDao;
    private UserLoginHistoryDAO userLoginHistoryDao;
    private IRoleUtil roleEngine;

    /**
     * dao
     *
     * @return the dao
     * @since CodingExample Ver 1.0
     */

    public DepartmentDAO getDao() {
        return dao;
    }

    /**
     * dao
     *
     * @param   dao    the dao to set
     * @since CodingExample Ver 1.0
     */

    public void setDao(DepartmentDAO dao) {
        this.dao = dao;
    }

    /**
     * userDao
     *
     * @return the userDao
     * @since CodingExample Ver 1.0
     */

    public UserDAO getUserDao() {
        return userDao;
    }

    /**
     * userDao
     *
     * @param   userDao    the userDao to set
     * @since CodingExample Ver 1.0
     */

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    /**
     * userLoginHistoryDao
     *
     * @return the userLoginHistoryDao
     * @since CodingExample Ver 1.0
     */

    public UserLoginHistoryDAO getUserLoginHistoryDao() {
        return userLoginHistoryDao;
    }

    /**
     * userLoginHistoryDao
     *
     * @param   userLoginHistoryDao    the userLoginHistoryDao to set
     * @since CodingExample Ver 1.0
     */

    public void setUserLoginHistoryDao(UserLoginHistoryDAO userLoginHistoryDao) {
        this.userLoginHistoryDao = userLoginHistoryDao;
    }


    /**
     * @return the roleEngine
     */
    public IRoleUtil getRoleEngine() {
        return roleEngine;
    }

    /**
     * @param roleEngine the roleEngine to set
     */
    public void setRoleEngine(IRoleUtil roleEngine) {
        this.roleEngine = roleEngine;
    }

    /**

     * @Method: createDept

     * TODO 新增部门信息

     * @param dept

     * @see com.orient.sysmodel.service.user.DepartmentService#createDept(com.orient.sysmodel.domain.user.Department)

     */

    @SuppressWarnings("unchecked")
    public void createDept(Department dept) {

        // TODO Auto-generated method stub
        dao.save(dept);

        Map<Integer, Department> depts = roleEngine.getRoleModel(false).getDepartments();
        depts.put(Integer.valueOf(dept.getId()), dept);

        if (!"-1".equals(dept.getPid())) {
            Department pDept = depts.get(Integer.valueOf(dept.getPid()));
            pDept.getChildDepts().add(dept);
        }

    }

    /**

     * @Method: deleteDept

     * TODO 删除部门及其子部门

     * @param dept

     * @see com.orient.sysmodel.service.user.DepartmentService#deleteDept(com.orient.sysmodel.domain.user.Department)

     */

    @SuppressWarnings("rawtypes")
    public void deleteDept(Department dept) {

        // TODO Auto-generated method stub
        IRoleModel roleModel = roleEngine.getRoleModel(false);
        //删除子部门
        for (Iterator it = dept.getChildDepts().iterator(); it.hasNext(); ) {
            Department subDepartment = (Department) it.next();
            deleteDept(subDepartment);
        }
        //移除父部门引用
        if (dept.getParentDept() != null) {
            dept.getParentDept().getChildDepts().remove(dept);
        }
        //将相关用户的部门信息置空
        for (Iterator it = dept.getUsers().iterator(); it.hasNext(); ) {
            User user = (User) it.next();
            user.setDept(null);
            userDao.attachDirty(user);
            roleModel.getUsers().get(user.getId()).setDept(null);
        }
        //将相关用户登录信息中的部门信息置空
        for (Iterator it = dept.getUserLoginHistorys().iterator(); it.hasNext(); ) {
            UserLoginHistory userLoginHistory = (UserLoginHistory) it.next();
            userLoginHistory.setDept(null);
            userLoginHistoryDao.attachDirty(userLoginHistory);
        }
        //删除部门
        dao.delete(dept);

        roleModel.getDepartments().remove(Integer.valueOf(dept.getId()));
    }

    /**

     * @Method: deleteDept

     * TODO 批量删除部门及其子部门

     * @param deptIds

     * @see com.orient.sysmodel.service.user.DepartmentService#deleteDept(java.lang.String)

     */

    public void deleteDept(String deptIds) {

        // TODO Auto-generated method stub
        String[] deptIdArray = deptIds.split(",");
        for (int i = 0; i < deptIdArray.length; i++) {
            Department dept = roleEngine.getRoleModel(false).getDepartments().get(Integer.valueOf(deptIdArray[i]));
            if (dept != null) deleteDept(dept);
        }

    }

    /**

     * @Method: findAll

     * TODO 查询所有的部门信息

     * @return

     * @see com.orient.sysmodel.service.user.DepartmentService#findAll()

     */

    public List<Department> findAll() {

        // TODO Auto-generated method stub
        List<Department> allDepartments = new ArrayList<Department>();
        Map<Integer, Department> departemnts = roleEngine.getRoleModel(false).getDepartments();
        for (Department dep : departemnts.values()) {
            allDepartments.add(dep);
        }
        return allDepartments;

    }

    /**

     * @Method: findByPid

     * TODO 查询子部门信息

     * @return

     * @see com.orient.sysmodel.service.user.DepartmentService#findByPid()

     */

    public List<Department> findByPid(String pid) {

        Department dep = roleEngine.getRoleModel(false).getDepartments().get(Integer.valueOf(pid));
        Set<Department> childDep = dep.getChildDepts();
        List<Department> subDeps = new ArrayList<Department>();
        for (Department subDep : childDep) {
            subDeps.add(subDep);
        }

        return subDeps;
    }

    /**

     * @Method: findById

     * TODO 取得部门信息

     * @param id
     * @return

     * @see com.orient.sysmodel.service.user.DepartmentService#findById(java.lang.String)

     */

    public Department findById(String id) {

        // TODO Auto-generated method stub
        return roleEngine.getRoleModel(false).getDepartments().get(Integer.valueOf(id));

    }

    /**

     * @Method: findByName

     * TODO 根据部门名称查找部门信息

     * @param deptName
     * @return

     * @see com.orient.sysmodel.service.user.DepartmentService#findByName(java.lang.String)

     */

    public Department findByName(String deptName) {

        // TODO Auto-generated method stub
        Map<Integer, Department> departemnts = roleEngine.getRoleModel(false).getDepartments();
        for (Department dep : departemnts.values()) {
            if (deptName.equals(dep.getName())) {
                return dep;

            }
        }
        return null;
    }

    /**

     * @Method: updateDept

     * TODO 更新部门信息

     * @param dept

     * @see com.orient.sysmodel.service.user.DepartmentService#updateDept(com.orient.sysmodel.domain.user.Department)

     */

    public void updateDept(Department dept) {

        // TODO Auto-generated method stub
        dao.attachDirty(dept);
        roleEngine.getRoleModel(false).getDepartments().put(Integer.valueOf(dept.getId()), dept);
    }

}

