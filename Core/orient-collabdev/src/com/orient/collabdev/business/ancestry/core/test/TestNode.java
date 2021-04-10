package com.orient.collabdev.business.ancestry.core.test;

import com.aptx.utils.CollectionUtil;
import com.google.common.collect.Sets;
import com.orient.collabdev.business.ancestry.core.bean.INode;
import com.orient.collabdev.business.ancestry.core.listener.StateChangeListener;
import com.orient.collabdev.business.ancestry.core.listener.UpStateChangeNotice;
import com.orient.collabdev.business.ancestry.core.listener.UpStateConsistentNotice;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by karry on 18-8-29.
 */
public class TestNode implements INode<String, String>, StateChangeListener<String, String>,
        UpStateChangeNotice<String, String>, UpStateConsistentNotice<String, String> {
    public static final String STATE_OUTDATE = "已过时";
    public static final String STATE_WAIT = "待更新";
    public static final String STATE_NEW = "最新";

    private String id;
    private String state;

    public TestNode(String id, String state) {
        this.id = id;
        this.state = state;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String stateChange(Set<INode<String, String>> upNodes, INode<String, String> fromNode) {
        Set<String> upStates = Sets.newHashSet();
        for(INode<String, String> upNode : upNodes) {
            upStates.add(upNode.getState());
        }
        if(CollectionUtil.isNullOrEmpty(upStates) || (upStates.size()==1 && STATE_NEW.equals(CollectionUtil.first(upStates)))) {
            if(this.equals(fromNode)) {
                return STATE_NEW;
            }
            else {
                return STATE_WAIT;
            }
        }
        else {
            return STATE_OUTDATE;
        }
    }

    @Override
    public String toString() {
        return "TestNode{" +
                "id='" + id + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    @Override
    public boolean beforeStateChange(Set<INode<String, String>> upNodes, String fromState, String toState) {
        return true;
    }

    @Override
    public void afterStateChange(Set<INode<String, String>> upNodes, String fromState, String toState) {
        System.out.println("beforeStateChange: "+this.getId() + ", " + fromState+"->"+toState);
    }

    @Override
    public void upStateChange(INode<String, String> upNode, String fromState, String toState) {

    }

    @Override
    public void upStateConsistent(Set<? extends INode<String, String>> iNodes, String state) {
        System.out.println("upStateConsistent: "+this.getId() + ", " + state);
    }
}
