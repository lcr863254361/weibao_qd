package com.orient.collabdev.business.structure.query.impl;

import com.orient.collabdev.constant.VersionStatusEnum;
import com.orient.collabdev.business.common.annotation.VersionStatus;
import com.orient.collabdev.business.structure.query.IStructureQuery;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.collabdev.util.DTOConverTool;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeWithRelation;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.sysmodel.service.collabdev.ICollabNodeWithRelationService;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-04 3:32 PM
 */
@Component
@VersionStatus(status = VersionStatusEnum.LATEST)
public class LatestStructureQuery implements IStructureQuery {

    @Override
    public List<CollabDevNodeDTO> getSonNodes(String nodeId, Integer nodeVersion) {
        List<CollabNode> collabNodes = collabNodeService.list(Restrictions.eq("pid", nodeId));
        List<CollabDevNodeDTO> retVal = new ArrayList<>();
        collabNodes.forEach(node -> retVal.add(DTOConverTool.converNativeNodeToDTO(node)));
        return retVal;
    }

    @Override
    public CollabDevNodeDTO getParentNode(String nodeId, Integer nodeVersion) {
        CollabNode collabNode = collabNodeService.getById(nodeId);
        if (null != collabNode) {
            String pid = collabNode.getPid();
            if (!StringUtil.isEmpty(pid) && !pid.startsWith("dir")) {
                CollabNode pNode = collabNodeService.getById(pid);
                return DTOConverTool.converNativeNodeToDTO(pNode);
            }
        }
        return null;
    }

    @Override
    public CollabDevNodeDTO getRootNode(String nodeId, Integer nodeVersion) {
        CollabNodeWithRelation collabNodeWithRelation = collabNodeWithRelationService.getById(nodeId);
        if (null != collabNodeWithRelation) {
            CollabNodeWithRelation prjNode = collabNodeWithRelation;
            while (!CollabDevConstants.NODE_TYPE_PRJ.equalsIgnoreCase(prjNode.getType())) {
                prjNode = collabNodeWithRelationService.getById(prjNode.getParent().getId());
            }
            return DTOConverTool.converCollabNodePOToDTO(prjNode);
        }
        return null;
    }

    @Override
    public CollabDevNodeDTO getNode(String nodeId, Integer nodeVersion) {
        CollabNodeWithRelation collabNodeWithRelation = collabNodeWithRelationService.getById(nodeId);
        if (null != collabNodeWithRelation) {
            return DTOConverTool.converCollabNodePOToDTO(collabNodeWithRelation);
        }
        return null;
    }

    @Override
    public List<CollabDevNodeDTO> getAllParentNode(String nodeId, Integer nodeVersion) {
        List<CollabDevNodeDTO> retVal = new ArrayList<>();
        CollabNode currentNode = collabNodeService.getById(nodeId);
        //???????????????????????????????????????????????????????????????????????????(?????????????????????????????????)
        if (null != currentNode && !currentNode.getType().equalsIgnoreCase(CollabDevConstants.NODE_TYPE_PRJ)) {
            String pId = currentNode.getPid();
            //????????????????????????????????????retVal???
            addParentNode(pId, retVal);
        }
        return retVal;
    }

    private void addParentNode(String nodeId, List<CollabDevNodeDTO> collabDevNodeDTOList) {
        List<CollabNodeWithRelation> collabNodeWithRelations = collabNodeWithRelationService.list(Restrictions.eq("id", nodeId));
        if (!CommonTools.isEmptyList(collabNodeWithRelations)) {
            CollabNodeWithRelation collabNodeWithRelation = collabNodeWithRelations.get(0);
            collabDevNodeDTOList.add(DTOConverTool.converCollabNodePOToDTO(collabNodeWithRelation));
            //??????collabNodeWithRelation???????????????????????????????????????????????????
            if (!CollabDevConstants.NODE_TYPE_PRJ.equalsIgnoreCase(collabNodeWithRelation.getType())) {
                CollabNodeWithRelation parentNode = collabNodeWithRelation.getParent();
                addParentNode(parentNode.getId(), collabDevNodeDTOList);
            }
        }
    }

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    ICollabNodeWithRelationService collabNodeWithRelationService;
}
