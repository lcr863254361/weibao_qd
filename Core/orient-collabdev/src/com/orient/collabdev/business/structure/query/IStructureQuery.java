package com.orient.collabdev.business.structure.query;

import com.orient.collabdev.model.CollabDevNodeDTO;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-04 3:33 PM
 */
public interface IStructureQuery {

    List<CollabDevNodeDTO> getSonNodes(String nodeId, Integer nodeVersion);

    CollabDevNodeDTO getParentNode(String nodeId, Integer nodeVersion);

    CollabDevNodeDTO getRootNode(String nodeId, Integer nodeVersion);

    CollabDevNodeDTO getNode(String nodeId, Integer nodeVersion);

    List<CollabDevNodeDTO> getAllParentNode(String nodeId, Integer nodeVersion);

}
