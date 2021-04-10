package com.orient.collabdev.business.version.event;

import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.sysmodel.domain.collabdev.CollabNode;

import java.util.List;
import java.util.Map;

/**
 * 新增节点时触发
 */
public class CollabNodeUpdateEventParam extends CollabVersionChangeEventParam {

    protected List<Map<String, String>> originalModelData;

    public CollabNodeUpdateEventParam(List<CollabNode> triggerNodes, ManagerStatusEnum projectStatus
            , List<Map<String, String>> originalModelData) {
        super.projectStatus = projectStatus;
        super.triggerNodes = triggerNodes;
        this.originalModelData = originalModelData;
    }
}
