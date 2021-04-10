package com.orient.collabdev.business.ancestry.taskanalyze.context;

import com.orient.collabdev.business.ancestry.core.bean.INode;
import com.orient.collabdev.business.ancestry.core.bean.AdjustNode;
import com.orient.collabdev.business.ancestry.core.bean.NodeState;
import com.orient.collabdev.business.ancestry.core.handler.StateAnalyzeHandler;
import com.orient.collabdev.business.ancestry.core.factory.AdjustNodeFactory;
import com.orient.collabdev.business.ancestry.core.factory.NodeFactory;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeDevStatus;

import java.util.Set;

/**
 * Created by karry on 18-9-5.
 */
public class AdjustNodeAnalyzeContext extends BaseAnalyzeContext {
    public void adjustStateAnalyze(String pid) {
        CollabNode pNode = ancestryModelBusiness.getNodeById(pid);
        if(ManagerStatusEnum.UNSTART.toString().equals(pNode.getStatus())) {
            return;
        }

        NodeFactory<String, NodeState> nodeFactory = new AdjustNodeFactory();
        StateAnalyzeHandler<String, NodeState> analyzeHandler = createAnalyzeHandler(pNode.getId(), nodeFactory, false, false);
        analyzeHandler.putExtraInfo("type", StateAnalyzeHandler.TYPE_ADJUST);
        Set<INode<String, NodeState>> rootNodes = analyzeHandler.getRootNodes();
        for (INode<String, NodeState> rootNode : rootNodes) {
            analyzeHandler.analyze(rootNode.getId());
        }
        //deal with results
        Set<INode<String, NodeState>> resultNodes = analyzeHandler.getResults();
        for (INode<String, NodeState> resultNode : resultNodes) {
            AdjustNode rNode = (AdjustNode) resultNode;
            CollabNodeDevStatus devStatus = rNode.syncDevState();
            ancestryModelBusiness.saveDevStatus(devStatus);
        }
    }
}
