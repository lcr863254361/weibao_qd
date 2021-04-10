package com.orient.collab.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.model.AssignUserBean;
import com.orient.collab.model.RoleFunctionTreeNode;
import com.orient.collab.model.RoleUserGridModel;
import com.orient.collab.model.RoleUserTreeNode;
import com.orient.sysmodel.dao.collab.ICollabRoleDao;
import com.orient.sysmodel.domain.collab.CollabFunction;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.collab.impl.CollabFunctionService;
import com.orient.sysmodel.service.collab.impl.CollabRoleService;
import com.orient.sysmodel.service.user.DepartmentService;
import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.util.UserContextUtil;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.orient.collab.config.CollabConstants.*;
import static com.orient.sysmodel.domain.collab.CollabFunction.BELONGED_MODEL;
import static com.orient.sysmodel.domain.collab.CollabRole.MODEL_NAME;
import static com.orient.sysmodel.domain.collab.CollabRole.NODE_ID;

/**
 * the team business
 *
 * @author Seraph
 * 2016-07-08 下午3:42
 */
@Service
public class TeamBusiness extends BaseBusiness {

    public List<CollabRole> getRoles(String modelName, String dataId) {

        List<CollabRole> collabRoles = this.roleService.list(Restrictions.eq(MODEL_NAME, modelName),
                Restrictions.eq(NODE_ID, dataId));

        return collabRoles;
    }


    public List<CollabFunction> getRoleFunctions(String roleId) {
        if (roleId.startsWith("\"")) {//ext's bug?
            roleId = roleId.substring(1, roleId.length() - 1);
        }
        CollabRole collabRole = this.roleService.getById(Long.valueOf(roleId));
        List<CollabFunction> functions = new ArrayList<CollabFunction>(collabRole.getFunctions());
        Collections.sort(functions);
        return functions;
    }

    public List<CollabFunction> getModelFunctions(String modelName) {
        List<CollabFunction> functions = this.functionService.list(Restrictions.eq(BELONGED_MODEL, modelName));
        Collections.sort(functions);
        return functions;
    }

    public List<CollabRole.User> getRoleUsers(String roleId) {

        CollabRole role = this.roleService.getById(Long.valueOf(roleId));
        return new ArrayList<>(role.getUsers());
    }

    public List<RoleUserGridModel> getRoleUserGridModels(String modelName, String dataId) {
        List<RoleUserGridModel> retV = UtilFactory.newArrayList();
        List<CollabRole> collabRoles = this.roleService.list(Restrictions.eq(MODEL_NAME, modelName),
                Restrictions.eq(NODE_ID, dataId));
        for (CollabRole collabRole : collabRoles) {

            for (CollabRole.User user : collabRole.getUsers()) {
                RoleUserGridModel gridModel = new RoleUserGridModel();
                gridModel.setId(collabRole.getId() + NODE_ID_SPLIT + user.getId());
                gridModel.setRoleId(String.valueOf(collabRole.getId()));
                gridModel.setRoleName(collabRole.getName());
                gridModel.setUserId(user.getId());
                gridModel.setUserName(user.getAllName());
                gridModel.setDeptName(CommonTools.isNullString(user.getDepId()) ? "" : this.departmentService.findById(user.getDepId()).getName());
                retV.add(gridModel);
            }
        }

        return retV;
    }

    public List<RoleUserTreeNode> getRoleUserTreeModels(String modelName, String dataId, String parId) {

        List<RoleUserTreeNode> retV = UtilFactory.newArrayList();
//        if (parId.startsWith("\"")) {//ext's bug?
//            parId = parId.substring(1, parId.length() - 1);
//        }
//
//        if ("-1".equals(parId)) {
//            List<CollabRole> collabRoles = this.roleService.list(Restrictions.eq(MODEL_NAME, modelName),
//                    Restrictions.eq(NODE_ID, dataId));
//            for (CollabRole collabRole : collabRoles) {
//
//                RoleUserTreeNode gridModel = new RoleUserTreeNode();
//                gridModel.setId("ROLE" + NODE_ID_SPLIT + collabRole.getId());
//                gridModel.setRoleId(String.valueOf(collabRole.getId()));
//                gridModel.setRoleName(collabRole.getName());
//                gridModel.setIconCls("icon-GROUP");
//                if (DefaultTeamRole.containRoleName(collabRole.getName())) {
//                    gridModel.setDefaultRole(true);
//                }
//                retV.add(gridModel);
//            }
//        } else {
//            CollabRole collabRole = this.roleService.getById(Long.valueOf(parId));
//            for (CollabRole.User user : collabRole.getUsers()) {
//                RoleUserTreeNode gridModel = new RoleUserTreeNode();
//                gridModel.setId("ROLE_" + parId + "USER_" + user.getId());
//                gridModel.setRoleId(String.valueOf(collabRole.getId()));
//                gridModel.setRoleName("");
//                gridModel.setUserId(user.getId());
//                gridModel.setUserName(user.getAllName());
//                gridModel.setDeptName(CommonTools.isNullString(user.getDepId()) ? "" : this.departmentService.findById(user.getDepId()).getName());
//                gridModel.setIconCls("icon-PEOPLE");
//                gridModel.setLeaf(true);
//                retV.add(gridModel);
//            }
//
//        }

        return retV;
    }

