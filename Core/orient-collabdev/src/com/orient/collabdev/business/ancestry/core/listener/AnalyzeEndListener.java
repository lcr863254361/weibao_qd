package com.orient.collabdev.business.ancestry.core.listener;

import com.google.common.collect.Table;
import com.orient.collabdev.business.ancestry.core.bean.INode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * On analyze end
 */
public abstract class AnalyzeEndListener<K, S> implements Comparable {
    private Class<K> classOfKey = null;
    private Class<S> classOfSatate = null;

    public AnalyzeEndListener() {
        Type type = this.getClass().getGenericSuperclass();
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        this.classOfKey = (Class<K>) types[0];
        this.classOfSatate = (Class<S>) types[1];
    }

    /**
     * Execute on analyze end
     * @param nodeMap All nodes
     * @param oriStateMap Original node state
     * @param destStateMap result node state
     * @param relations relation of the nodes
     */
    public abstract void onAnalyzeEnd(String type, String planId, Map<K, INode<K, S>> nodeMap, Map<K, S> oriStateMap, Map<K, S> destStateMap, Table<K, K, String> relations);

    /**
     * Order of the listener
     * @return order
     */
    public abstract Integer getOrder();

    @Override
    public int compareTo(Object o) {
        return this.hashCode();
    }

    public Class<K> getClassOfKey() {
        return classOfKey;
    }

    public Class<S> getClassOfSatate() {
        return classOfSatate;
    }
}
