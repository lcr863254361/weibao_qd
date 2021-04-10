package com.orient.template.business.core.support.impl;

import com.orient.collab.business.strategy.DefaultTeamRole;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Project;
import com.orient.collab.model.RoleFunctionTreeNode;
import com.orient.collab.model.RoleUserTreeNode;
import com.orient.sysmodel.dao.collab.ICollabRoleDao;
import com.orient.sysmodel.domain.collab.CollabFunction;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.collab.impl.CollabFunctionService;
import com.orient.sysmodel.service.collab.impl.CollabRoleService;
import com.orient.sysmodel.service.user.DepartmentService;
import com.orient.template.business.core.support.TemplateNodeImportHelper;
import com.orient.template.business.core.support.TemplateSupport;
import com.orient.template.model.CollabTeam;
import com.orient.template.model.CollabTemplatePreviewNode;
import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;
import com.orient.web.util.UserContextUtil;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

import static com.orient.collab.config.CollabConstants.*;
import static com.orient.sysmodel.domain.collab.CollabFunction.BELONGED_MODEL;
import static com.orient.sysmodel.domain.collab.CollabRole.NODE_ID;
import static com.orient.sysmodel.domain.collab.CollabRole.MODEL_NAME;

/**
 * support for collab team's template operation
 *
 * @author Seraph
 * 2016-09-30 下午1:04
 */
@Component
public class CollabTeamTemplateSupport implements TemplateSupport<CollabTeam> {

    /**
     * we suppose the function of model keep stable
     *
     * @param currentNode   current node to import
     * @param dependentNode if current node is a <b>direct</b> relation node of another node, this will be the dependent node, otherwise this will be null
     * @param importHelper  a helper object that provide some methods that may be used
     * @return
     */
    @Override
    public boolean importNode(CollabTemplateNode currentNode, CollabTemplateNode dependentNode, TemplateNodeImportHelper importHelper) {
        CollabTeam collabTeam = (CollabTeam) currentNode.getData();

        List<CollabRole> collabRoles = collabTeam.getRoles();
        for (CollabRole collabRole : collabRoles) {
            Set<CollabRole.User> roleUsers = UtilFactory.newHashSet();
            collabRole.setUsers(roleUsers);

            CollabTemplateNode ref = currentNode.getIndependentCompRef();
            collabRole.setId(null);
            //collabRole.setDataId(ref.getNewDataId());
            if (ref.getParent() == null && collabRole.getName().equals(DefaultTeamRole.Executor.toString())) {
                addCurrentUserToTeam(collabRole);
            }

            if (ref.getParent() != null && collabRole.getName().equals(DefaultTeamRole.Leader.toString())) {
                //addCurrentUserToTeam(collabRole);
            }

            Set<CollabFunction> roleFunctions = UtilFactory.newHashSet();
            for (Long functionId : collabRole.getFunctionIds()) {
                roleFunctions.add(this.functionService.getById(functionId));
            }


            for (String userId : collabRole.getUserIds()) {
                CollabRole.User user = this.collabRoleDao.getBy(CollabRole.User.class, Restrictions.eq("id", userId));
                roleUsers.add(user);
            }

            collabRole.setFunctions(roleFunctions);
            this.roleService.save(collabRole);
        }
        return false;
    }

    private void addCurrentUserToTeam(CollabRole collabRole) {
        User currentUser = UserContextUtil.getCurrentUser();

        if (!collabRole.getUserIds().contains(currentUser.getId())) {
            CollabRole.User user = this.collabRoleDao.getBy(CollabRole.User.class, Restrictions.eq("id", currentUser.getId()));
            collabRole.getUsers().add(user);
        }
    }

    @Override
    public CollabTemplateNode exportNode(CollabTeam node, MutableBoolean dataSetted, List<Serializable> childrenData, List<Serializable> independentComponentsData, List<Serializable> relationComponentsData) {
        CollabTemplateNode templateNode = new CollabTemplateNode();
        templateNode.setData(node);
        templateNode.setType(node.getClass().getName());


        List<CollabRole> collabRoles = this.roleService.list(Restrictions.eq(MODEL_NAME, node.getBelongedModelName()),
                Restrictions.eq(NODE_ID, node.getBelongedDataId()));

        for (CollabRole collabRole : collabRoles) {
            Set<CollabFunction> collabFunctions = collabRole.getFunctions();
            for (CollabFunction collabFunction : collabFunctions) {

            }

            Set<CollabRole.User> persistentUserSet = collabRole.getUsers();
            for (CollabRole.User user : persistentUserSet) {
                collabRole.getUserIds().add(user.getId());
            }

        }
        node.setRoles(collabRoles);

        List<CollabFunction> allFunctions = this.functionService.list(Restrictions.eq(BELONGED_MODEL, node.getBelongedModelName()));
        node.setFunctions(allFunctions);
        return templateNode;
    }

