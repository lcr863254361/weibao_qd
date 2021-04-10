package com.orient.sysmodel.service.collab;

import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.IBaseService;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-07-08 下午2:33
 */
public interface ICollabRoleService extends IBaseService<CollabRole> {

    List<CollabRole.User> getUnassignedUsers(String assignedUserIds, String userName, String departmentId);


    List<CollabRole>  getCollabRoleByUserId(String userId);
}
