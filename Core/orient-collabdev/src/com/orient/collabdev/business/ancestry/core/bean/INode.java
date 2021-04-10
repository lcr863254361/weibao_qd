package com.orient.collabdev.business.ancestry.core.bean;

import java.util.Set;

/**
 * Created by karry on 18-8-29.
 * Abstract node for ancestry analyze
 */
public interface INode<K, S> {
    /**
     * This node id
     *
     * @return id of the node
     */
    K getId();

    /**
     * State of this node
     *
     * @return State of this node
     */
    S getState();

    /**
     * Set State of this node
     *
     * @param state State of this node
     */
    void setState(S state);

    /**
     * Give exact state of the node upon up nodes
     *
     * @param upNodes  up nodes
     * @param fromNode analyze from node
     * @return this node state, null for unchange
     */
    S stateChange(Set<INode<K, S>> upNodes, INode<K, S> fromNode);

}
