package com.orient.collabdev.business.common.noderefopera.impl;

import com.orient.collabdev.business.common.annotation.NodeRefOperate;
import com.orient.collabdev.business.common.noderefopera.NodeRefOperateInterface;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.service.collab.ICollabRoleService;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.orient.sysmodel.domain.collab.CollabRole.NODE_ID;

/**
 * 删除权限相关
 *
 * @author panduanduan
 * @create 2018-08-20 10:37 AM
 */
@NodeRefOperate
public class RightSettingsRef extends AbstractNodeRefOperate implements NodeRefOperateInterface {

    @Override
    public boolean deleteNodeRefData(Boolean isUnstarted, CollabNode... nodes) {
        if (isUnstarted) {
            List<String> nodeIds = getNodeIds(nodes);
            List<CollabRole> toDelCollabRoles = this.collabRoleService.list(Restrictions.in(NODE_ID, nodeIds));
            toDelCollabRoles.forEach(toDelCollabRole -> {
                toDelCollabRole.setFunctions(null);
                toDelCollabRole.setUsers(null);
                collabRoleService.delete(toDelCollabRole);
            });
        }
        return true;
    }

    @Autowired
    private ICollabRoleService collabRoleService;
}