    public List<RoleFunctionTreeNode> getRoleFunctionTreeNodes(String modelName, String roleId) {

        if (modelName.startsWith("\"")) {//ext's bug?
            modelName = modelName.substring(1, modelName.length() - 1);
        }

        List<RoleFunctionTreeNode> nodes = UtilFactory.newArrayList();

        List<CollabFunction> allFunctions = this.functionService.list(Restrictions.eq(BELONGED_MODEL, modelName));
        List<CollabFunction> roleFunctions = this.getRoleFunctions(roleId);
        for (CollabFunction collabFunction : allFunctions) {
//            if (collabFunction.getParFunction() != null) {
//                continue;
//            }
            nodes.add(convertCollabFunctionToRoleFunctionTreeNode(collabFunction, roleFunctions, false));
        }

        return nodes;
    }

    public CommonResponseData saveAssignFunctions(String roleId, String functionIds) {
        CommonResponseData retV = new CommonResponseData(true, "");

//        CollabRole collabRole = this.roleService.getById(Long.valueOf(roleId));
//
//        List<Long> toSaveFuncIds = UtilFactory.newArrayList();
//
//
//        String[] functionIdArray = functionIds.split(",");
//        for (String funcId : functionIdArray) {
//            if (!CommonTools.isNullString(funcId)) {
//                toSaveFuncIds.add(Long.valueOf(funcId));
//            }
//        }
//
//        List<CollabFunction> functions = toSaveFuncIds.size() == 0 ? UtilFactory.newArrayList() : this.functionService.list(Restrictions.in("id", toSaveFuncIds));
//
//        if (collabRole.getName().equals(DefaultTeamRole.Leader.toString())) {
//            MutableBoolean hasTeamFunction = new MutableBoolean(false);
//            functions.forEach(function -> {
//                if (function.getName().equals(ROLE_FUNCTION_TEAM)) {
//                    hasTeamFunction.setValue(true);
//                }
//            });
//
//            if (!(Boolean) hasTeamFunction.getValue()) {
//                return new CommonResponseData(false, "领导角色工作组功能点必须保留!");
//            }
//
//        }
//
//        collabRole.getFunctions().clear();
//        collabRole.getFunctions().addAll(new HashSet<>(functions));
//
//        this.roleService.update(collabRole);
        return retV;
    }

    public ExtGridData<AssignUserBean> getUsersToAssign(String roleId, boolean assigned, int page, int limit, AssignUserBean userFilter) {
        ExtGridData<AssignUserBean> retV = new ExtGridData<>();


//        List<CollabRole.User> assignedUsers = UtilFactory.newArrayList();
//        if (!CommonTools.isNullString(roleId)) {
//            assignedUsers = getRoleUsers(roleId);
//        }
//
//        List<CollabRole.User> users = UtilFactory.newArrayList();
//        if (assigned) {
//            users = assignedUsers;
//        } else {
//            StringBuilder assignedUserIds = new StringBuilder();
//            for (CollabRole.User assignedUser : assignedUsers) {
//                assignedUserIds.append(assignedUser.getId()).append(",");
//            }
//            if (assignedUserIds.length() > 0) {
//                assignedUserIds.deleteCharAt(assignedUserIds.length() - 1);
//            }
//            users = this.roleService.getUnassignedUsers(assignedUserIds.toString(), userFilter.getUserName(), userFilter.getDepartment());
//        }
//
//        List<CollabRole.User> subList = PageUtil.page(users, page, limit);
//        List<AssignUserBean> results = new ArrayList<>();
//        subList.forEach(user -> {
//            AssignUserBean userBean = new AssignUserBean();
//            try {
//                PropertyUtils.copyProperties(userBean, user);
//                userBean.setDepartment(CommonTools.isNullString(user.getDepId()) ? "" : this.departmentService.findById(user.getDepId()).getName());
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//            results.add(userBean);
//        });
//
//        retV.setResults(results);
//        retV.setTotalProperty(users.size());
        return retV;
    }

