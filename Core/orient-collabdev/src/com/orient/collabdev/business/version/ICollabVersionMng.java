package com.orient.collabdev.business.version;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.business.structure.model.HisStructure;
import com.orient.sysmodel.domain.collabdev.CollabHistoryNode;
import com.orient.sysmodel.domain.collabdev.CollabHistoryStruct;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeWithRelation;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-11 1:46 PM
 */
public interface ICollabVersionMng {

    Integer getNextVersion(String nodeId);

    void updateParentVersion(Integer nextVersion, CollabNode collabNode);

    CollabHistoryNode addToHistory(CollabNode latestNode, String hisBmDataId);

    CollabHistoryNode addToHistory(CollabNodeWithRelation latestNode, String hisBmDataId);

    CollabHistoryNode addToHistory(String nodeId, String hisBmDataId);

    String createHisModelData(IBusinessModel bm, Map<String, String> dataMap);

    CollabNode increaseVersion(String nodeId);

    CollabHistoryStruct saveHisStruct(String prjNodeId, Integer version);

    void saveDevStatuses(CollabNode node);
}
