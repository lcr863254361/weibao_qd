package com.orient.sysmodel.service.user;

import com.orient.sysmodel.domain.role.*;
import com.orient.sysmodel.domain.user.*;
import com.orient.sysmodel.operationinterface.IUser;
import com.orient.sysmodel.roleengine.IRoleUtil;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO dao;
    private FunctionDAO functionDao;
    private UserDeptDAO userDeptDAO;
    private IRoleUtil roleEngine;

    public UserDeptDAO getUserDeptDAO() {
        return userDeptDAO;
    }

    public void setUserDeptDAO(UserDeptDAO userDeptDAO) {
        this.userDeptDAO = userDeptDAO;
    }

    /**
     * 查询用户所在部门信息;
     *
     * @param user_id
     * @return
     */
    @SuppressWarnings("unchecked")
    public List queryUserDept(String user_id) {
        List<UserDept> list = userDeptDAO.findAll();
        return list;
    }

    /**
     * 新增用户
     *
     * @param user
     * @Method: createUser
     * <p>
     * TODO
     * @see com.orient.sysmodel.service.user.UserService#createUser(com.orient.sysmodel.domain.user.User)
     */
    @SuppressWarnings("unchecked")
    public void createUser(User user) {
        dao.save(user);
        Map<String, User> users = roleEngine.getRoleModel(false).getUsers();
        users.put(user.getId(), user);
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @Method: updateUser
     * <p>
     * TODO
     * @see com.orient.sysmodel.service.user.UserService#updateUser(com.orient.sysmodel.domain.user.User)
     */
    public void updateUser(User user) {
        dao.attachDirty(user);
    }

    /**
     * 删除用户信息
     *
     * @param user
     * @Method: delete
     * <p>
     * TODO
     * @see com.orient.sysmodel.service.user.UserService#delete(com.orient.sysmodel.domain.user.User)
     */
    public void delete(User user) {
//		dao.delete(user);
        user.setState("0");
        dao.attachDirty(user);
    }

    /**
     * 批量删除用户信息
     *
     * @param userids 用户的ID集合, 用逗号隔开
     * @return
     * @Method: delete
     * <p>
     * TODO
     * @see com.orient.sysmodel.service.user.UserService#delete(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public boolean delete(String userIds) {
        Map<String, User> users = roleEngine.getRoleModel(false).getUsers();

        if (userIds != null && !userIds.equalsIgnoreCase("")) {
            String[] ids = userIds.split(",");
            for (int i = 0; i < ids.length; i++) {
                User user = users.get(ids[i]);
                user.setState("0");
                //删除用户后，该账号名称按理应该可以继续用于用户创建，所以把假删除的用户名加上唯一的时间后缀
                user.setUserName(user.getUserName() + "&" + System.currentTimeMillis());
                dao.update(user);
                //真删除数据
//                dao.delete(users.get(ids[i]));
                User delUser = users.remove(ids[i]);
                for (Iterator<RoleUser> it = delUser.getRoleUsers().iterator(); it.hasNext(); ) {
                    RoleUser roleUser = it.next();
                    roleUser.getRole().deleteRoleUser(roleUser.getId());
                }
            }
            return true;
        } else return false;
    }

    /**
     * 根据Id查找用户
     *
     * @param userId
     * @return User
     * @throws
     * @Method: findById
     * <p>
     * TODO
     */
    public User findById(String userId) {
        IUser user = roleEngine.getRoleModel(false).getUserById(userId);
        return (User)user;
    }

    @Override
    public List findByExampleLike(LightweightUser instance, Map<String, String> betweens) {
        return dao.findByExampleLike(instance, betweens);
    }

    /**
     * 根据Id查找用户(只显示用户的基本信息，role等等除外)
     *
     * @Enclosing_Method : findUserById
     * @Version : v1.00
     */
    public User findUserById(String userId) {

        return dao.findById(userId);
    }

    /**
     * 根据用户名查找用户
     *
     * @param userName
     * @return User
     * @throws
     * @Method: findByUserName
     * <p>
     * TODO
     */
    public User findByUserName(String userName) {
        List<User> userList = dao.findByUserName(userName);
        if (userList != null && userList.size() > 0) {
            //return userList.get(0);
            return setUserContainer(userList.get(0));
        } else {
            return null;
        }
    }


    /**
     * 根据用户名查找用户(只显示用户的基本信息，role等等除外)
     *
     * @Enclosing_Method : findUserByUserName
     * @Version : v1.00
     */
    public User findUserByUserName(String userName) {
        List<User> userList = dao.findByUserName(userName);
        if (userList != null && userList.size() > 0) {
            //return userList.get(0);
            return userList.get(0);
        } else {
            return null;
        }
    }


    /**
     * 根据数据库中取得的user信息,初始化一些变量 如roleIds等
     *
     * @param user
     * @return User
     * @throws
     * @Method: setUserContainer
     * <p>
     * TODO
     */
    private User setUserContainer(User user) {
        //取得角色权限信息
        String roleIds = "";
        String roleNames = "";
        String overAllOperations = "";

        for (Iterator it = user.getRoleUsers().iterator(); it.hasNext(); ) {
            RoleUser roleUser = (RoleUser) it.next();
            Role role = roleUser.getRole();
            roleIds = roleIds + role.getId() + ",";
            roleNames = roleNames + role.getName() + ",";
            for (Iterator itOperations = role.getOverAllOperations().iterator(); itOperations.hasNext(); ) {
                OverAllOperations operatons = (OverAllOperations) itOperations.next();
                //overAllOperations = overAllOperations + operatons.getId().getOperationIds() + ",";
                overAllOperations = overAllOperations + operatons.getOperationIds() + ",";
            }
        }

        if (roleIds.length() > 0) {
            roleIds = roleIds.substring(0, roleIds.length() - 1);
        }
        if (roleNames.length() > 0) {
            roleNames = roleNames.substring(0, roleNames.length() - 1);
        }
        if (overAllOperations.length() > 0) {
            overAllOperations = overAllOperations.substring(0, overAllOperations.length() - 1);
        }

        user.setRoleIds(roleIds);
        user.setRoleNames(roleNames);
        user.setOverAllOperations(overAllOperations);

        if (!"".equals(roleIds)) {
            //取得一级功能点信息
            user.setTabList(queryTabList(roleIds));
        }

        //左侧目录树
        user.setTreeList(getTreeList(user));


        return user;
    }

    /**
     * 取得用户有权限查看的一级功能点信息
     *
     * @param roleIds
     * @return List
     * @throws
     * @Method: queryTabList
     * <p>
     * TODO
     */
    private List queryTabList(String roleIds) {
        List list = null;
        if (roleIds != null && roleIds.length() > 0) {
            roleIds = roleIds.replaceAll(",", "','");
            StringBuffer sql = new StringBuffer();
            //sql.append(" SELECT  functionid,name,url,tbomFlg");
            sql.append(" FROM Function");
            sql.append(" WHERE code NOT LIKE '%rcp%'");
            sql.append("       AND functionid IN (SELECT id.functionId");
            sql.append("                            FROM RoleFunction");
            sql.append("                            WHERE id.roleId IN ('" + roleIds + "'))");
            sql.append("       AND parentid = 4 ");
            sql.append(" ORDER BY position");

            List resultList = functionDao.getSqlResult(sql.toString());

            if (resultList != null && resultList.size() > 0) {
                list = new ArrayList();
                for (int i = 0; i < resultList.size(); i++) {
                    Map map = new HashMap();
                    map.put("ID", ((Function) resultList.get(i)).getFunctionid().toString());
                    map.put("NAME", ((Function) resultList.get(i)).getName());
                    map.put("URL", ((Function) resultList.get(i)).getUrl());
                    map.put("TBOM_FLG", ((Function) resultList.get(i)).getTbomFlg());
                    list.add(map);
                }
            }

        }

        return list;
    }

    /**
     * 查询左侧目录树
     *
     * @param user
     * @return List
     * @throws
     * @Method: getTreeList
     * <p>
     * TODO
     */
    private List getTreeList(User user) {

        List tabList = user.getTabList();
        if (tabList == null) {
            tabList = new ArrayList<>();
        }
        String roleIds = user.getRoleIds();
        List treeList = new ArrayList();
        if (!"".equals(roleIds)) {
            for (int i = 0; i < tabList.size(); i++) {
                Map tabListMap = (Map) tabList.get(i);
                String tabListId = (String) tabListMap.get("ID");
                List leftTreeList = (List) queryLeftTree(tabListId, roleIds);
                treeList.add(leftTreeList);
            }
        }

        return treeList;
    }

    /**
     * 查询左侧目录树
     *
     * @param functionid
     * @param roleIds
     * @return List
     * @throws
     * @Method: queryTreeList
     * <p>
     * TODO
     */
    public List<Map> queryLeftTree(String functionid, String roleIds) {
        List list = new ArrayList();
        roleIds = roleIds.replaceAll(",", "','");
        Function function = functionDao.findById(Long.valueOf(functionid));
        if (function != null) {
            Map map = new HashMap();
            map.put("ID", function.getFunctionid().toString());
            map.put("PID", "-1");
            map.put("DISPLAY_NAME", function.getName());
            map.put("URL", function.getUrl());
            map.put("TBOM_FLG", function.getTbomFlg());
            list.add(map);
        }

        StringBuffer sql = new StringBuffer();
        //sql.append("SELECT     FUNCTIONID ID, NAME DISPLAY_NAME, PARENTID PID, URL,TBOM_FLG");
        sql.append(" FROM Function");
        sql.append(" WHERE parentid = '" + functionid + "' ");
        sql.append("       AND functionid IN (SELECT id.functionId");
        sql.append("                            FROM RoleFunction");
        sql.append("                            WHERE id.roleId IN ('" + roleIds + "'))");
        sql.append(" ORDER BY position");

        List resultList = functionDao.getSqlResult(sql.toString());

        if (resultList != null && resultList.size() > 0) {
            for (int i = 0; i < resultList.size(); i++) {
                Function subFunction = (Function) resultList.get(i);
                Map map = new HashMap();
                map.put("ID", subFunction.getFunctionid().toString());
                map.put("PID", functionid);
                map.put("DISPLAY_NAME", subFunction.getName());
                map.put("URL", subFunction.getUrl());
                map.put("TBOM_FLG", subFunction.getTbomFlg());
                list.add(map);
            }
        }

        return list;
    }

    /**
     * 查找所有有效用户
     *
     * @return List<User>
     * @throws
     * @Method: findAllUser
     * <p>
     * TODO
     */
    public List<User> findAllUser() {
        return dao.findAll();
    }

    /**
     * 校验用户密码是否正确
     *
     * @param user
     * @param password 加密的密码串
     * @return
     * @Method: validateUserPassword
     * <p>
     * TODO
     * @see com.orient.sysmodel.service.user.UserService#validateUserPassword(com.orient.sysmodel.domain.user.User, java.lang.String)
     */
    public boolean validateUserPassword(User user, String password) {

        if (user == null) {
            return false;
        } else {
            if (user.getPassword().equalsIgnoreCase(password)) {
                return true;
            } else {
                return false;
            }
        }

    }

    /**
     * 取得有权限的功能
     *
     * @param roleIds
     * @param functioncode
     * @return
     * @Method: findFunctionsByCode
     * <p>
     * TODO
     * @see com.orient.sysmodel.service.user.UserService#findFunctionsByCode(java.lang.String, java.lang.String)
     */
    public List<Function> findFunctionsByCode(String roleIds, String functioncode) {
        List list = null;
        if (roleIds != null && roleIds.length() > 0) {
            roleIds = roleIds.replaceAll(",", "','");
            StringBuffer sql = new StringBuffer();
            //sql.append(" SELECT  functionid,name,url,tbomFlg");
            sql.append(" FROM Function");
            sql.append(" WHERE code = '" + functioncode + "'");
            sql.append("       AND functionid IN (SELECT id.functionId");
            sql.append("                            FROM RoleFunction");
            sql.append("                            WHERE id.roleId IN ('" + roleIds + "'))");
            sql.append(" ORDER BY position");

            list = functionDao.getSqlResult(sql.toString());

        }
        return list;
    }

    //取得用户列表页面上的数据
    public List getUserListForDisplay() {
        StringBuffer sql = new StringBuffer();
        //sql.append("SELECT id, userName, allName, dept.id as deptId, dept.name as deptName,decode(sex, '1', '男','0','女') as sex ");
        //sql.append(" FROM User ");
        //sql.append(" WHERE isDel<>'1' or isDel is null ");
        sql.append(" SELECT a.ID,a.USER_NAME,a.ALL_NAME,a.DEP_ID,decode(a.SEX,'1','男','0','女')SEX,a.FLG,decode(a.STATE,'1','启用','0','禁用')STATE,decode('1','测试工程师','2','网络工程师','3','软件工程师',a.POST,'4','技术支持','5','项目经理','6','技术主任','7','架构师','8','销售经理','9','部门经理')POST,a.PHONE,a.SPECIALTY,a.CREATE_TIME,a.CREATE_USER,a.UPDATE_TIME,decode(a.GRADE,'C4CA4238A0B923820DCC509A6F75849B','机密','C81E728D9D4C2F636F067F89CC14862C','秘密','ECCBC87E4B5CE2FE28308FD9F2A7BAF3','内部','A87FF679A2F3E71D9181A67B7542122C','非密')GRADE,a.UPDATE_USER,a.NOTES,a.BIRTHDAY,a.MOBILE,a.E_MAIL,a.PASSWORD,a.LOCK_STATE, a.ID Pk, a.is_del, a.PASSWORD_SET_TIME, a.LOCK_TIME, a.LOGIN_FAILURES, a.LAST_FAILURE_TIME ");
        //sql.append(" SELECT a.ID,a.USER_NAME,a.ALL_NAME,a.DEP_ID, decode(a.SEX,'1','男','0','女')SEX,a.FLG,decode(a.STATE,'1','启用','0','禁用')STATE,decode(a.POST,'4','技术支持','5','项目经理','6','技术主任','7','架构师','8','销售经理','9','部门经理','1','测试工程师','2','网络工程师','3','软件工程师')POST,a.PHONE,a.SPECIALTY,a.CREATE_TIME,a.CREATE_USER,a.UPDATE_TIME,decode(a.GRADE,'C4CA4238A0B923820DCC509A6F75849B','机密','C81E728D9D4C2F636F067F89CC14862C','秘密','ECCBC87E4B5CE2FE28308FD9F2A7BAF3','内部','A87FF679A2F3E71D9181A67B7542122C','非密')GRADE,a.UPDATE_USER,a.NOTES,a.BIRTHDAY,a.MOBILE,a.E_MAIL,a.PASSWORD,a.LOCK_STATE, a.ID Pk ");

        sql.append(" FROM cwm_sys_user a, cwm_sys_department b WHERE a.dep_id = to_char(b.id(+)) and (a.is_del <> '1' or a.is_del is null) ");
        List resultList = dao.getSqlResult(sql.toString());
        int i = resultList.size();
        return resultList;
        //SELECT a.ID,a.USER_NAME,a.ALL_NAME,a.DEP_ID,b.NAME DEP_ID,decode(a.SEX,'1','男','0','女')SEX,a.FLG,decode(a.STATE,'1','启用','0','禁用')STATE,decode(a.POST,'4','技术支持','5','项目经理','6','技术主任','7','架构师','8','销售经理','9','部门经理','1','测试工程师','2','网络工程师','3','软件工程师')POST,a.PHONE,a.SPECIALTY,a.CREATE_TIME,a.CREATE_USER,a.UPDATE_TIME,decode(a.GRADE,'C4CA4238A0B923820DCC509A6F75849B','机密','C81E728D9D4C2F636F067F89CC14862C','秘密','ECCBC87E4B5CE2FE28308FD9F2A7BAF3','内部','A87FF679A2F3E71D9181A67B7542122C','非密')GRADE,a.UPDATE_USER,a.NOTES,a.BIRTHDAY,a.MOBILE,a.E_MAIL,a.PASSWORD,a.LOCK_STATE, a.ID Pk
        //FROM cwm_sys_user a, cwm_sys_department b WHERE a.dep_id = to_char(b.id(+)) and (is_del <> '1' or is_del is null)
    }
    //取得用户详细页面上的数据
    //取得用户编辑页面上的数据

    public UserDAO getDao() {
        return dao;
    }

    public void setDao(UserDAO dao) {
        this.dao = dao;
    }

    /**
     * functionDao
     *
     * @return the functionDao
     * @since CodingExample Ver 1.0
     */

    public FunctionDAO getFunctionDao() {
        return functionDao;
    }

    /**
     * functionDao
     *
     * @param functionDao the functionDao to set
     * @since CodingExample Ver 1.0
     */

    public void setFunctionDao(FunctionDAO functionDao) {
        this.functionDao = functionDao;
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


}