    private RoleFunctionTreeNode convertCollabFunctionToRoleFunctionTreeNode(CollabFunction collabFunction, List<CollabFunction> roleFunctions, Boolean defaultChecked) {
        RoleFunctionTreeNode node = new RoleFunctionTreeNode();
//        node.setId(String.valueOf(collabFunction.getId()));
//        node.setText(collabFunction.getName());
//        //node.setPid(collabFunction.getParFunction() == null ? "" : String.valueOf(collabFunction.getParFunction().getId()));
//        node.setIconCls(collabFunction.getIconCls());
//        if (CommonTools.isEmptyList(roleFunctions)) {
//            node.setChecked(defaultChecked);
//        } else {
//            for (CollabFunction roleFunction : roleFunctions) {
//                if (roleFunction.getId().equals(collabFunction.getId())) {
//                    node.setChecked(true);
//                    break;
//                }
//            }
//        }
//
////        if (collabFunction.getSubFunctions().size() == 0) {
////            node.setLeaf(true);
////        } else {
////            node.setExpanded(true);
////
////            List<CollabFunction> subFunctionList = new ArrayList<>(collabFunction.getSubFunctions());
////            Collections.sort(subFunctionList);
////            for (CollabFunction subFunction : subFunctionList) {
////                node.getResults().add(convertCollabFunctionToRoleFunctionTreeNode(subFunction, roleFunctions, defaultChecked));
////            }
////        }

        return node;
    }

    public boolean saveAssignedUsers(String roleId, String[] selectedIds, String direction, StringBuilder errorMsg) {
//        CollabRole collabRole = this.roleService.getById(Long.valueOf(roleId));
//        Set<CollabRole.User> roleUsers = collabRole.getUsers();
//
//        if ("left".equals(direction)) {
//            for (String selectedId : selectedIds) {
//                Iterator<CollabRole.User> itor = roleUsers.iterator();
//                while (itor.hasNext()) {
//                    if (itor.next().getId().equals(selectedId)) {
//                        itor.remove();
//                    }
//                }
//            }
//        } else {
//            for (String selectedId : selectedIds) {
//                CollabRole.User user = this.collabRoleDao.get(CollabRole.User.class, selectedId);
//                roleUsers.add(user);
//            }
//        }
//
//        if (collabRole.getName().equals(DefaultTeamRole.Executor.toString())) {
//            if (collabRole.getUsers().size() <= 0) {
//                errorMsg.append("应至少保留一个执行人!");
//                return false;
//            }
//        }
//        this.roleService.update(collabRole);
        return true;
    }

    public CommonResponseData deleteRole(String roleId) {
        CommonResponseData retV = new CommonResponseData(false, "");
//        CollabRole role = roleService.getById(Long.valueOf(roleId));
//        if (DefaultTeamRole.containRoleName(role.getName())) {
//            retV.setMsg("不能删除默认角色");
//            return retV;
//        }
//
//        this.roleService.delete(role);
//        retV.setSuccess(true);
        return retV;
    }

    public CommonResponseData addRole(String modelName, String dataId, String name) {
        CommonResponseData retV = new CommonResponseData(false, "");
//        if (DefaultTeamRole.containRoleName(name)) {
//            retV.setMsg("与默认角色重名");
//            return retV;
//        }
//
//        CollabRole role = new CollabRole();
//        role.setName(name);
//        /*role.setModelName(modelName);
//        role.setDataId(dataId);*/
//        List<CollabRole> oldRoles = this.roleService.listBeansByExample(role);
//        if (oldRoles.size() > 0) {
//            retV.setMsg("与已有角色重名");
//            return retV;
//        }
//
//        this.roleService.save(role);
//        retV.setSuccess(true);
//        retV.setMsg("新增成功");
        return retV;
    }

