package com.orient.collabdev.business.ancestry.core.handler;

import com.aptx.utils.CollectionUtil;
import com.google.common.base.Objects;
import com.google.common.collect.*;
import com.orient.collabdev.business.ancestry.core.bean.INode;
import com.orient.collabdev.business.ancestry.core.listener.AnalyzeEndListener;
import com.orient.collabdev.business.ancestry.core.listener.StateChangeListener;
import com.orient.collabdev.business.ancestry.core.listener.UpStateChangeNotice;
import com.orient.collabdev.business.ancestry.core.listener.UpStateConsistentNotice;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by karry on 18-8-29.
 * Main context for Ancestry Analyze
 */
public abstract class BaseStateAnalyzeHandler<K, S> {
    protected boolean stopOnStateNoChange = true;
    protected boolean noticeConsistOfSelf = false;
    protected Map<K, INode<K, S>> nodeMap = Maps.newHashMap();
    protected Map<K, INode<K, S>> rootNodeMap = Maps.newHashMap();
    protected Table<K, K, String> relations = HashBasedTable.create();
    protected Map<K, S> oriStateMap = Maps.newHashMap();
    protected Map<K, S> destStateMap = Maps.newHashMap();
    protected Map<String, Object> extraInfos = Maps.newHashMap();


    public BaseStateAnalyzeHandler(Set<INode<K, S>> nodes, Multimap<K, K> relations) {
        this(nodes, relations, true, false);
    }

    public BaseStateAnalyzeHandler(Set<INode<K, S>> nodes, Multimap<K, K> relations, boolean stopOnStateNoChange, boolean noticeConsistOfSelf) {
        for (INode<K, S> node : nodes) {
            nodeMap.put(node.getId(), node);
            rootNodeMap.put(node.getId(), node);
            oriStateMap.put(node.getId(), node.getState());
            destStateMap.put(node.getId(), node.getState());
        }
        List<K> findedRootNodes = CollectionUtil.getRootNodes(relations);
        for (Map.Entry<K, K> entry : relations.entries()) {
            this.relations.put(entry.getKey(), entry.getValue(), "");
            if (!findedRootNodes.contains(entry.getKey())) {
                this.rootNodeMap.remove(entry.getKey());
            }
            if (!findedRootNodes.contains(entry.getValue())) {
                this.rootNodeMap.remove(entry.getValue());
            }
        }
        this.stopOnStateNoChange = stopOnStateNoChange;
        this.noticeConsistOfSelf = noticeConsistOfSelf;
    }

    public void analyze(K thisNodeKey) {
        INode<K, S> thisNode = nodeMap.get(thisNodeKey);
        analyzeRecurently(thisNode, thisNode);
    }

    public Set<INode<K, S>> getResults() {
        return Sets.newHashSet(nodeMap.values());
    }

    protected void analyzeRecurently(INode<K, S> thisNode, INode<K, S> fromNode) {
        K nodeId = thisNode.getId();
        Set<K> upNodeIds = getUpKeys(nodeId);
        Set<INode<K, S>> upNodes = getByKey(upNodeIds);
        Set<K> downNodeIds = getDownKeys(nodeId);
        Set<INode<K, S>> downNodes = getByKey(downNodeIds);

        if (thisNode instanceof UpStateConsistentNotice) {
            Set<S> upStates = upStates(nodeId);
            if (upStates.size() <= 1 && noticeConsistOfSelf) {
                ((UpStateConsistentNotice) thisNode).upStateConsistent(upNodes, CollectionUtil.first(upStates));
            }
        }

        S oriState = thisNode.getState();
        S destState = thisNode.stateChange(upNodes, fromNode);

        boolean isStateChange = !Objects.equal(oriState, destState);
        if (isStateChange && thisNode instanceof StateChangeListener && !((StateChangeListener) thisNode).beforeStateChange(upNodes, oriState, destState)) {
            isStateChange = false;
        }

        if (isStateChange) {
            thisNode.setState(destState);
            destStateMap.put(nodeId, destState);
            if (thisNode instanceof StateChangeListener) {
                ((StateChangeListener) thisNode).afterStateChange(upNodes, oriState, destState);
            }
            for (INode<K, S> downNode : downNodes) {
                if (downNode instanceof UpStateChangeNotice) {
                    ((UpStateChangeNotice) downNode).upStateChange(thisNode, oriState, destState);
                }

                if (downNode instanceof UpStateConsistentNotice) {
                    Set<S> upStates = upStates(downNode.getId());
                    if (upStates.size() <= 1) {
                        Set<INode<K, S>> downUpNodes = getByKey(getUpKeys(downNode.getId()));
                        ((UpStateConsistentNotice) downNode).upStateConsistent(downUpNodes, CollectionUtil.first(upStates));
                    }
                }
                analyzeRecurently(downNode, fromNode);
            }
        } else {
            if (!stopOnStateNoChange || fromNode.equals(thisNode)) {
                for (INode<K, S> downNode : downNodes) {
                    analyzeRecurently(downNode, fromNode);
                }
            }
        }
    }

    public Object getExtraInfoByKey(String key) {
        return extraInfos.get(key);
    }

    public void putExtraInfo(String key, Object value) {
        extraInfos.put(key, value);
    }

    /**********************
     * Common Methods
     **********************/
    public Set<INode<K, S>> getRootNodes() {
        return Sets.newHashSet(this.rootNodeMap.values());
    }

    public Set<K> getUpKeys(K key) {
        Set<K> retSet = relations.column(key).keySet();
        return Sets.newHashSet(retSet);
    }

    public Set<K> getDownKeys(K key) {
        Set<K> retSet = relations.row(key).keySet();
        return Sets.newHashSet(retSet);
    }

    public Set<INode<K, S>> getByKey(Set<K> keys) {
        Set<INode<K, S>> retSet = Sets.newHashSet();
        for (K key : keys) {
            retSet.add(nodeMap.get(key));
        }
        return retSet;
    }

    public Set<S> upStates(K key) {
        Set<INode<K, S>> upNodes = getByKey(getUpKeys(key));
        Set<S> retVal = Sets.newHashSet();
        for (INode<K, S> upNode : upNodes) {
            retVal.add(upNode.getState());
        }
        return retVal;
    }
}
