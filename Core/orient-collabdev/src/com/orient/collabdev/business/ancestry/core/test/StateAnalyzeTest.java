package com.orient.collabdev.business.ancestry.core.test;


import com.aptx.utils.bean.BaseObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.orient.collabdev.business.ancestry.core.handler.StateAnalyzeHandler;
import com.orient.collabdev.business.ancestry.core.bean.INode;
import org.junit.Test;

import java.util.Set;

/**
 * Created by karry on 18-8-29.
 */
public class StateAnalyzeTest extends BaseObject {
    @Test
    public void test() {
        Set<INode<String, String>> nodes = Sets.newHashSet();
        nodes.add(new TestNode("1", "已过时"));
        nodes.add(new TestNode("2", "已过时"));
        nodes.add(new TestNode("3", "已过时"));
        nodes.add(new TestNode("4", "已过时"));
        nodes.add(new TestNode("11", "已过时"));

        Multimap<String, String> relations = ArrayListMultimap.create();
        relations.put("1", "2");
        relations.put("2", "3");
        relations.put("3", "4");
        relations.put("1", "3");
        relations.put("2", "4");

        StateAnalyzeHandler<String, String> handler = new StateAnalyzeHandler<>(nodes, relations, false, false);
        handler.analyze("1");
        //context.analyze("2");
        //context.analyze("3");
        //context.analyze("4");
        Set<INode<String, String>> results = handler.getResults();
        out.println(results);
    }
}
