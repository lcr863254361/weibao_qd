package com.orient.collabdev.business.ancestry.taskanalyze;

import com.aptx.utils.CollectionUtil;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.metamodel.metadomain.Restriction;
import com.orient.sqlengine.util.BusinessModelUtils;
import com.orient.sysmodel.dao.collabdev.CollabNodeDependDao;
import com.orient.sysmodel.domain.collabdev.*;
import com.orient.sysmodel.service.collabdev.impl.*;
import com.orient.utils.CommonTools;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by karry on 18-8-30.
 */
@Service
public class AncestryModelBusiness {
    @Autowired
    private BusinessModelUtils businessModelUtils;

    @Autowired
    private CollabNodeService collabNodeService;

    @Autowired
    private CollabHistoryNodeService collabHistoryNodeService;

    @Autowired
    private CollabNodeDevStatusService collabNodeDevStatusService;

    @Autowired
    private CollabNodeRelationService collabNodeRelationService;

    @Autowired
    private CollabNodeDependDao collabNodeDependDao;

    @Autowired
    private CollabRoundService collabRoundService;

    /***********************************
     * Node
     ***********************************/
    public CollabNode getNodeById(String id) {
        return collabNodeService.getById(id);
    }

    public List<CollabNode> getNodesByPid(String pid) {
        return collabNodeService.list(Restrictions.eq("pid", pid));
    }

    public CollabNode getParentNodeByType(String nodeId, String type) {
        CollabNode destNode = collabNodeService.getById(nodeId);
        while (destNode != null && !destNode.getType().equalsIgnoreCase(type)) {
            String pid = destNode.getPid();
            if (!Strings.isNullOrEmpty(pid)) {
                destNode = collabNodeService.getById(pid);
            } else {
                destNode = null;
            }
        }
        if (destNode == null || !destNode.getType().equalsIgnoreCase(type)) {
            return null;
        } else {
            return destNode;
        }
    }

    public List<CollabHistoryNode> getAllHisNodes(String nodeId) {
        List<CollabHistoryNode> hisNodes = collabHistoryNodeService.list(Restrictions.eq("belongNode.id", nodeId));
        List<CollabHistoryNode> retVal = CollectionUtil.getNaturalOrdering(true, new Function<CollabHistoryNode, Integer>() {
            @Override
            public Integer apply(CollabHistoryNode hisNode) {
                return hisNode.getVersion();
            }
        }).reverse().sortedCopy(hisNodes);
        return retVal;
    }

    public CollabHistoryNode getMaxVersionedHistoryNode(String nodeId) {
        List<CollabHistoryNode> hisNodes = collabHistoryNodeService.list(Restrictions.eq("belongNode.id", nodeId));
        if(CollectionUtil.isNullOrEmpty(hisNodes)) {
            return null;
        }
        else {
            return Collections.max(hisNodes, new Comparator<CollabHistoryNode>() {
                @Override
                public int compare(CollabHistoryNode o1, CollabHistoryNode o2) {
                    return o1.getVersion() - o2.getVersion();
                }
            });
        }
    }

    /***********************************
     * Node Dev State
     ***********************************/
    public List<CollabNodeDevStatus> getLatestDevStateByPlan(String planId) {
        CollabNode planNode = getNodeById(planId);
        return collabNodeDevStatusService.list(Restrictions.eq("pid", planId), Restrictions.eq("pversion", Long.valueOf(planNode.getVersion())));
    }

    public CollabNodeDevStatus getLatestDevStateByNode(String nodeId) {
        CollabNode planNode = getParentNodeByType(nodeId, CollabDevConstants.NODE_TYPE_PLAN);
        return collabNodeDevStatusService.get(Restrictions.eq("pid", planNode.getId()), Restrictions.eq("pversion", Long.valueOf(planNode.getVersion())),
                Restrictions.eq("nodeId", nodeId));
    }

    public List<CollabNodeDevStatus> getDevStateByPlan(String planId, Long planVersion) {
        return collabNodeDevStatusService.list(Restrictions.eq("pid", planId), Restrictions.eq("pversion", planVersion));
    }