    public CommonResponseData modifyRole(String roleId, String oldRoleName, String name) {
        CommonResponseData retV = new CommonResponseData(false, "");
//        if (DefaultTeamRole.containRoleName(oldRoleName) || DefaultTeamRole.containRoleName(name)) {
//            retV.setMsg("不能修改默认角色或新名称与默认角色重名");
//            return retV;
//        }
//
//        CollabRole role = roleService.getById(Long.valueOf(roleId));
//        role.setName(name);
//        this.roleService.update(role);
//        retV.setSuccess(true);
        return retV;
    }

    public List<CollabFunction> getCurrentUserFunctions(String modelName, String dataId) {
        User curUser = UserContextUtil.getCurrentUser();
        List<CollabRole> roles = this.roleService.list(Restrictions.eq(MODEL_NAME, modelName), Restrictions.eq(NODE_ID, dataId));


        Set<CollabFunction> userFunctions = UtilFactory.newHashSet();
//        for (CollabRole role : roles) {
//            Set<CollabRole.User> roleUsers = role.getUsers();
//
//            for (CollabRole.User roleUser : roleUsers) {
//                if (!roleUser.getId().equals(curUser.getId())) {
//                    continue;
//                }
//
//                userFunctions.addAll(role.getFunctions());
//            }
//        }
        List<CollabFunction> functions = new ArrayList<>(userFunctions);
        Collections.sort(functions);

        return functions;
    }


    /**
     * 获取用户所参与的所有工作(Project,Plan,Task),其中参与是指只要在工作的团队中就认为参与
     *
     * @param beanClz   Task,Plan,Project类名称
     * @param modelName 模型名称:PLAN,PROJECT,TASK
     * @param userId
     * @param <T>
     * @return
     */
    public <T> List<T> getParticiptionJob(Class<T> beanClz, String modelName, String userId) {

        List<T> retList = new ArrayList<>();
        List<CollabRole> roles = roleService.getCollabRoleByUserId(userId);
        Map<String, T> JobMapping = new HashMap<>();
        for (CollabRole role : roles) {
           /* if (role.getModelName().equals(modelName)) {
                String jobId = role.getDataId();
                if (null == JobMapping.get(jobId)) {
                    T bean = orientSqlEngine.getTypeMappingBmService().getById(beanClz, jobId);
                    if (bean != null) {
                        JobMapping.put(jobId, bean);
                        retList.add(bean);
                    }
                }
            }*/
        }
        return retList;
    }


    /**
     * 获取Project,Plan,Task对应的用户Id
     *
     * @param modelName
     * @param jobId
     * @return
     */
    public List<String> getJobUserIds(String modelName, String jobId) {

        List<String> ids = new ArrayList<>();
        Map<String, String> idMap = new HashMap<>();

        List<CollabRole> collabRoles = this.roleService.list(Restrictions.eq(MODEL_NAME, modelName),
                Restrictions.eq(NODE_ID, jobId));
        for (CollabRole collabRole : collabRoles) {
            List<CollabRole.User> users = this.getRoleUsers(String.valueOf(collabRole.getId()));
            for (CollabRole.User user : users) {
                if (!idMap.containsKey(user.getId())) {
                    idMap.put(user.getId(), user.getAllName());
                }
            }
        }
        for (String id : idMap.keySet()) {

            ids.add(id);
        }
        return ids;
    }

    /**
     * @param collabRoles
     * @return 角色转化为前端对象
     */
    public List<RoleUserTreeNode> converRoleToFrontData(List<CollabRole> collabRoles) {
        List<RoleUserTreeNode> retVal = new ArrayList<>();
        collabRoles.forEach(collabRole -> {
            RoleUserTreeNode roleUserTreeNode = new RoleUserTreeNode();
            roleUserTreeNode.setId("ROLE" + NODE_ID_SPLIT + collabRole.getId());
            roleUserTreeNode.setRoleId(String.valueOf(collabRole.getId()));
            roleUserTreeNode.setRoleName(collabRole.getName());
            //绑定子节点信息
            roleUserTreeNode.setChildren(converUserToFrontData(collabRole.getUsers(), String.valueOf(collabRole.getId())));
            retVal.add(roleUserTreeNode);
        });
        return retVal;
    }

