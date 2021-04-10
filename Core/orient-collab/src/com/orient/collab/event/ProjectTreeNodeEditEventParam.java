package com.orient.collab.event;

import com.orient.modeldata.eventParam.SaveModelDataEventParam;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-08-18 下午4:19
 */
public class ProjectTreeNodeEditEventParam extends SaveModelDataEventParam {

    private String originalUserId;

    public String getOriginalUserId() {
        return originalUserId;
    }

    public void setOriginalUserId(String originalUserId) {
        this.originalUserId = originalUserId;
    }
}
