package com.orient.collabdev.business.version.event;

import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.sysmodel.domain.collabdev.CollabNode;

import java.util.List;

/**
 * 新增节点时触发
 */
public class CollabNodeCreateEventParam extends CollabVersionChangeEventParam {

    public CollabNodeCreateEventParam(List<CollabNode> triggerNodes, ManagerStatusEnum projectStatus) {
        super.projectStatus = projectStatus;
        super.triggerNodes = triggerNodes;
    }
}
