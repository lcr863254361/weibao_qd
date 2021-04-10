package com.orient.collabdev.business.ancestry.core.factory;

import com.orient.collabdev.business.ancestry.core.bean.INode;
import com.orient.collabdev.business.ancestry.core.bean.AdjustNode;
import com.orient.collabdev.business.ancestry.core.bean.NodeState;
import com.orient.sysmodel.domain.collabdev.CollabHistoryNode;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeDepend;
import com.orient.sysmodel.domain.collabdev.CollabNodeDevStatus;

import java.util.List;
import java.util.Map;

/**
 * Created by karry on 18-9-5.
 */
public class AdjustNodeFactory extends NodeFactory<String, NodeState> {
    @Override
    public INode<String, NodeState> createNode(CollabNode node, CollabNodeDevStatus devStatus, List<CollabNodeDepend> dependsInPlan) {
        AdjustNode retVal = new AdjustNode();
        retVal.setId(node.getId());
        retVal.setState(new NodeState(devStatus.getTechStatus(), devStatus.getApprovalStatus()));
        retVal.setNode(node);
        retVal.setNodeDevStatus(devStatus);
        Map<String, Long> dependMap = getDependMap(node.getId(), dependsInPlan);
        retVal.setDependMap(dependMap);
        retVal.setMaxValidMap(getMaxValidMap(dependMap));
        return retVal;
    }

    @Override
    public INode<String, NodeState> createHisNode(CollabHistoryNode hisNode, CollabNodeDevStatus devStatus, List<CollabNodeDepend> dependsInPlan) {
        throw new RuntimeException("历史节点无法进行调整操作");
    }
}
