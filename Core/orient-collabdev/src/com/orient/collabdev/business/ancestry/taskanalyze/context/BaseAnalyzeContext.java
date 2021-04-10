package com.orient.collabdev.business.ancestry.taskanalyze.context;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.orient.collabdev.business.ancestry.core.bean.INode;
import com.orient.collabdev.business.ancestry.core.bean.NodeState;
import com.orient.collabdev.business.ancestry.core.handler.StateAnalyzeHandler;
import com.orient.collabdev.business.ancestry.core.factory.NodeFactory;
import com.orient.collabdev.business.ancestry.taskanalyze.AncestryModelBusiness;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeDepend;
import com.orient.sysmodel.domain.collabdev.CollabNodeDevStatus;
import com.orient.sysmodel.domain.collabdev.CollabNodeRelation;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by karry on 18-9-5.
 */
public abstract class BaseAnalyzeContext {
    protected ApplicationContext applicationContext;

    protected AncestryModelBusiness ancestryModelBusiness;

    public BaseAnalyzeContext() {
        initApplicationContext();
        this.ancestryModelBusiness = applicationContext.getBean(AncestryModelBusiness.class);
    }

    /***********************************
     * Node
     ***********************************/
    public StateAnalyzeHandler<String, NodeState> createAnalyzeHandler(String pid, NodeFactory<String, NodeState> nodeFactory) {
        return createAnalyzeHandler(pid, nodeFactory, true, false);
    }

    public StateAnalyzeHandler<String, NodeState> createAnalyzeHandler(String pid, NodeFactory<String, NodeState> nodeFactory,
                                                                       boolean stopOnStateNoChange, boolean noticeConsistOfSelf) {
        Set<INode<String, NodeState>> nodes = createNodes(pid, nodeFactory);
        Multimap<String, String> relations = createRelations(pid);
        StateAnalyzeHandler<String, NodeState> analyzeHandler = new StateAnalyzeHandler<>(nodes, relations, stopOnStateNoChange, noticeConsistOfSelf);
        analyzeHandler.putExtraInfo("planId", pid);
        return analyzeHandler;
    }

    public Set<INode<String, NodeState>> createNodes(String pid, NodeFactory<String, NodeState> nodeFactory) {
        Set<INode<String, NodeState>> retSet = Sets.newHashSet();
        CollabNode planNode = ancestryModelBusiness.getNodeById(pid);
        List<CollabNode> nodes = ancestryModelBusiness.getNodesByPid(pid);
        List<CollabNodeDevStatus> allDevStates = ancestryModelBusiness.getDevStateByPlan(pid, Long.valueOf(planNode.getVersion()));
        Map<String, CollabNodeDevStatus> devStateMap = getExactDevStates(nodes, allDevStates);
        List<CollabNodeDepend> dependInPlan = ancestryModelBusiness.getNodeDependByPlan(pid, planNode.getVersion());
        for (CollabNode node : nodes) {
            CollabNodeDevStatus devState = devStateMap.get(node.getId());
            INode<String, NodeState> iNode = nodeFactory.createNode(node, devState, dependInPlan);
            retSet.add(iNode);
        }
        return retSet;
    }

    public Multimap<String, String> createRelations(String pid) {
        Multimap<String, String> retMap = ArrayListMultimap.create();
        List<CollabNodeRelation> relations = ancestryModelBusiness.getNodeRelationByPlan(pid);
        for (CollabNodeRelation relation : relations) {
            retMap.put(relation.getSrcDevNodeId(), relation.getDestDevNodeId());
        }
        return retMap;
    }

    /*********************************** History Node ***********************************/

    /***********************************
     * Private Methods
     ***********************************/
    private ApplicationContext initApplicationContext() {
        if (this.applicationContext == null) {
            this.applicationContext = ContextLoader.getCurrentWebApplicationContext();
            return this.applicationContext;
        } else {
            return this.applicationContext;
        }
    }

    private static Map<String, CollabNodeDevStatus> getExactDevStates(List<CollabNode> nodes, List<CollabNodeDevStatus> nodeDevStatuses) {
        Map<String, CollabNodeDevStatus> retMap = Maps.newHashMap();
        for (CollabNode node : nodes) {
            CollabNodeDevStatus nodeDevStatus = getExactDevState(node, nodeDevStatuses);
            if (nodeDevStatus != null) {
                retMap.put(node.getId(), nodeDevStatus);
            }
        }
        return retMap;
    }

    private static CollabNodeDevStatus getExactDevState(CollabNode node, List<CollabNodeDevStatus> nodeDevStatuses) {
        String nodeId = node.getId();
        Integer nodeVersion = node.getVersion();
        for (CollabNodeDevStatus nodeDevStatus : nodeDevStatuses) {
            if (nodeId.equals(nodeDevStatus.getNodeId()) && Long.valueOf(nodeVersion).equals(Long.valueOf(nodeDevStatus.getNodeVersion()))) {
                return nodeDevStatus;
            }
        }
        return null;
    }
}
