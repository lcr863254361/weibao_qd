package com.orient.collabdev.business.ancestry.core.factory;

import com.aptx.utils.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.orient.collabdev.business.ancestry.core.bean.INode;
import com.orient.collabdev.business.ancestry.taskanalyze.AncestryModelBusiness;
import com.orient.sysmodel.domain.collabdev.CollabHistoryNode;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeDepend;
import com.orient.sysmodel.domain.collabdev.CollabNodeDevStatus;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import java.util.List;
import java.util.Map;

/**
 * Created by karry on 18-9-4.
 */
public abstract class NodeFactory<K, S> {
    protected ApplicationContext applicationContext;

    public abstract INode<K, S> createNode(CollabNode node, CollabNodeDevStatus devStatus, List<CollabNodeDepend> dependsInPlan);

    public abstract INode<K, S> createHisNode(CollabHistoryNode hisNode, CollabNodeDevStatus devStatus, List<CollabNodeDepend> dependsInPlan);

    public NodeFactory() {
        initApplicationContext();
    }

    private ApplicationContext initApplicationContext() {
        if (this.applicationContext == null) {
            this.applicationContext = ContextLoader.getCurrentWebApplicationContext();
            return this.applicationContext;
        } else {
            return this.applicationContext;
        }
    }

    protected Map<String, Long> getMaxValidMap(Map<String, Long> depends) {
        Map<String, Long> retMap = Maps.newHashMap();
        if (!CollectionUtil.isNullOrEmpty(depends)) {
            for (Map.Entry<String, Long> entry : depends.entrySet()) {
                String dependId = entry.getKey();
                retMap.put(dependId, getMaxValidDevStatusVersion(dependId));
            }
        }
        return retMap;
    }

    private Long getMaxValidDevStatusVersion(String nodeId) {
        AncestryModelBusiness ancestryModelBusiness = applicationContext.getBean(AncestryModelBusiness.class);
        return ancestryModelBusiness.getMaxValidVersion(nodeId);
    }

    protected Map<String, Long> getDependMap(String nodeId, List<CollabNodeDepend> dependsInPlan) {
        Map<String, Long> retMap = Maps.newHashMap();
        List<CollabNodeDepend> depends = getMatchedDepends(nodeId, dependsInPlan);
        for (CollabNodeDepend depend : depends) {
            retMap.put(depend.getDependId(), Long.valueOf(depend.getDependVersion()));
        }
        return retMap;
    }

    private List<CollabNodeDepend> getMatchedDepends(String nodeId, List<CollabNodeDepend> dependsInPlan) {
        List<CollabNodeDepend> retList = Lists.newArrayList();
        if (!CollectionUtil.isNullOrEmpty(dependsInPlan)) {
            for (CollabNodeDepend depend : dependsInPlan) {
                if (nodeId.equals(depend.getNodeId())) {
                    retList.add(depend);
                }
            }
        }
        return retList;
    }
}
