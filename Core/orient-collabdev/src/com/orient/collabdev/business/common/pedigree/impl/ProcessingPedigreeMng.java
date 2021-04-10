package com.orient.collabdev.business.common.pedigree.impl;

import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.collabdev.business.common.pedigree.PedigreeBusiness;
import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.business.version.ICollabVersionMng;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeRelation;
import com.orient.sysmodel.service.collabdev.ICollabNodeHisRelationService;
import com.orient.sysmodel.service.collabdev.ICollabNodeRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-06 2:38 PM
 */
@MngStatus(status = ManagerStatusEnum.PROCESSING)
@Component
public class ProcessingPedigreeMng implements com.orient.collabdev.business.common.pedigree.IPedigreeMng {

    @Override
    public void saveRelations(List<CollabNodeRelation> relations, String nodeId, Integer nodeVersion) {
        CollabDevNodeDTO rootNode = structureBusiness.getRootNode(nodeId, nodeVersion);
        String rootId = rootNode.getId();
        //Integer rootVersion = rootNode.getVersion();
        //升级计划版本--项目版本
        CollabNode latestNode = collabVersionMng.increaseVersion(nodeId);
        Integer latestVersion = latestNode.getVersion();
        //save old relation to history
        List<CollabNodeRelation> collabNodeRelations = pedigreeBusiness.saveToHistory(nodeId, rootId, latestVersion);
        //remove current relations by plan id and version
        List<CollabNodeRelation> existRelations = collabNodeRelations.stream().filter(collabNodeRelation -> nodeId.equalsIgnoreCase(collabNodeRelation.getPid())).collect(Collectors.toList());
        existRelations.forEach(existRelation -> collabNodeRelationService.delete(existRelation));
        //save new relation
        relations.forEach(relation -> {
            relation.setRid(rootId);
            relation.setRversion(latestVersion);
            relation.setPid(nodeId);
            relation.setPversion(latestVersion);
            collabNodeRelationService.save(relation);
        });
    }

    @Autowired
    StructureBusiness structureBusiness;

    @Autowired
    ICollabVersionMng collabVersionMng;

    @Autowired
    ICollabNodeRelationService collabNodeRelationService;

    @Autowired
    ICollabNodeHisRelationService collabNodeHisRelationService;

    @Autowired
    PedigreeBusiness pedigreeBusiness;
}
