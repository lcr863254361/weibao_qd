package com.orient.collabdev.business.ancestry.core.factory;

import com.orient.collabdev.business.ancestry.core.bean.BasicNode;
import com.orient.collabdev.business.ancestry.core.bean.INode;
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
public class BasicNodeFactory extends NodeFactory<String, NodeState> {
    @Override
    public INode<String, NodeState> createNode(CollabNode node, CollabNodeDevStatus devStatus, List<CollabNodeDepend> dependsInPlan) {
        BasicNode retVal = new BasicNode();
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
        CollabNode node = hisNode.getBelongNode();
        BasicNode retVal = new BasicNode();
        retVal.setId(node.getId());
        retVal.setState(new NodeState(devStatus.getTechStatus(), devStatus.getApprovalStatus()));
        retVal.setNode(node);
        retVal.setHisNode(hisNode);
        retVal.setNodeDevStatus(devStatus);
        Map<String, Long> dependMap = getDependMap(node.getId(), dependsInPlan);
        retVal.setDependMap(dependMap);
        retVal.setMaxValidMap(getMaxValidMap(dependMap));
        return retVal;
    }
}