    public List<CollabNodeDevStatus> getAllDevStateByPlan(String pid, Long minPlanVersion, Long maxPlanVersion) {
        Criterion criterion = Restrictions.eq("pid", pid);
        if(minPlanVersion != null) {
            criterion = Restrictions.and(criterion, Restrictions.ge("pversion", minPlanVersion));
        }
        if(maxPlanVersion != null) {
            criterion = Restrictions.and(criterion, Restrictions.le("pversion", maxPlanVersion));
        }
        return collabNodeDevStatusService.list(criterion);
    }

    public List<CollabNodeDevStatus> getAllDevStateByNode(String nodeId) {
        List<CollabNodeDevStatus> retVal = collabNodeDevStatusService.list(Restrictions.eq("nodeId", nodeId));
        retVal = CollectionUtil.getNaturalOrdering(true, new Function<CollabNodeDevStatus, Long>() {
            @Override
            public Long apply(CollabNodeDevStatus devStatus) {
                return devStatus.getNodeVersion();
            }
        }).reverse().sortedCopy(retVal);
        return retVal;
    }

    public void saveDevStatus(CollabNodeDevStatus devStatus) {
        syncDevStatus(devStatus);
        collabNodeDevStatusService.save(devStatus);
    }

    public void deleteDevStateByPlan(String pid, Long pversion) {
        List<CollabNodeDevStatus> devStatuses = getDevStateByPlan(pid, pversion);
        for (CollabNodeDevStatus devStatus : devStatuses) {
            collabNodeDevStatusService.delete(devStatus);
        }
    }

    public Long getMaxValidVersion(String nodeId) {
        String hql = "select max(nodeVersion) from CollabNodeDevStatus where nodeId='" + nodeId + "' and approvalStatus='有效'";
        return (Long) collabNodeDevStatusService.queryHQL(hql);
    }

    public Map<String, Integer> getUpNodesMaxValidVersions(String nodeId) {
        Map<String, Integer> retMap = Maps.newHashMap();
        CollabNode node = getNodeById(nodeId);
        Set<String> upNodeIds = getUpNodeIds(nodeId, node.getPid());
        for (String upNodeId : upNodeIds) {
            Long maxValidVersion = getMaxValidVersion(upNodeId);
            retMap.put(upNodeId, maxValidVersion.intValue());
        }
        return retMap;
    }

    private void syncDevStatus(CollabNodeDevStatus devStatus) {
        CollabNode node = getNodeById(devStatus.getNodeId());
        CollabNode pnode = getNodeById(node.getPid());
        devStatus.setNodeVersion(Long.valueOf(node.getVersion()));
        devStatus.setPversion(Long.valueOf(pnode.getVersion()));
    }

    /***********************************
     * Node Relation
     ***********************************/
    public List<CollabNodeRelation> getNodeRelationByPlan(String pid) {
        return collabNodeRelationService.list(Restrictions.eq("pid", pid));
    }

    public Set<String> getUpNodeIds(String nodeId, String pid) {
        Set<String> retList = Sets.newHashSet();
        List<CollabNodeRelation> relations = getNodeRelationByPlan(pid);
        for (CollabNodeRelation relation : relations) {
            if (relation.getDestDevNodeId().equals(nodeId)) {
                retList.add(relation.getSrcDevNodeId());
            }
        }
        return retList;
    }

    /***********************************
     * Node Depends
     ***********************************/
    public List<CollabNodeDepend> getNodeDependByPlan(String pid, Integer version) {
        return collabNodeDependDao.getNodeDependByPlan(pid, version);
    }

    public void deleteNodeDepends(String nodeId, String planId, Integer planVersion) {
        DetachedCriteria criteria = DetachedCriteria.forClass(CollabNodeDepend.class).add(Restrictions.eq("nodeId", nodeId))
                .add(Restrictions.eq("pId", planId)).add(Restrictions.eq("pVersion", planVersion));
        List<CollabNodeDepend> oriDepends = collabNodeDependDao.listByCriteria(criteria);
        collabNodeDependDao.deleteAll(oriDepends);
    }

