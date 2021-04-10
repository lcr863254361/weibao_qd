package com.orient.collabdev.business.ancestry.taskanalyze.context;

import com.orient.collabdev.business.ancestry.core.bean.RollbackNode;
import com.orient.collabdev.business.ancestry.core.bean.INode;
import com.orient.collabdev.business.ancestry.core.bean.NodeState;
import com.orient.collabdev.business.ancestry.core.handler.StateAnalyzeHandler;
import com.orient.collabdev.business.ancestry.core.factory.RollbackNodeFactory;
import com.orient.collabdev.business.ancestry.core.factory.NodeFactory;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeDevStatus;

import java.util.Set;

/**
 * Created by karry on 18-9-5.
 */
public class RollbackNodeAnalyzeContext extends BaseAnalyzeContext {
    public void rollbackStateAnalyze(String nodeId) {
        CollabNode node = ancestryModelBusiness.getNodeById(nodeId);
        CollabNode pNode = ancestryModelBusiness.getNodeById(node.getPid());
        if(ManagerStatusEnum.UNSTART.toString().equals(pNode.getStatus())) {
            return;
        }

        NodeFactory<String, NodeState> nodeFactory = new RollbackNodeFactory();
        StateAnalyzeHandler<String, NodeState> analyzeHandler = createAnalyzeHandler(pNode.getId(), nodeFactory);
        analyzeHandler.putExtraInfo("type", StateAnalyzeHandler.TYPE_ROLLBACK);
        analyzeHandler.analyze(nodeId);

        Set<INode<String, NodeState>> resultNodes = analyzeHandler.getResults();
        for (INode<String, NodeState> resultNode : resultNodes) {
            RollbackNode rNode = (RollbackNode) resultNode;
            CollabNodeDevStatus devStatus = rNode.syncDevState();
            ancestryModelBusiness.saveDevStatus(devStatus);
        }
    }
}
