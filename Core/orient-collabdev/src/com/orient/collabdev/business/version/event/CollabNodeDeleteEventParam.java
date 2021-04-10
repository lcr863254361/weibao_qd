package com.orient.collabdev.business.version.event;

import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.sysmodel.domain.collabdev.CollabNode;

import java.util.List;

/**
 * 新增节点时触发
 */
public class CollabNodeDeleteEventParam extends CollabVersionChangeEventParam {

    public CollabNodeDeleteEventParam(List<CollabNode> triggerNodes, ManagerStatusEnum projectStatus) {
        super.projectStatus = projectStatus;
        super.triggerNodes = triggerNodes;
    }
}
