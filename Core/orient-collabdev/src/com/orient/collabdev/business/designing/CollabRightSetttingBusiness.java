package com.orient.collabdev.business.designing;

import com.google.common.collect.Lists;
import com.orient.collab.business.strategy.DefaultTeamRole;
import com.orient.collab.model.AssignUserBean;
import com.orient.collab.model.RoleFunctionTreeNode;
import com.orient.collab.model.RoleUserTreeNode;
import com.orient.collabdev.business.common.privilege.node.DefaultNodeRole;
import com.orient.collabdev.constant.ProjectStageEnum;
import com.orient.sysmodel.dao.collab.ICollabRoleDao;
import com.orient.sysmodel.domain.collab.CollabFunction;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.collab.impl.CollabFunctionService;
import com.orient.sysmodel.service.collab.impl.CollabRoleService;
import com.orient.sysmodel.service.user.DepartmentService;
import com.orient.utils.CommonTools;
import com.orient.utils.PageUtil;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.util.UserContextUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import static com.orient.collab.config.CollabConstants.NODE_ID_SPLIT;
import static com.orient.collab.config.CollabConstants.ROLE_FUNCTION_TEAM;
import static com.orient.sysmodel.domain.collab.CollabRole.NODE_ID;

@Component
public class CollabRightSetttingBusiness extends BaseBusiness {

    public List<CollabRole> getRoles(String nodeId) {
        List<CollabRole> collabRoles = this.roleService.list(Restrictions.eq(NODE_ID, nodeId));
        return collabRoles;
    }


    public List<CollabFunction> getRoleFunctions(String roleId) {
        if (roleId.startsWith("\"")) {//ext's bug?
            roleId = roleId.substring(1, roleId.length() - 1);
        }
        CollabRole collabRole = this.roleService.getById(Long.valueOf(roleId));
        List<CollabFunction> functions = new ArrayList<>(collabRole.getFunctions());
        Collections.sort(functions);
        return functions;
    }

    public List<CollabRole.User> getRoleUsers(String roleId) {
        CollabRole role = this.roleService.getById(Long.valueOf(roleId));
        return new ArrayList<>(role.getUsers());
    }


    public List<RoleUserTreeNode> getRoleUserTreeModels(String nodeId, String parId) {

        List<RoleUserTreeNode> retV = UtilFactory.newArrayList();
        if (parId.startsWith("\"")) {
            parId = parId.substring(1, parId.length() - 1);
        }
        if ("-1".equals(parId)) {
            List<CollabRole> collabRoles = this.roleService.list(Restrictions.eq(NODE_ID, nodeId));
            collabRoles.forEach(collabRole -> {
                RoleUserTreeNode gridModel = new RoleUserTreeNode();
                gridModel.setId("ROLE" + NODE_ID_SPLIT + collabRole.getId());
                gridModel.setRoleId(String.valueOf(collabRole.getId()));
                gridModel.setRoleName(collabRole.getName());
                gridModel.setIconCls("icon-GROUP");
                if (DefaultTeamRole.containRoleName(collabRole.getName())) {
                    gridModel.setDefaultRole(true);
                }
                retV.add(gridModel);
            });
        } else {
            CollabRole collabRole = this.roleService.getById(Long.valueOf(parId));
            Set<CollabRole.User> users = collabRole.getUsers();
            String finalParId = parId;
            users.forEach(user -> {
                RoleUserTreeNode gridModel = new RoleUserTreeNode();
                gridModel.setId("ROLE_" + finalParId + "USER_" + user.getId());
                gridModel.setRoleId(String.valueOf(collabRole.getId()));
                gridModel.setRoleName("");
                gridModel.setUserId(user.getId());
                gridModel.setUserName(user.getAllName());
                gridModel.setDeptName(CommonTools.isNullString(user.getDepId()) ? "" : this.departmentService.findById(user.getDepId()).getName());
                gridModel.setIconCls("icon-PEOPLE");
                gridModel.setLeaf(true);
                retV.add(gridModel);
            });
        }

        return retV;
    }