    public Map<String, List<RoleFunctionTreeNode>> converFunctionToFrontData(List<CollabRole> collabRoles) {
        Map<String, List<RoleFunctionTreeNode>> retVal = new LinkedHashMap<>();
        collabRoles.forEach(collabRole -> {
            Set<CollabFunction> collabFunctions = collabRole.getFunctions();
            List<RoleFunctionTreeNode> roleFunctionTreeNodes = new ArrayList<RoleFunctionTreeNode>(collabFunctions.size());
            collabFunctions.forEach(collabFunction -> {
                RoleFunctionTreeNode roleFunctionTreeNode = convertCollabFunctionToRoleFunctionTreeNode(collabFunction, null, true);
                roleFunctionTreeNodes.add(roleFunctionTreeNode);
            });
            retVal.put(String.valueOf(collabRole.getId()), roleFunctionTreeNodes);
        });

        return retVal;
    }

    /**
     * @param users
     * @param parId
     * @return 用户转化为前端对象
     */
    private List<RoleUserTreeNode> converUserToFrontData(Set<CollabRole.User> users, String parId) {
        List<RoleUserTreeNode> retVal = new ArrayList<>();
        users.forEach(user -> {
            RoleUserTreeNode gridModel = new RoleUserTreeNode();
            gridModel.setId("ROLE_" + parId + "USER_" + user.getId());
            gridModel.setRoleId(parId);
            gridModel.setRoleName("");
            gridModel.setUserId(user.getId());
            gridModel.setUserName(user.getAllName());
            gridModel.setDeptName(CommonTools.isNullString(user.getDepId()) ? "" : this.departmentService.findById(user.getDepId()).getName());
            gridModel.setLeaf(true);
            retVal.add(gridModel);
        });
        return retVal;
    }


    /**
     * 解散团队,用于节点删除后,对应的角色及用户删除
     *
     * @param modelName
     * @param jobId
     * @return
     */
    public boolean dismissTeam(String modelName, String jobId) {
        List<CollabRole> collabRoles = this.roleService.list(Restrictions.eq(MODEL_NAME, modelName),
                Restrictions.eq(NODE_ID, jobId));
        for (CollabRole role : collabRoles) {
            this.roleService.delete(role);
        }
        return true;
    }

    /**
     * 根据对应参数来添加用户
     *
     * @param roleName    角色名称
     * @param modelName   名称
     * @param dataId      计划标示
     * @param selectedIds 要选择的用户
     * @param taskName    任务名称
     * @return
     */
    public boolean saveAssignedUsersByRolename(String roleName, String modelName, String dataId, String[] selectedIds, String taskName, StringBuilder errorMsg) {
        IBusinessModel taskBm = this.businessModelService.getBusinessModelBySName(TASK, COLLAB_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        taskBm.appendCustomerFilter(new CustomerFilter(modelName + "_" + COLLAB_SCHEMA_ID + "_ID", EnumInter.SqlOperation.Equal, dataId));
        taskBm.appendCustomerFilter(new CustomerFilter("name_" + taskBm.getId(), EnumInter.SqlOperation.Equal, taskName));
        List<Map<String, String>> tasks = this.orientSqlEngine.getBmService().createModelQuery(taskBm).list();
//        if (tasks != null) {
//            Map<String, String> task = tasks.get(0);
//            String taskId = task.get("ID");
//            CollabRole collabRole = this.collabRoleDao.getByHQL(CollabRole.class, "FROM CollabRole where dataId=" + taskId + " AND NAME='" + roleName + "' AND modelName='" + TASK + "'");
//            Set<CollabRole.User> users = collabRole.getUsers();
//            //先移除已有的人员
//            users.clear();
//            for (int i = 0; i < selectedIds.length; i++) {
//                users.add(this.collabRoleDao.get(CollabRole.User.class, selectedIds[i]));
//            }
//            this.roleService.update(collabRole);
//            return true;
//        }
        errorMsg.append("执行人添加时没有找到对应的任务记录");
        return false;
    }

    @Autowired
    private CollabRoleService roleService;
    @Autowired
    private CollabFunctionService functionService;
    @Autowired
    private ICollabRoleDao collabRoleDao;
    @Autowired
    @Qualifier("DepartmentService")
    private DepartmentService departmentService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

}
