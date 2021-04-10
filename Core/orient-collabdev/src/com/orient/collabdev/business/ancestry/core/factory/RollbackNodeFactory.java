package com.orient.collabdev.business.ancestry.core.factory;

import com.orient.collabdev.business.ancestry.core.bean.RollbackNode;
import com.orient.collabdev.business.ancestry.core.bean.INode;
import com.orient.collabdev.business.ancestry.core.bean.NodeState;
import com.orient.sysmodel.domain.collabdev.CollabHistoryNode;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeDepend;
import com.orient.sysmodel.domain.collabdev.CollabNodeDevStatus;

import java.util.List;

/**
 * Created by karry on 18-9-5.
 */
public class RollbackNodeFactory extends NodeFactory<String, NodeState> {
    @Override
    public INode<String, NodeState> createNode(CollabNode node, CollabNodeDevStatus devStatus, List<CollabNodeDepend> dependsInPlan) {
        RollbackNode retVal = new RollbackNode();
        retVal.setId(node.getId());
        retVal.setState(new NodeState(devStatus.getTechStatus(), devStatus.getApprovalStatus()));
        retVal.setNode(node);
        retVal.setNodeDevStatus(devStatus);
        return retVal;
    }

    @Override
    public INode<String, NodeState> createHisNode(CollabHistoryNode hisNode, CollabNodeDevStatus devStatus, List<CollabNodeDepend> dependsInPlan) {
        throw new RuntimeException("历史节点无法进行撤销操作");
    }
}
