package com.orient.collabdev.business.common.noderefopera.impl;

import com.orient.collabdev.business.common.annotation.NodeRefOperate;
import com.orient.collabdev.business.common.noderefopera.NodeRefOperateInterface;
import com.orient.collabdev.business.common.pedigree.PedigreeBusiness;
import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeRelation;
import com.orient.sysmodel.service.collabdev.ICollabNodeRelationService;
import com.orient.utils.CommonTools;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 删除谱系相关
 *
 * @author panduanduan
 * @create 2018-08-20 10:37 AM
 */
@NodeRefOperate
public class PedigreeSettingsRef extends AbstractNodeRefOperate implements NodeRefOperateInterface {

    @Override
    public boolean deleteNodeRefData(Boolean isUnstarted, CollabNode... nodes) {
        if (isUnstarted) {
            List<String> nodeIds = getNodeIds(nodes);
            nodeIds.forEach(nodeId -> deleteRelationFromNode(nodeId));
        } else {
            //获取任务相关节点
            List<CollabNode> taskNode = Arrays.stream(nodes).filter(collabNode -> CollabDevConstants.NODE_TYPE_TASK.equalsIgnoreCase(collabNode.getType()))
                    .collect(Collectors.toList());
            if (!CommonTools.isEmptyList(taskNode)) {
                //修改当前版本
                pedigreeBusiness.removeNodes(taskNode, true);
            }
        }
        return true;
    }

    private void deleteRelationFromNode(String nodeId) {
        List<CollabNodeRelation> relations = new ArrayList<>();
        CollabDevNodeDTO collabDevNodeDTO = structureBusiness.getNode(nodeId, null);
        if (CollabDevConstants.NODE_TYPE_PLAN.equalsIgnoreCase(collabDevNodeDTO.getType())) {
            relations = collabNodeRelationService.list(Restrictions.eq("pid", nodeId));
        } else if (CollabDevConstants.NODE_TYPE_TASK.equalsIgnoreCase(collabDevNodeDTO.getType())) {
            relations = collabNodeRelationService.list(Restrictions.or(Restrictions.eq("srcDevNodeId", nodeId)
                    , Restrictions.eq("destDevNodeId", nodeId)));
        }
        if (!CommonTools.isEmptyList(relations)) {
            String[] ids = new String[relations.size()];
            relations.stream().map(CollabNodeRelation::getId).collect(Collectors.toList()).toArray(ids);
            collabNodeRelationService.delete(ids);
        }
    }

    @Autowired
    ICollabNodeRelationService collabNodeRelationService;

    @Autowired
    StructureBusiness structureBusiness;

    @Autowired
    PedigreeBusiness pedigreeBusiness;
}
