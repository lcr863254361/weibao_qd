package com.orient.collabdev.business.version.event;

import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.web.base.OrientEventBus.OrientEventParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 版本变化事件基类
 */
public class CollabVersionChangeEventParam extends OrientEventParams {

    /**
     * input
     */

    //项目状态
    protected ManagerStatusEnum projectStatus;

    //如果批量操作 需要节点为同层节点的约束
    protected List<CollabNode> triggerNodes = new ArrayList<>();

    public ManagerStatusEnum getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ManagerStatusEnum projectStatus) {
        this.projectStatus = projectStatus;
    }

    public List<CollabNode> getTriggerNodes() {
        return triggerNodes;
    }

    public void setTriggerNodes(List<CollabNode> triggerNodes) {
        this.triggerNodes = triggerNodes;
    }
}
