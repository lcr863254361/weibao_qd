package com.orient.collabdev.business.common.pedigree.impl;

import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.collabdev.business.common.pedigree.IPedigreeMng;
import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.sysmodel.domain.collabdev.CollabNodeRelation;
import com.orient.sysmodel.service.collabdev.ICollabNodeRelationService;
import com.orient.utils.CommonTools;
import org.hibernate.criterion.Restrictions;
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
@MngStatus(status = ManagerStatusEnum.UNSTART)
@Component
public class UnStartPedigreeMng implements IPedigreeMng {

    @Override
    public void saveRelations(List<CollabNodeRelation> relations, String nodeId, Integer nodeVersion) {
        CollabDevNodeDTO rootNode = structureBusiness.getRootNode(nodeId, nodeVersion);
        String rootId = rootNode.getId();
        Integer rootVersion = rootNode.getVersion();
        //remove exists
        List<String> existIds = collabNodeRelationService.list(Restrictions.eq("pid", nodeId)
                , Restrictions.eq("pversion", nodeVersion)).stream().map(CollabNodeRelation::getId).collect(Collectors.toList());
        if (!CommonTools.isEmptyList(existIds)) {
            collabNodeRelationService.delete(CommonTools.list2stringArray(existIds));
        }

        //save new relations
        relations.forEach(relation -> {
            //remove exist relations
            relation.setPid(nodeId);
            relation.setPversion(nodeVersion);
            relation.setRid(rootId);
            relation.setRversion(rootVersion);
            collabNodeRelationService.save(relation);
        });
    }

    @Autowired
    ICollabNodeRelationService collabNodeRelationService;

    @Autowired
    StructureBusiness structureBusiness;
}
