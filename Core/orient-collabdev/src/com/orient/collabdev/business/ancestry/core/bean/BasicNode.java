package com.orient.collabdev.business.ancestry.core.bean;

import com.aptx.utils.CollectionUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.orient.collabdev.business.ancestry.core.listener.StateChangeListener;
import com.orient.collabdev.business.ancestry.taskanalyze.AncestryModelBusiness;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.sysmodel.domain.collabdev.CollabHistoryNode;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeDevStatus;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.Set;

/**
 * Created by karry on 18-8-29.
 */
public class BasicNode implements INode<String, NodeState>, StateChangeListener<String, NodeState> {
    private String id;
    private NodeState state;
    private CollabNode node;
    private CollabHistoryNode hisNode;
    private CollabNodeDevStatus nodeDevStatus;
    private Map<String, Long> dependMap = Maps.newHashMap();
    private Map<String, Long> maxValidMap = Maps.newHashMap();

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public NodeState getState() {
        return state;
    }

    @Override
    public void setState(NodeState state) {
        this.state = state;
    }

    public CollabNode getNode() {
        return node;
    }

    public void setNode(CollabNode node) {
        this.node = node;
    }

    public CollabHistoryNode getHisNode() {
        return hisNode;
    }

    public void setHisNode(CollabHistoryNode hisNode) {
        this.hisNode = hisNode;
    }

    public CollabNodeDevStatus getNodeDevStatus() {
        return nodeDevStatus;
    }

    public void setNodeDevStatus(CollabNodeDevStatus nodeDevStatus) {
        this.nodeDevStatus = nodeDevStatus;
    }

    public Map<String, Long> getDependMap() {
        return dependMap;
    }

    public void setDependMap(Map<String, Long> dependMap) {
        this.dependMap = dependMap;
    }

    public Map<String, Long> getMaxValidMap() {
        return maxValidMap;
    }

    public void setMaxValidMap(Map<String, Long> maxValidMap) {
        this.maxValidMap = maxValidMap;
    }

    public CollabNodeDevStatus syncDevState() {
        nodeDevStatus.setTechStatus(state.getTechState());
        nodeDevStatus.setApprovalStatus(state.getApproveState());
        return nodeDevStatus;
    }

    @Override
    public NodeState stateChange(Set<INode<String, NodeState>> upNodes, INode<String, NodeState> fromNode) {
        if (isAllUpStateNew(upNodes)) {
            if (isAllDependsMaxValid()) {
                if (this.equals(fromNode)) {
                    return new NodeState(NodeState.TS_NEW, NodeState.AS_VALID);
                }
                else {
                    return new NodeState(NodeState.TS_WAIT, this.getState().getApproveState());
                }
            } else {
                return new NodeState(NodeState.TS_WAIT, NodeState.AS_INVALID);
            }
        } else {
            return new NodeState(NodeState.TS_OUTDATE, this.getState().getApproveState());
        }
    }

    private boolean isAllUpStateNew(Set<INode<String, NodeState>> upNodes) {
        Set<NodeState> upStates = Sets.newHashSet();
        for (INode<String, NodeState> upNode : upNodes) {
            upStates.add(upNode.getState());
        }
        if (CollectionUtil.isNullOrEmpty(upStates) || (upStates.size() == 1
                && new NodeState(NodeState.TS_NEW, NodeState.AS_VALID).equals(CollectionUtil.first(upStates)))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isAllDependsMaxValid() {
        boolean retVal = true;
        for (Map.Entry<String, Long> entry : dependMap.entrySet()) {
            Long maxValidVersion = maxValidMap.get(entry.getKey());
            if (maxValidVersion != null && entry.getValue() < maxValidVersion) {
                retVal = false;
                break;
            }
        }
        return retVal;
    }

    @Override
    public boolean beforeStateChange(Set<INode<String, NodeState>> upNodes, NodeState fromState, NodeState toState) {
        WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
        AncestryModelBusiness ancestryModelBusiness = applicationContext.getBean(AncestryModelBusiness.class);

        if (NodeState.TS_OUTDATE.equals(toState.getTechState())) {
            ancestryModelBusiness.updateNodeStatus(this.getId(), "0");
        }
        else if (NodeState.TS_WAIT.equals(toState.getTechState())) {
            ancestryModelBusiness.updateNodeStatus(this.getId(), "1");
        }
        else if(NodeState.TS_NEW.equals(toState.getTechState())) {
            ancestryModelBusiness.updateNodeStatus(this.getId(), "2");
        }
        return true;
    }

    @Override
    public void afterStateChange(Set<INode<String, NodeState>> upNodes, NodeState fromState, NodeState toState) {
    }
}
