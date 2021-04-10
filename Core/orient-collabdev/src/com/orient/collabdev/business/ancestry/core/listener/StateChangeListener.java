package com.orient.collabdev.business.ancestry.core.listener;

import com.orient.collabdev.business.ancestry.core.bean.INode;

import java.util.Set;

/**
 * Created by karry on 18-8-29.
 * This node state change
 */
public interface StateChangeListener<K, S> {
    /**
     * Trigger according to INode.stateChange method returns a new state
     *
     * @param upNodes   up nodes
     * @param fromState original state
     * @param toState   dest state
     * @return true to commit state change, false to unchange
     */
    boolean beforeStateChange(Set<INode<K, S>> upNodes, S fromState, S toState);

    /**
     * Trigger according to StateChangeListener.beforeStateChange method returns
     *
     * @param upNodes   up nodes
     * @param fromState original state
     * @param toState   dest state
     */
    void afterStateChange(Set<INode<K, S>> upNodes, S fromState, S toState);
}
