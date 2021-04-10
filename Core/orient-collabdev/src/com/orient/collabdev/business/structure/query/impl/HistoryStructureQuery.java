package com.orient.collabdev.business.structure.query.impl;

import com.alibaba.fastjson.JSON;
import com.orient.collabdev.business.common.annotation.VersionStatus;
import com.orient.collabdev.business.structure.model.HisStructure;
import com.orient.collabdev.business.structure.query.IStructureQuery;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.constant.VersionStatusEnum;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.collabdev.util.DTOConverTool;
import com.orient.sysmodel.domain.collabdev.CollabHistoryNode;
import com.orient.sysmodel.domain.collabdev.CollabHistoryStruct;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.service.collabdev.ICollabHistoryNodeService;
import com.orient.sysmodel.service.collabdev.ICollabHistoryStructService;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.utils.CommonTools;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-04 3:32 PM
 */
@VersionStatus(status = VersionStatusEnum.HISTORY)
public class HistoryStructureQuery implements IStructureQuery {

    /**
     * todo 暂时不考虑查询效率，后期记得修改(避免循环里查询数据库)
     *
     * @param nodeId
     * @param nodeVersion
     * @return
     */
    @Override
    public List<CollabDevNodeDTO> getSonNodes(String nodeId, Integer nodeVersion) {
        List<CollabDevNodeDTO> sonNodeList = new ArrayList<>();
        CollabDevNodeDTO rootNode = getRootNode(nodeId, nodeVersion);
        CollabDevNodeDTO currentNode = getNode(nodeId, nodeVersion);
        List<CollabHistoryStruct> list = collabHistoryStructService.list(Restrictions.eq("rootId", rootNode.getRootId()), Restrictions.eq("rootVersion", rootNode.getVersion()));
        if (list.size() > 0) {
            CollabHistoryStruct historyStruct = list.get(0);
            String structor = historyStruct.getStructor();
            HisStructure hisStructure = JSON.parseObject(structor, HisStructure.class);
            List<HisStructure> children = hisStructure.getChildren();
            if (!CommonTools.isEmptyList(children)) {
                String id = hisStructure.getId();
                Integer version = hisStructure.getVersion();
                if (currentNode.getId().equals(id) && currentNode.getVersion().equals(version)) {
                    children.forEach(child -> {
                        if (isHistoryNode(child.getId(), child.getVersion())) {
                            sonNodeList.add(getNode(child.getId(), child.getVersion()));
                        } else {
                            sonNodeList.add(latestStructureQuery.getNode(child.getId(), child.getVersion()));
                        }
                    });
                } else {
                    return addSonNodes(currentNode.getId(), children, sonNodeList);
                }
            }
        }
        return sonNodeList;
    }

    private List<CollabDevNodeDTO> addSonNodes(String id, List<HisStructure> children, List<CollabDevNodeDTO> sonNodeList) {
        for (HisStructure hisStructure : children) {
            List<HisStructure> childrenList = hisStructure.getChildren();
            if (hisStructure.getId().equals(id)) {
                if (!CommonTools.isEmptyList(childrenList)) {
                    childrenList.forEach(child -> {
                        if (isHistoryNode(child.getId(), child.getVersion())) {
                            sonNodeList.add(getNode(child.getId(), child.getVersion()));
                        } else {
                            sonNodeList.add(latestStructureQuery.getNode(child.getId(), child.getVersion()));
                        }
                    });
                    return sonNodeList;
                }
            } else {
                if (!CommonTools.isEmptyList(childrenList)) {
                    addSonNodes(id, childrenList, sonNodeList);
                }
            }
        }
        return sonNodeList;
    }

    @Override
    public CollabDevNodeDTO getParentNode(String nodeId, Integer nodeVersion) {
        CollabDevNodeDTO rootNode = getRootNode(nodeId, nodeVersion);
        CollabDevNodeDTO currentNode = getNode(nodeId, nodeVersion);
        List<CollabHistoryStruct> list = collabHistoryStructService.list(Restrictions.eq("rootId", rootNode.getRootId()), Restrictions.eq("rootVersion", rootNode.getVersion()));
        if (list.size() > 0) {
            CollabHistoryStruct collabHistoryStruct = list.get(0);
            String structor = collabHistoryStruct.getStructor();
            HisStructure hisStructure = JSON.parseObject(structor, HisStructure.class);
            List<HisStructure> children = hisStructure.getChildren();
            if (!CommonTools.isEmptyList(children)) {
                return subQuery(hisStructure.getId(), hisStructure.getVersion(), children, currentNode.getId(), currentNode.getVersion());
            }
        }
        return null;
    }

