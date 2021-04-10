package com.orient.collab.event;

import com.orient.modeldata.event.SaveModelDataEvent;

/**
 * ProjectTreeNodeCreatedOrUpdateEvent
 *
 * @author Seraph
 *         2016-07-14 下午2:57
 */
public class ProjectTreeNodeCreatedEvent extends SaveModelDataEvent {

    public ProjectTreeNodeCreatedEvent(Object source, ProjectTreeNodeCreatedEventParam params) {
        super(source, params);
    }
}
