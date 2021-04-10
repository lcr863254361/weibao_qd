package com.orient.collab.event;

import com.orient.modeldata.event.UpdateModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-08-18 下午4:18
 */
public class ProjectTreeNodeEditEvent extends UpdateModelDataEvent{

    public ProjectTreeNodeEditEvent(Object source, ProjectTreeNodeEditEventParam eventParam) {
        super(source, eventParam);
    }
}