    private CollabDevNodeDTO subQuery(String parentId, Integer parentVersion, List<HisStructure> children, String nodeId, Integer nodeVersion) {
        for (HisStructure hisStructure : children) {
            if (hisStructure.getId().equals(nodeId)) {
                return getNode(parentId, parentVersion);
            } else {
                List<HisStructure> childrenHisStructures = hisStructure.getChildren();
                if (!CommonTools.isEmptyList(childrenHisStructures)) {
                    subQuery(hisStructure.getId(), hisStructure.getVersion(), childrenHisStructures, nodeId, nodeVersion);
                }
            }
        }
        return null;
    }

    @Override
    public CollabDevNodeDTO getRootNode(String nodeId, Integer nodeVersion) {
        CollabDevNodeDTO rootCollabDevNodeDTO = null;
        //节点id和节点版本可能来自两张表
        List<CollabHistoryNode> collabHistoryNodeList = collabHistoryNodeService.list(Restrictions.eq("id", nodeId), Restrictions.eq("version", nodeVersion));
        if (collabHistoryNodeList.size() > 0) {  //在历史表中可以通过nodeId和nodeVersion直接找到记录
            CollabHistoryNode collabHistoryNode = collabHistoryNodeList.get(0);
            String rootId = collabHistoryNode.getRootId();
            Integer rootVersion = collabHistoryNode.getRootVersion();
            List<CollabHistoryNode> rootNodeList = collabHistoryNodeService.list(Restrictions.eq("rootId", rootId), Restrictions.eq("rootVersion", rootVersion), Restrictions.eq("type", CollabDevConstants.NODE_TYPE_PRJ));
            if (rootNodeList.size() > 0) {
                rootCollabDevNodeDTO = (DTOConverTool.converHistoryNativeNodeToDTO(rootNodeList.get(0)));
                //节点描述的id设置为CB_SYS_NODE表中对应的id
                rootCollabDevNodeDTO.setId(rootId);
            }
        } else { //如果在历史表中找不到，说明nodeId是当前表的，nodeVerison是历史表的
            CollabNode collabNode = collabNodeService.getById(nodeId);
            for (CollabHistoryNode collabHistoryNode : collabNode.getHistoryNodeList()) {
                if (collabHistoryNode.getVersion().equals(nodeVersion)) {
                    String rootId = collabHistoryNode.getRootId();
                    Integer rootVersion = collabHistoryNode.getRootVersion();
                    List<CollabHistoryNode> rootNodeList = collabHistoryNodeService.list(Restrictions.eq("rootId", rootId), Restrictions.eq("rootVersion", rootVersion), Restrictions.eq("type", CollabDevConstants.NODE_TYPE_PRJ));
                    if (rootNodeList.size() > 0) {
                        rootCollabDevNodeDTO = (DTOConverTool.converHistoryNativeNodeToDTO(rootNodeList.get(0)));
                        //节点描述的id设置为CB_SYS_NODE表中对应的id
                        rootCollabDevNodeDTO.setId(rootId);
                        break;
                    }
                }
            }
        }
        return rootCollabDevNodeDTO;
    }

