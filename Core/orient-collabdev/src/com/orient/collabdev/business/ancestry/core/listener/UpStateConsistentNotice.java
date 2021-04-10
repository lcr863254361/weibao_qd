package com.orient.collabdev.business.ancestry.core.listener;

import com.orient.collabdev.business.ancestry.core.bean.INode;

import java.util.Set;

/**
 * Created by karry on 18-8-29.
 * Up state change listener
 */
public interface UpStateConsistentNotice<K, S> {
    /**
     * Notify when all up nodes state consistent
     *
     * @param nodes all up nodes
     * @param state consistent state
     */
    void upStateConsistent(Set<? extends INode<K, S>> nodes, S state);
}
