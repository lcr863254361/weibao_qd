package com.orient.collab.business.strategy;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collab.business.projectCore.cmd.CommandService;
import com.orient.collab.business.projectCore.cmd.concrete.GetAllSubPlansCmd;
import com.orient.collab.business.projectCore.cmd.concrete.GetChildTaskIdsCascade;
import com.orient.collab.model.Plan;
import com.orient.collab.model.Project;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.sysmodel.domain.user.UserDAO;
import com.orient.sysmodel.service.collab.impl.CollabRoleService;
import com.orient.utils.CommonTools;
import com.orient.utils.Pair;
import com.orient.utils.UtilFactory;
import org.hibernate.criterion.Restrictions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.collab.config.CollabConstants.*;
import static com.orient.sysmodel.domain.collab.CollabRole.NODE_ID;
import static com.orient.sysmodel.domain.collab.CollabRole.MODEL_NAME;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-07-14 上午10:27
 */
public enum DefaultTeamRole {

    Candidate(ROLE_CANDIDATE) {
        @Override
        public Set<String> getDefaultUserIds(ProjectTreeNodeStrategy nodeStrategy, IBusinessModel businessModel, Map<String, String> dataMap) {
            Set<String> userIds = UtilFactory.newHashSet();
            return userIds;
        }

        @Override
        public void updateRoleUsers(IBusinessModel businessModel, String dataId, CollabRole collabRole, String newUserIds) {
            return;
        }
    }, Executor(ROLE_EXECUTOR) {
        @Override
        public Set<String> getDefaultUserIds(ProjectTreeNodeStrategy nodeStrategy, IBusinessModel businessModel, Map<String, String> dataMap) {
            Set<String> userIds = UtilFactory.newHashSet();
            String principalIds = CommonTools.Obj2String(dataMap.get("PRINCIPAL_" + businessModel.getId()));
            if (!CommonTools.isNullString(principalIds)) {
                String[] principals = principalIds.split(USERID_SPERATOR);
                userIds.addAll(Arrays.asList(principals));
            }
            return userIds;
        }

        @Override
        public void updateRoleUsers(IBusinessModel businessModel, String dataId, CollabRole collabRole, String newUserIds) {
            if (CommonTools.isNullString(newUserIds)) {
                return;
            }

            UserDAO userDAO = OrientContextLoaderListener.Appwac.getBean(UserDAO.class);

            String[] userIdArray = newUserIds.split(USERID_SPERATOR);

            List<CollabRole.User> newUsers = UtilFactory.newArrayList();
            for (String userId : userIdArray) {
                CollabRole.User newUser = userDAO.get(CollabRole.User.class, userId);
                newUsers.add(newUser);
                collabRole.getUsers().add(newUser);
            }

            CollabRoleService collabRoleService = OrientContextLoaderListener.Appwac.getBean(CollabRoleService.class);
            collabRoleService.update(collabRole);

            ISqlEngine sqlEngine = OrientContextLoaderListener.Appwac.getBean(ISqlEngine.class);
            CommandService commandService = OrientContextLoaderListener.Appwac.getBean(CommandService.class);

            List<Plan> subPlans = UtilFactory.newArrayList();
            if (businessModel.getMatrix().getName().equals(PROJECT)) {
                Project project = sqlEngine.getTypeMappingBmService().getById(Project.class, dataId);
                try {
                    subPlans = commandService.execute(new GetAllSubPlansCmd(project, sqlEngine));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (businessModel.getMatrix().getName().equals(PLAN)) {
                Plan plan = sqlEngine.getTypeMappingBmService().getById(Plan.class, dataId);
                try {
                    subPlans = commandService.execute(new GetAllSubPlansCmd(plan, sqlEngine));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            List<String> subPlanIds = UtilFactory.newArrayList();
            for (Plan subPlan : subPlans) {
                subPlanIds.add(subPlan.getId());
            }
            if (subPlanIds.size() > 0) {
                List<CollabRole> collabRoles = collabRoleService.list(
                        Restrictions.eq(MODEL_NAME, PLAN), Restrictions.in(NODE_ID, subPlanIds),
                        Restrictions.eq(CollabRole.NAME, Leader.toString()));
                for (CollabRole role : collabRoles) {
                    role.getUsers().addAll(newUsers);
                    collabRoleService.update(role);
                }
            }
            if (businessModel.getMatrix().getName().equals(PLAN)) {
                subPlanIds.add(dataId);
            }
            try {
                List<String> subTaskIds = UtilFactory.newArrayList();
                if (businessModel.getMatrix().getName().equals(PLAN)) {
                    subTaskIds = commandService.execute(new GetChildTaskIdsCascade(PLAN, CommonTools.listJoinCommaToString(subPlanIds), null));
                } else if (businessModel.getMatrix().getName().equals(TASK)) {
                    subTaskIds = commandService.execute(new GetChildTaskIdsCascade(TASK, dataId, null));
                }

                if (subTaskIds.size() > 0) {
                    List<CollabRole> collabRoles = collabRoleService.list(
                            Restrictions.eq(MODEL_NAME, TASK), Restrictions.in(NODE_ID, subTaskIds),
                            Restrictions.eq(CollabRole.NAME, Leader.toString()));
                    for (CollabRole role : collabRoles) {
                        role.getUsers().addAll(newUsers);
                        collabRoleService.update(role);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return;
        }
    }, Team(ROLE_TEAM) {
        @Override
        public Set<String> getDefaultUserIds(ProjectTreeNodeStrategy nodeStrategy, IBusinessModel businessModel, Map<String, String> dataMap) {
            Set<String> userIds = UtilFactory.newHashSet();
            return userIds;
        }

        @Override
        public void updateRoleUsers(IBusinessModel businessModel, String dataId, CollabRole collabRole, String newUserIds) {
            return;
        }
    }, Leader(ROLE_LEADER) {
        @Override
        public Set<String> getDefaultUserIds(ProjectTreeNodeStrategy nodeStrategy, IBusinessModel businessModel, Map<String, String> dataMap) {
            Set<String> userIds = UtilFactory.newHashSet();

            if (nodeStrategy == ProjectTreeNodeStrategy.DirNode) {
                return userIds;
            }
            Pair<ProjectTreeNodeStrategy, Map<String, String>> parentNodeData = nodeStrategy.getParentNodeData(dataMap);
            while (parentNodeData != null && parentNodeData.fst != ProjectTreeNodeStrategy.DirNode) {
                String modelName = parentNodeData.fst.toString();
                IBusinessModel parentModel = bmService.getBusinessModelBySName(modelName, COLLAB_SCHEMA_ID, Table);
                String userId = CommonTools.Obj2String(parentNodeData.snd.get("PRINCIPAL_" + parentModel.getId()));
                if (!CommonTools.isNullString(userId)) {
                    userIds.add(userId);
                }
                parentNodeData = parentNodeData.fst.getParentNodeData(parentNodeData.snd);
            }
            return userIds;
        }

        @Override
        public void updateRoleUsers(IBusinessModel businessModel, String dataId, CollabRole collabRole, String newUserIds) {
            return;
        }
    };

    DefaultTeamRole(String roleName) {
        this.roleName = roleName;
    }

    private final String roleName;

    @Override
    public String toString() {
        return roleName;
    }

    abstract public Set<String> getDefaultUserIds(ProjectTreeNodeStrategy nodeStrategy, IBusinessModel businessModel, Map<String, String> dataMap);

    abstract public void updateRoleUsers(IBusinessModel businessModel, String dataId, CollabRole collabRole, String newUserIds);

    private static final Map<String, DefaultTeamRole> stringToEnum = UtilFactory.newHashMap();

    static {
        for (DefaultTeamRole s : values()) {
            stringToEnum.put(s.toString(), s);
        }
    }

    public static DefaultTeamRole fromString(String roleName) {
        return stringToEnum.get(roleName);
    }

    public static boolean containRoleName(String roleName) {
        Set<String> defaultRoleNames = stringToEnum.keySet();
        return defaultRoleNames.contains(roleName);
    }

    protected IBusinessModelService bmService = OrientContextLoaderListener.Appwac.getBean(IBusinessModelService.class);
}