    /**
     * @param nodeId
     * @param nodeVersion
     * @return
     */
    @Override
    public CollabDevNodeDTO getNode(String nodeId, Integer nodeVersion) {
        CollabDevNodeDTO historyCollabDevNodeDTO = null;
        //节点id和节点版本可能来自两张表
        List<CollabHistoryNode> collabHistoryNodeList = collabHistoryNodeService.list(Restrictions.eq("id", nodeId), Restrictions.eq("version", nodeVersion));
        if (collabHistoryNodeList.size() > 0) {  //在历史表中可以通过nodeId和nodeVersion直接找到记录
            CollabHistoryNode collabHistoryNode = collabHistoryNodeList.get(0);
            historyCollabDevNodeDTO = DTOConverTool.converHistoryNativeNodeToDTO(collabHistoryNode);
            //节点描述的id设置为CB_SYS_NODE表中对应的id
            historyCollabDevNodeDTO.setId(collabHistoryNode.getBelongNode().getId());
        } else {//如果在历史表中找不到，说明nodeId是当前表的，nodeVerison是历史表的
            CollabNode collabNode = collabNodeService.getById(nodeId);
            for (CollabHistoryNode collabHistoryNode : collabNode.getHistoryNodeList()) {
                if (collabHistoryNode.getVersion().equals(nodeVersion)) {
                    historyCollabDevNodeDTO = (DTOConverTool.converHistoryNativeNodeToDTO(collabHistoryNode));
                    //节点描述的id设置为CB_SYS_NODE表中对应的id
                    historyCollabDevNodeDTO.setId(nodeId);
                    break;
                }
            }
        }
        return historyCollabDevNodeDTO;
    }

    @Override
    public List<CollabDevNodeDTO> getAllParentNode(String nodeId, Integer nodeVersion) {
        List<CollabDevNodeDTO> parentNodeList = new ArrayList<>();
        CollabDevNodeDTO currentNode = getNode(nodeId, nodeVersion);
        CollabDevNodeDTO rootNode = getRootNode(nodeId, nodeVersion);
        List<CollabHistoryStruct> list = collabHistoryStructService.list(new Criterion[]{Restrictions.eq("rootId", rootNode.getRootId()), Restrictions.le("rootVersion", rootNode.getVersion())}, Order.desc("rootVersion"));
        if (!CommonTools.isEmptyList(list)) {
            CollabHistoryStruct historyStruct = list.get(0);
            String structor = historyStruct.getStructor();
            HisStructure hisStructure = JSON.parseObject(structor, HisStructure.class);
            List<HisStructure> children = hisStructure.getChildren();
            String id = hisStructure.getId();
            Integer version = hisStructure.getVersion();
            if (currentNode.getId().equals(id) && currentNode.getVersion().equals(version)) {
                return parentNodeList; //如果当前节点是项目节点，直接返回空集合，说明没有父节点
            } else {
                if (!CommonTools.isEmptyList(children)) {
                    return addParentNode(rootNode, children, currentNode.getId(), parentNodeList);
                }
            }
        }
        return parentNodeList;
    }

    private List<CollabDevNodeDTO> addParentNode(CollabDevNodeDTO parentNode, List<HisStructure> children, String nodeId, List<CollabDevNodeDTO> parentNodeList) {
        for (HisStructure hisStructure : children) {
            if (hisStructure.getId().equals(nodeId)) {
                parentNodeList.add(parentNode);
                return parentNodeList;
            } else {
                List<HisStructure> childrenList = hisStructure.getChildren();
                if (!CommonTools.isEmptyList(childrenList)) {
                    String id = hisStructure.getId();
                    Integer version = hisStructure.getVersion();
                    if (isHistoryNode(id, version)) {
                        addParentNode(getNode(id, version), childrenList, nodeId, parentNodeList);
                    } else {
                        addParentNode(latestStructureQuery.getNode(id, version), childrenList, nodeId, parentNodeList);
                    }
                }
            }
        }
        return parentNodeList;
    }

    /**
     * 通过节点id和节点版本判断是否是历史节点
     *
     * @param nodeId
     * @param nodeVersion
     * @return
     */
    private boolean isHistoryNode(String nodeId, Integer nodeVersion) {
        Criterion idCriterion = Restrictions.eq("id", nodeId);
        if (null != nodeVersion) {
            return collabNodeService.count(idCriterion, Restrictions.eq("version", nodeVersion)) == 0;
        } else {
            return collabNodeService.count(idCriterion) == 0;
        }
    }

    @Autowired
    ICollabHistoryNodeService collabHistoryNodeService;

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    ICollabHistoryStructService collabHistoryStructService;

    @Autowired
    LatestStructureQuery latestStructureQuery;

}