    @Override
    public CollabTemplatePreviewNode convertTemplateNodeToPreviewNode(CollabTemplateNode node, String previewType) {
        CollabTemplatePreviewNode collabTemplatePreviewNode = new CollabTemplatePreviewNode(node, previewType);

        node.convertSerialBytesToData();
        collabTemplatePreviewNode.addExtraInfo("roleUser", getPreviewTeamRoleUserInfo(node));
        collabTemplatePreviewNode.addExtraInfo("roleFunction", getPreviewTeamRoleFunctionInfo(node));
        return collabTemplatePreviewNode;
    }

    private List<RoleUserTreeNode> getPreviewTeamRoleUserInfo(CollabTemplateNode node) {

        CollabTeam collabTeam = (CollabTeam) node.getData();

        List<RoleUserTreeNode> retV = UtilFactory.newArrayList();
        List<CollabRole> collabRoles = collabTeam.getRoles();
        for (CollabRole collabRole : collabRoles) {
            RoleUserTreeNode roleModel = new RoleUserTreeNode();
            roleModel.setId("ROLE" + NODE_ID_SPLIT + collabRole.getId());
            roleModel.setRoleId(String.valueOf(collabRole.getId()));
            roleModel.setRoleName(collabRole.getName());
            retV.add(roleModel);

            Set<String> userIds = collabRole.getUserIds();
            List<RoleUserTreeNode> children = UtilFactory.newArrayList();
            for (String userId : userIds) {
                RoleUserTreeNode userModel = new RoleUserTreeNode();
                userModel.setId("ROLE_" + collabRole.getId() + "USER_" + userId);
                userModel.setRoleId(String.valueOf(collabRole.getId()));
                userModel.setRoleName("");
                userModel.setUserId(userId);

                CollabRole.User user = this.collabRoleDao.getBy(CollabRole.User.class, Restrictions.eq("id", userId));
                userModel.setUserName(user.getAllName());
                userModel.setDeptName(CommonTools.isNullString(user.getDepId()) ? "" : this.departmentService.findById(user.getDepId()).getName());
                userModel.setLeaf(true);
                children.add(userModel);
            }
            roleModel.setChildren(children);
        }

        return retV;
    }

    private Map<String, List<RoleFunctionTreeNode>> getPreviewTeamRoleFunctionInfo(CollabTemplateNode node) {
        Map<String, List<RoleFunctionTreeNode>> retV = UtilFactory.newHashMap();

        CollabTemplateNode refNode = node.getIndependentCompRef();
        refNode.convertSerialBytesToData();
        Serializable refData = refNode.getData();

        String modelName;
        if (refData instanceof Project) {
            modelName = PROJECT;
        } else if (refData instanceof Plan) {
            modelName = PLAN;
        } else {
            modelName = TASK;
        }

        List<CollabFunction> allFunctions = this.functionService.list(Restrictions.eq(BELONGED_MODEL, modelName));

        CollabTeam collabTeam = (CollabTeam) node.getData();
        List<CollabRole> collabRoles = collabTeam.getRoles();
        for (CollabRole collabRole : collabRoles) {
            List<RoleFunctionTreeNode> roleFunctions = UtilFactory.newArrayList();

            for (CollabFunction collabFunction : allFunctions) {
                roleFunctions.add(convertCollabFunctionToRoleFunctionTreeNode(collabFunction, collabRole.getFunctionIds()));
            }
            retV.put(String.valueOf(collabRole.getId()), roleFunctions);
        }

        return retV;
    }

    private RoleFunctionTreeNode convertCollabFunctionToRoleFunctionTreeNode(CollabFunction collabFunction, List<Long> roleFunctionIds) {
        RoleFunctionTreeNode node = new RoleFunctionTreeNode();
        node.setId(String.valueOf(collabFunction.getId()));
        node.setText(collabFunction.getName());

        for (Long roleFunctionId : roleFunctionIds) {
            if (roleFunctionId.equals(collabFunction.getId())) {
                node.setChecked(true);
                break;
            }
        }
        node.setLeaf(true);
        return node;
    }

    @Autowired
    @Qualifier("DepartmentService")
    private DepartmentService departmentService;

    @Autowired
    private ICollabRoleDao collabRoleDao;
    @Autowired
    private CollabRoleService roleService;
    @Autowired
    private CollabFunctionService functionService;
}
