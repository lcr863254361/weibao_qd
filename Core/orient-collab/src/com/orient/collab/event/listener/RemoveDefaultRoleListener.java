package com.orient.collab.event.listener;

import com.orient.collab.event.ProjectTreeNodeDeletedEvent;
import com.orient.collab.event.ProjectTreeNodeDeletedEventParam;
import com.orient.sysmodel.domain.collab.CollabRole;
import com.orient.sysmodel.service.collab.ICollabRoleService;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.orient.sysmodel.domain.collab.CollabRole.NODE_ID;
import static com.orient.sysmodel.domain.collab.CollabRole.MODEL_NAME;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-07-15 上午10:51
 */
@Component
public class RemoveDefaultRoleListener extends OrientEventListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return ProjectTreeNodeDeletedEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }

        OrientEvent orientEvent = (OrientEvent) event;
        ProjectTreeNodeDeletedEventParam param = (ProjectTreeNodeDeletedEventParam) orientEvent.getParams();

        if (!param.getTreeDeleteResult().getDeleteSuccess()) {
            orientEvent.aboardEvetn();
            return;
        }

        param.getTreeDeleteResult().getTreeDeleteTargets().forEach(treeDeleteTarget -> {
            List<CollabRole> toDelCollabRoles = this.collabRoleService.list(Restrictions.eq(MODEL_NAME, treeDeleteTarget.getModelName()), Restrictions.eq(NODE_ID, treeDeleteTarget.getDataId()));
            toDelCollabRoles.forEach(toDelCollabRole -> {
                toDelCollabRole.setFunctions(null);
                toDelCollabRole.setUsers(null);
                collabRoleService.delete(toDelCollabRole);
            });
        });
    }

    @Autowired
    private ICollabRoleService collabRoleService;
}