    public List<RoleFunctionTreeNode> getRoleFunctionTreeNodes(String roleId, String node) {
        if ("-1".equalsIgnoreCase(node)) {
            List<RoleFunctionTreeNode> nodes = UtilFactory.newArrayList();
            List<CollabFunction> allFunctions = this.functionService.list();
            List<CollabFunction> roleFunctions = this.getRoleFunctions(roleId);
            allFunctions.forEach(collabFunction -> nodes.add(convertCollabFunctionToRoleFunctionTreeNode(collabFunction, roleFunctions, false)));
            //分组
            RoleFunctionTreeNode designGroup = new RoleFunctionTreeNode("-2", "项目设计", true, false, "icon-group");
            designGroup.setResults(nodes.stream().filter(fnode -> ProjectStageEnum.DESIGNING.toString().equalsIgnoreCase(fnode.getStage())).collect(Collectors.toList()));
            RoleFunctionTreeNode processGroup = new RoleFunctionTreeNode("-3", "项目运行", true, false, "icon-group");
            processGroup.setResults(nodes.stream().filter(fnode -> ProjectStageEnum.PROCESSING.toString().equalsIgnoreCase(fnode.getStage())).collect(Collectors.toList()));
            return Lists.newArrayList(designGroup, processGroup);
        } else
            return new ArrayList<>();

    }

    public CommonResponseData saveAssignFunctions(String roleId, String functionIds) {
        CommonResponseData retV = new CommonResponseData(true, "");
        CollabRole collabRole = this.roleService.getById(Long.valueOf(roleId));
        List<CollabFunction> functions = StringUtil.isEmpty(functionIds) ? UtilFactory.newArrayList() :
                this.functionService.list(Restrictions.in("id", CommonTools.stringToList(functionIds)));
        if (collabRole.getName().equals(DefaultTeamRole.Leader.toString())) {
            MutableBoolean hasTeamFunction = new MutableBoolean(false);
            functions.forEach(function -> {
                if (function.getName().equals(ROLE_FUNCTION_TEAM)) {
                    hasTeamFunction.setValue(true);
                }
            });
            if (!(Boolean) hasTeamFunction.getValue()) {
                return new CommonResponseData(false, "领导角色工作组功能点必须保留!");
            }
        }
        collabRole.getFunctions().clear();
        collabRole.getFunctions().addAll(new HashSet<>(functions));
        this.roleService.update(collabRole);
        return retV;
    }