    public void saveNodeDepends(String nodeId, Map<String, Integer> dependsMap) {
        if (CollectionUtil.isNullOrEmpty(dependsMap)) {
            return;
        }
        CollabNode taskNode = getNodeById(nodeId);
        CollabNode planNode = getParentNodeByType(nodeId, CollabDevConstants.NODE_TYPE_PLAN);

        for (Map.Entry<String, Integer> entry : dependsMap.entrySet()) {
            CollabNodeDepend depend = new CollabNodeDepend();
            depend.setNodeId(taskNode.getId());
            depend.setNodeVersion(taskNode.getVersion());
            depend.setDependId(entry.getKey());
            depend.setDependVersion(entry.getValue());
            depend.setpId(planNode.getId());
            depend.setpVersion(planNode.getVersion());
            collabNodeDependDao.save(depend);
        }
    }

    /***********************************
     * Node & Model
     ***********************************/
    public Map<String, String> getCollabModelDataByType(String type, String dataId) {
        List<Map<String, String>> list = null;
        if (CollabDevConstants.NODE_TYPE_PRJ.equals(type)) {
            list = businessModelUtils.queryRecords(CollabDevConstants.COLLAB_SCHEMA_ID, CollabDevConstants.PROJECT, " AND ID=" + dataId);
        }
        else if (CollabDevConstants.NODE_TYPE_PLAN.equals(type)) {
            list = businessModelUtils.queryRecords(CollabDevConstants.COLLAB_SCHEMA_ID, CollabDevConstants.PLAN, " AND ID=" + dataId);
        }
        else if (CollabDevConstants.NODE_TYPE_TASK.equals(type)) {
            list = businessModelUtils.queryRecords(CollabDevConstants.COLLAB_SCHEMA_ID, CollabDevConstants.TASK, " AND ID=" + dataId);
        }
        return CollectionUtil.first(list);
    }

    public void updateNodeStatus(String nodeId, String status) {
        CollabNode node = getNodeById(nodeId);
        node.setStatus(status);
        collabNodeService.save(node);

        String type = node.getType();
        String dataId = node.getBmDataId();
        Map<String, String> modelData = getCollabModelDataByType(type, dataId);
        modelData.put("STATUS", status);
        if (status.equals(ManagerStatusEnum.PROCESSING.toString())) {
            modelData.put("ACTUAL_START_DATE", CommonTools.FormatDate(new Date(), "yyyy-MM-dd"));
        }
        else if (status.equals(ManagerStatusEnum.DONE.toString())) {
            modelData.put("ACTUAL_END_DATE", CommonTools.FormatDate(new Date(), "yyyy-MM-dd"));
        }
        if (CollabDevConstants.NODE_TYPE_PRJ.equals(type)) {
            businessModelUtils.updateRecord(CollabDevConstants.COLLAB_SCHEMA_ID, CollabDevConstants.PROJECT, modelData);
        }
        else if (CollabDevConstants.NODE_TYPE_PLAN.equals(type)) {
            businessModelUtils.updateRecord(CollabDevConstants.COLLAB_SCHEMA_ID, CollabDevConstants.PLAN, modelData);
        }
        else if (CollabDevConstants.NODE_TYPE_TASK.equals(type)) {
            businessModelUtils.updateRecord(CollabDevConstants.COLLAB_SCHEMA_ID, CollabDevConstants.TASK, modelData);
        }
    }

    /***********************************
     * Node Rounds
     ***********************************/
    public CollabRound getLatestRound(String nodeId) {
        CollabRound retVal = null;
        List<CollabRound> rounds = collabRoundService.list(Restrictions.eq("nodeId", nodeId));
        for(CollabRound round : rounds) {
            if(retVal == null) {
                retVal = round;
            }
            else {
                if(round.getId() > retVal.getId()) {
                    retVal = round;
                }
            }
        }
        return retVal;
    }

    public CollabRound saveRound(String nodeId, Long startVersion, Long endVersion) {
        CollabRound round = new CollabRound();
        round.setNodeId(nodeId);
        round.setStartVersion(startVersion);
        round.setEndVersion(endVersion);
        collabRoundService.save(round);
        return round;
    }
}
