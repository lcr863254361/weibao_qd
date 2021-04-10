package com.orient.collab.event;

import com.orient.web.base.OrientEventBus.OrientEventParams;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-07-14 下午2:59
 */
public class ProjectTreeNodeEventParam extends OrientEventParams {

    public ProjectTreeNodeEventParam(String modelName, String dataId){
        this.modelName = modelName;
        this.dataId = dataId;
    }
    private final String modelName;
    private final String dataId;
}
