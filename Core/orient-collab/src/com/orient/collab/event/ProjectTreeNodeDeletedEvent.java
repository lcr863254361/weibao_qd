package com.orient.collab.event;

import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-07-15 上午10:47
 */
public class ProjectTreeNodeDeletedEvent extends OrientEvent {

    public ProjectTreeNodeDeletedEvent(Object source, ProjectTreeNodeDeletedEventParam params) {
        super(source, params);
    }
}
