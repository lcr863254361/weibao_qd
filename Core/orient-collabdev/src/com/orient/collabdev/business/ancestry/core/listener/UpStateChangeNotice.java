package com.orient.collabdev.business.ancestry.core.listener;

import com.orient.collabdev.business.ancestry.core.bean.INode;

/**
 * Created by karry on 18-8-29.
 * Up state change listener
 */
public interface UpStateChangeNotice<K, S> {
    /**
     * Notify when up node state change
     *
     * @param upNode    up node
     * @param fromState original state
     * @param toState   dest state
     */
    void upStateChange(INode<K, S> upNode, S fromState, S toState);
}