    public ExtGridData<AssignUserBean> getUsersToAssign(String roleId, boolean assigned, int page, int limit, AssignUserBean userFilter) {
        ExtGridData<AssignUserBean> retV = new ExtGridData<>();


        List<CollabRole.User> assignedUsers = UtilFactory.newArrayList();
        if (!CommonTools.isNullString(roleId)) {
            assignedUsers = getRoleUsers(roleId);
        }

        List<CollabRole.User> users;
        if (assigned) {
            users = assignedUsers;
        } else {
            StringBuilder assignedUserIds = new StringBuilder();
            for (CollabRole.User assignedUser : assignedUsers) {
                assignedUserIds.append(assignedUser.getId()).append(",");
            }
            if (assignedUserIds.length() > 0) {
                assignedUserIds.deleteCharAt(assignedUserIds.length() - 1);
            }
            users = this.roleService.getUnassignedUsers(assignedUserIds.toString(), userFilter.getUserName(), userFilter.getDepartment());
        }

        List<CollabRole.User> subList = PageUtil.page(users, page, limit);
        List<AssignUserBean> results = new ArrayList<>();
        subList.forEach(user -> {
            AssignUserBean userBean = new AssignUserBean();
            try {
                PropertyUtils.copyProperties(userBean, user);
                userBean.setDepartment(CommonTools.isNullString(user.getDepId()) ? "" : this.departmentService.findById(user.getDepId()).getName());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            results.add(userBean);
        });

        retV.setResults(results);
        retV.setTotalProperty(users.size());
        return retV;
    }

    private RoleFunctionTreeNode convertCollabFunctionToRoleFunctionTreeNode(CollabFunction collabFunction, List<CollabFunction> roleFunctions, Boolean defaultChecked) {
        RoleFunctionTreeNode node = new RoleFunctionTreeNode();
        node.setId(String.valueOf(collabFunction.getId()));
        node.setText(collabFunction.getName());
        node.setIconCls(collabFunction.getIconCls());
        node.setStage(collabFunction.getStage());
        if (CommonTools.isEmptyList(roleFunctions)) {
            node.setChecked(defaultChecked);
        } else {
            for (CollabFunction roleFunction : roleFunctions) {
                if (roleFunction.getId().equals(collabFunction.getId())) {
                    node.setChecked(true);
                    break;
                }
            }
        }
        node.setLeaf(true);
        return node;
    }

    public boolean saveAssignedUsers(String roleId, String[] selectedIds, String direction, StringBuilder errorMsg) {
        CollabRole collabRole = this.roleService.getById(Long.valueOf(roleId));
        Set<CollabRole.User> roleUsers = collabRole.getUsers();

        if ("left".equals(direction)) {
            for (String selectedId : selectedIds) {
                Iterator<CollabRole.User> itor = roleUsers.iterator();
                while (itor.hasNext()) {
                    if (itor.next().getId().equals(selectedId)) {
                        itor.remove();
                    }
                }
            }
        } else {
            for (String selectedId : selectedIds) {
                CollabRole.User user = this.collabRoleDao.get(CollabRole.User.class, selectedId);
                roleUsers.add(user);
            }
        }

        if (collabRole.getName().equals(DefaultTeamRole.Executor.toString())) {
            if (collabRole.getUsers().size() <= 0) {
                errorMsg.append("应至少保留一个执行人!");
                return false;
            }
        }
        this.roleService.update(collabRole);
        return true;
    }

    public CommonResponseData deleteRole(String roleId) {
        CommonResponseData retV = new CommonResponseData(false, "");
        CollabRole role = roleService.getById(Long.valueOf(roleId));
        if (DefaultTeamRole.containRoleName(role.getName())) {
            retV.setMsg("不能删除默认角色");
            return retV;
        }

        this.roleService.delete(role);
        retV.setSuccess(true);
        return retV;
    }

    public CommonResponseData addRole(String nodeId, String name) {
        CommonResponseData retV = new CommonResponseData(false, "");
        if (DefaultTeamRole.containRoleName(name)) {
            retV.setMsg("与默认角色重名");
            return retV;
        }

        CollabRole role = new CollabRole();
        role.setName(name);
        role.setNodeId(nodeId);
        List<CollabRole> oldRoles = this.roleService.listBeansByExample(role);
        if (oldRoles.size() > 0) {
            retV.setMsg("与已有角色重名");
            return retV;
        }

        this.roleService.save(role);
        retV.setSuccess(true);
        retV.setMsg("新增成功");
        return retV;
    }

    public CommonResponseData modifyRole(String roleId, String oldRoleName, String name) {
        CommonResponseData retV = new CommonResponseData(false, "");
        if (DefaultTeamRole.containRoleName(oldRoleName) || DefaultTeamRole.containRoleName(name)) {
            retV.setMsg("不能修改默认角色或新名称与默认角色重名");
            return retV;
        }

        CollabRole role = roleService.getById(Long.valueOf(roleId));
        role.setName(name);
        this.roleService.update(role);
        retV.setSuccess(true);
        return retV;
    }

    public List<CollabFunction> getCurrentUserFunctions(String dataId) {
        User curUser = UserContextUtil.getCurrentUser();
        List<CollabRole> roles = this.roleService.list(Restrictions.eq(NODE_ID, dataId));


        Set<CollabFunction> userFunctions = UtilFactory.newHashSet();
        for (CollabRole role : roles) {
            Set<CollabRole.User> roleUsers = role.getUsers();

            for (CollabRole.User roleUser : roleUsers) {
                if (!roleUser.getId().equals(curUser.getId())) {
                    continue;
                }

                userFunctions.addAll(role.getFunctions());
            }
        }
        List<CollabFunction> functions = new ArrayList<>(userFunctions);
        Collections.sort(functions);

        return functions;
    }

    /**
     * create role
     *
     * @param assigner
     * @param nodeId
     */
    public void createDefaultRoles(String assigner, String nodeId) {
        DefaultNodeRole[] defaultNodeRoles = DefaultNodeRole.values();
        for (DefaultNodeRole defaultNodeRole : defaultNodeRoles) {
            defaultNodeRole.create(assigner, nodeId);
        }
    }

    public void replaceAssign(String nodeId, String originalAssign, String targetAssign, boolean recursive) {
        DefaultNodeRole[] defaultNodeRoles = DefaultNodeRole.values();
        for (DefaultNodeRole defaultNodeRole : defaultNodeRoles) {
            defaultNodeRole.replaceAssign(nodeId, originalAssign, targetAssign, recursive);
        }
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
