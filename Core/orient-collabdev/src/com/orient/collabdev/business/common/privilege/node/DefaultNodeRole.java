package com.orient.collabdev.business.common.privilege.node;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.business.structure.tool.IModelAndNodeHelper;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysmodel.dao.collab.ICollabRoleDao;
import com.orient.sysmodel.domain.collab.CollabFunction;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.sysmodel.domain.collabdev.CollabNodeWithRelation;
import com.orient.sysmodel.service.collab.ICollabFunctionService;
import com.orient.sysmodel.service.collab.ICollabRoleService;
import com.orient.sysmodel.service.collabdev.ICollabNodeWithRelationService;
import com.orient.utils.CommonTools;
import com.orient.utils.Pair;
import org.hibernate.criterion.Restrictions;

import java.util.*;

import static com.orient.collabdev.constant.CollabDevConstants.DEFAULT_ROLE_OWNER;
import static com.orient.collabdev.constant.CollabDevConstants.DEFAULT_ROLE_OWNGROUP;
import static com.orient.sysmodel.domain.collab.CollabRole.NAME;
import static com.orient.sysmodel.domain.collab.CollabRole.NODE_ID;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-21 9:27 AM
 */
public enum DefaultNodeRole implements DefaultRoleInterface {

    DEFAULT_ROLE(DEFAULT_ROLE_OWNER) {
        @Override
        public void create(String userIds, String nodeId) {
            createDefaultRole(this.toString(), userIds, nodeId);
        }

        @Override
        public void replaceAssign(String nodeId, String originalAssign, String targetAssign, boolean recursive) {
            replaceCurrentAssign(this.toString(), nodeId, originalAssign, targetAssign, true);
        }

    }, DEFAULT_GROUP(DEFAULT_ROLE_OWNGROUP) {
        @Override
        public void create(String userIds, String nodeId) {
            CollabRole collabRole = createDefaultRole(this.toString(), userIds, nodeId);
            //增加默认父亲节点执行人
            appendParentAsisgner(collabRole);
        }

        @Override
        public void replaceAssign(String nodeId, String originalAssign, String targetAssign, boolean recursive) {
            boolean removeOriginal = calculateRemoveOriginalResult(nodeId, originalAssign);
            replaceCurrentAssign(this.toString(), nodeId, originalAssign, targetAssign, removeOriginal);
            if (recursive) {
                replaceChildrenGroupAssign(nodeId, originalAssign, targetAssign);
            }
        }

        private void replaceChildrenGroupAssign(String nodeId, String originalAssign, String targetAssign) {
            CollabNodeWithRelation collabNodeWithRelation = collabNodeWithRelationService.getById(nodeId);
            List<CollabNodeWithRelation> children = collabNodeWithRelation.getChildren();
            if (!CommonTools.isEmptyList(children)) {
                children.forEach(cbnr -> {
                    boolean removeOriginal = calculateRemoveOriginalResult(cbnr.getId(), originalAssign);
                    replaceCurrentAssign(this.toString(), cbnr.getId(), originalAssign, targetAssign, removeOriginal);
                    if (removeOriginal) {
                        replaceChildrenGroupAssign(cbnr.getId(), originalAssign, targetAssign);
                    }
                });
            }
        }

        private void appendParentAsisgner(CollabRole collabRole) {
            Set<CollabRole.User> users = collabRole.getUsers();
            List<Pair<IBusinessModel, List<Map<String, String>>>> parentModelAndDatas = modelAndNodeHelper.getParentModelAndIds(collabRole.getNodeId());
            String asisgnKey = "PRINCIPAL_";
            parentModelAndDatas.forEach(pair -> {
                IBusinessModel businessModel = pair.fst;
                String businessModelId = businessModel.getId();
                List<Map<String, String>> queryList = pair.snd;
                queryList.forEach(dataMap -> {
                    String assigner = dataMap.get(asisgnKey + businessModelId);
                    CollabRole.User user = collabRoleDao.get(CollabRole.User.class, assigner);
                    if (null != user) {
                        users.add(user);
                    }
                });
            });
            this.roleService.update(collabRole);
        }
    };

    /**
     * 计算是否需要移除用户
     *
     * @param nodeId
     * @return
     */
    protected boolean calculateRemoveOriginalResult(String nodeId, String originalAssigner) {
        List<Pair<IBusinessModel, List<Map<String, String>>>> parentModelAndDatas = modelAndNodeHelper.getParentModelAndIds(nodeId);
        String asisgnKey = "PRINCIPAL_";
        Set<String> assigners = new HashSet<>();
        parentModelAndDatas.forEach(pair -> {
            IBusinessModel businessModel = pair.fst;
            String businessModelId = businessModel.getId();
            List<Map<String, String>> queryList = pair.snd;
            queryList.forEach(dataMap -> {
                String assigner = dataMap.get(asisgnKey + businessModelId);
                assigners.add(assigner);
            });
        });
        return !assigners.contains(originalAssigner);
    }

    protected CollabRole replaceCurrentAssign(String roleName, String nodeId, String originalAssign, String targetAssign, Boolean removeOriginal) {
        List<CollabRole> roles = roleService.list(Restrictions.eq(NODE_ID, nodeId), Restrictions.eq(NAME, roleName));
        CollabRole role = null;
        if (!CommonTools.isEmptyList(roles) && roles.size() == 1) {
            role = roles.get(0);
            Set<CollabRole.User> roleUsers = role.getUsers();
            String[] originalArray = originalAssign.split(",");
            String[] targetArray = targetAssign.split(",");
            if (removeOriginal) {
                for (String userId : originalArray) {
                    Iterator<CollabRole.User> itor = roleUsers.iterator();
                    while (itor.hasNext()) {
                        if (itor.next().getId().equals(userId)) {
                            itor.remove();
                        }
                    }
                }
            }
            for (String userId : targetArray) {
                CollabRole.User user = this.collabRoleDao.get(CollabRole.User.class, userId);
                roleUsers.add(user);
            }

            this.roleService.update(role);
        }
        return role;
    }

    protected CollabRole createDefaultRole(String roleName, String assigner, String nodeId) {
        CollabRole.User user = collabRoleDao.get(CollabRole.User.class, assigner);
        CollabRole role = new CollabRole();
        role.setName(roleName);
        role.setNodeId(nodeId);
        //role user
        Set<CollabRole.User> roleUsers = role.getUsers();
        roleUsers.add(user);
        //role function
        List<CollabFunction> functionList = functionService.list();
        role.getFunctions().addAll(new HashSet<>(functionList));
        this.roleService.save(role);
        return role;
    }

    private String name;

    DefaultNodeRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static final Map<String, DefaultNodeRole> stringToEnum = new HashMap<>();

    static {
        for (DefaultNodeRole s : values()) {
            stringToEnum.put(s.toString(), s);
        }
    }

    public static DefaultNodeRole fromString(String name) {
        return stringToEnum.get(name);
    }

    //spring refrence
    ICollabRoleService roleService = OrientContextLoaderListener.Appwac.getBean(ICollabRoleService.class);
    ICollabFunctionService functionService = OrientContextLoaderListener.Appwac.getBean(ICollabFunctionService.class);
    ICollabRoleDao collabRoleDao = OrientContextLoaderListener.Appwac.getBean(ICollabRoleDao.class);
    ICollabNodeWithRelationService collabNodeWithRelationService = OrientContextLoaderListener.Appwac.getBean(ICollabNodeWithRelationService.class);
    IModelAndNodeHelper modelAndNodeHelper = OrientContextLoaderListener.Appwac.getBean(IModelAndNodeHelper.class);
}
