package com.orient.collabdev.business.designing;

import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.collabdev.util.DTOConverTool;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-07-27 9:02 AM
 */
@Service
public class DesigningStructureBusiness extends StructureBusiness {

    /**
     * 根据父节点获取子节点信息（最新版本）
     *
     * @param node 父节点唯一标识
     * @return
     */
    public List<CollabDevNodeDTO> getStructureByParent(String node) {
        List<CollabDevNodeDTO> retVal;
        if ("root".equalsIgnoreCase(node)) {
            //get root dir
            retVal = getRootDirs();
        } else if (node.startsWith("dir")) {
            //get prjs
            retVal = getPrjByDirId(node);
        } else {
            //从node节点获取结构
            retVal = getSonNodes(node);
        }
        return retVal;
    }

    /**
     * 从指定位置展现项目树(最新版本)
     *
     * @param node
     * @param startNodeId
     * @param startNodeVersion
     * @return
     */
    public List<CollabDevNodeDTO> getStructureFromSpecialNode(String node, String startNodeId, Integer startNodeVersion) {
        List<CollabDevNodeDTO> retVal;
        if ("root".equalsIgnoreCase(node)) {
            //get current node
            retVal = getNodeById(startNodeId);
        } else {
            //从node节点获取结构
            retVal = getStructureByParent(node);
        }
        return retVal;
    }


    /**
     * get prj node by dirId
     *
     * @param dirId
     * @return
     */
    private List<CollabDevNodeDTO> getPrjByDirId(String dirId) {

        List<CollabNode> collabNodes = collabNodeService.list(Restrictions.eq("isRoot", 1), Restrictions.eq("pid", dirId));
        List<CollabDevNodeDTO> retVal = new ArrayList<>();
        collabNodes.forEach(node -> retVal.add(DTOConverTool.converNativeNodeToDTO(node)));
        return retVal;
    }

    /**
     * 获取指定的node描述（最新版本）
     *
     * @param nodeId
     * @return
     */
    private List<CollabDevNodeDTO> getNodeById(String nodeId) {

        List<CollabNode> collabNodes = collabNodeService.list(Restrictions.eq("id", nodeId));
        List<com.orient.collabdev.model.CollabDevNodeDTO> retVal = new ArrayList<>();
        collabNodes.forEach(node -> retVal.add(DTOConverTool.converNativeNodeToDTO(node)));
        return retVal;
    }

    /**
     * 获取节点的下级信息（最新版本）
     *
     * @param nodeId
     * @return
     */
    private List<CollabDevNodeDTO> getSonNodes(String nodeId) {

        List<CollabNode> collabNodes = collabNodeService.list(Restrictions.eq("pid", nodeId));
        List<CollabDevNodeDTO> retVal = new ArrayList<>();
        collabNodes.forEach(node -> retVal.add(DTOConverTool.converNativeNodeToDTO(node)));
        return retVal;
    }

    @Autowired
    ICollabNodeService collabNodeService;
}
