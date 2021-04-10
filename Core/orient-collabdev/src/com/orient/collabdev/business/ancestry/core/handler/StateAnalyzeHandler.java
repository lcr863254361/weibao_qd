package com.orient.collabdev.business.ancestry.core.handler;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeMultimap;
import com.orient.collabdev.business.ancestry.core.bean.INode;
import com.orient.collabdev.business.ancestry.core.listener.AnalyzeEndListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import java.util.Collection;
import java.util.Set;

/**
 * Created by karry on 18-8-29.
 * Main context for Ancestry Analyze
 */
public class StateAnalyzeHandler<K, S> extends BaseStateAnalyzeHandler<K, S> {
    public static final String TYPE_BASIC = "basic";
    public static final String TYPE_ROLLBACK = "rollback";
    public static final String TYPE_ADJUST = "adjust";

    public StateAnalyzeHandler(Set<INode<K, S>> nodes, Multimap<K, K> relations) {
        this(nodes, relations, true, false);
    }

    public StateAnalyzeHandler(Set<INode<K, S>> nodes, Multimap<K, K> relations, boolean stopOnStateNoChange, boolean noticeConsistOfSelf) {
        super(nodes, relations, stopOnStateNoChange, noticeConsistOfSelf);
    }

    public Set<INode<K, S>> getResults() {
        executeAnalyzeEndListener();
        return Sets.newHashSet(nodeMap.values());
    }

    private void executeAnalyzeEndListener() {
        ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
        Collection<AnalyzeEndListener> listeners = applicationContext.getBeansOfType(AnalyzeEndListener.class).values();
        TreeMultimap<Integer, AnalyzeEndListener> listenerMap = TreeMultimap.create();
        for(AnalyzeEndListener listener : listeners) {
            listenerMap.put(listener.getOrder(), listener);
        }
        for(AnalyzeEndListener analyzeEndListener : listenerMap.values()) {
            analyzeEndListener.onAnalyzeEnd((String)this.getExtraInfoByKey("type"), (String)this.getExtraInfoByKey("planId"),
                    nodeMap, oriStateMap, destStateMap, relations);
        }
    }
}
