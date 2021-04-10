package com.orient.collabdev.business.version;

import com.alibaba.fastjson.JSON;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.business.ancestry.taskanalyze.context.CopyNodeAnalyzeContext;
import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.business.structure.model.HisStructure;
import com.orient.collabdev.business.structure.versionbridge.IStructureVersionManager;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.sysmodel.domain.collabdev.*;
import com.orient.sysmodel.service.collabdev.*;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import com.orient.utils.Pair;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseBusiness;
import com.orient.web.util.RequestUtil;
import com.orient.web.util.UserContextUtil;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-11 1:47 PM
 */
@Component
public class DefaultCollabVersionMng extends BaseBusiness implements ICollabVersionMng {

    private static final Logger log = LoggerFactory.getLogger(DefaultCollabVersionMng.class);

    /**
     * TODO 考虑高并发下是否会出现两次操作返回同一个下一版本号的bug
     *
     * @param nodeId
     * @return
     */
    @Override
    public Integer getNextVersion(String nodeId) {
        if (!StringUtil.isEmpty(nodeId)) {
            if (!nodeId.startsWith("dir")) {
                //同一次请求 只生成一个版本
                CollabDevNodeDTO root = structureBusiness.getRootNode(nodeId, null);
                String rootNodeId = root.getId();

                //save history struct conditionlly
                CollabHistoryStruct hisStructure = saveHisStruct(rootNodeId, root.getVersion());

                HttpServletRequest request = RequestUtil.getHttpServletRequest();
                Integer nextVersion = (Integer) request.getAttribute(rootNodeId);
                if (null == nextVersion) {
                    List<CollabPrjVersion> prjVersions = collabPrjVersionService.list(Restrictions.eq("nodeId", Integer.valueOf(rootNodeId)));
                    if (!CommonTools.isEmptyList(prjVersions)) {
                        CollabPrjVersion bindVersion = prjVersions.get(0);
                        Integer currentVersion = bindVersion.getPrjVersion();
                        nextVersion = currentVersion + 1;
                        bindVersion.setPrjVersion(nextVersion);
                        collabPrjVersionService.update(bindVersion);
                        request.setAttribute(rootNodeId, nextVersion);
                    }
                }
                return nextVersion;
            }
        }
        return 1;
    }

    @Override
    public CollabNode increaseVersion(String nodeId) {
        Integer nextVersion = getNextVersion(nodeId);
        CollabNode currentNode = collabNodeService.getById(nodeId);
        //save to history
        addToHistory(currentNode, "");
        //increase current node version
        currentNode.setVersion(nextVersion);
        currentNode.setUpdateUser(UserContextUtil.getUserName());
        currentNode.setUpdateTime(new Date());
        collabNodeService.update(currentNode);
        //increase parent nodes version recursive
        updateParentVersion(nextVersion, currentNode);
        return currentNode;
    }

    /**
     * 递归更新父节点的版本号
     *
     * @param nextVersion
     * @param collabNode
     */
    @Override
    public void updateParentVersion(Integer nextVersion, CollabNode collabNode) {
        if (null != collabNode && !collabNode.getType().equalsIgnoreCase(CollabDevConstants.NODE_TYPE_PRJ)) {
            String pId = collabNode.getPid();
            nextVersion = null == nextVersion ? getNextVersion(collabNode.getId()) : nextVersion;
            updateParentVersion(pId, nextVersion);
        }
    }

    @Override
    public CollabHistoryNode addToHistory(CollabNode collabNode, String hisBmDataId) {
        return addToHistory(collabNode.getId(), hisBmDataId);
    }

    @Override
    public CollabHistoryNode addToHistory(String nodeId, String hisBmDataId) {
        CollabNode collabNode = collabNodeService.getById(nodeId);
        CollabHistoryNode historyNode = new CollabHistoryNode();
        BeanUtils.copyProperties(historyNode, collabNode);
        historyNode.setBelongNode(collabNode);
        if (StringUtil.isEmpty(hisBmDataId)) {
            //如果为空 则被动升级 引用的历史模型数据与上一历史版本模型数据一致
            hisBmDataId = getLastHistoryBmDataId(collabNode);
        }
        historyNode.setBmDataId(hisBmDataId);
        //append root info
        //get root info
        CollabDevNodeDTO root = structureBusiness.getRootNode(nodeId, null);
        historyNode.setRootId(root.getId());
        historyNode.setRootVersion(root.getVersion());
        collabHistoryNodeService.save(historyNode);
        return historyNode;
    }

    @Override
    public CollabHistoryNode addToHistory(CollabNodeWithRelation latestNode, String hisBmDataId) {
        return addToHistory(latestNode.getId(), hisBmDataId);
    }

    /**
     * update parent nodes version in latest node table
     *
     * @param nodeId
     * @param nextVersion
     */
    public void updateParentVersion(String nodeId, Integer nextVersion) {
        //如果使用collabNodeWithRelationService,由于内存没有更新，导致数据有问题，所以改用collabNodeService
        List<CollabNode> collabNodeList = collabNodeService.list(Restrictions.eq("id", nodeId));
        if (!CommonTools.isEmptyList(collabNodeList)) {
            CollabNode collabNode = collabNodeList.get(0);
            if (collabNode.getVersion().intValue() != nextVersion.intValue()) {
                addToHistory(collabNode, "");
                collabNode.setVersion(nextVersion);
                //更新父节点除了更新版本，还需要更新时间和修改人
                collabNode.setUpdateTime(new Date());
                collabNode.setUpdateUser(UserContextUtil.getUserAllName());
                collabNodeService.update(collabNode);
                if (!collabNode.getType().equalsIgnoreCase(CollabDevConstants.NODE_TYPE_PRJ)) {
                    updateParentVersion(collabNode.getPid(), nextVersion);
                }
            }
        }

    }

    public String createHisModelData(IBusinessModel bm, Map<String, String> dataMap) {

        IBusinessModel historyModel = businessModelService.getBusinessModelBySName(bm.getName() + "_HIS", bm.getSchema().getId()
                , EnumInter.BusinessModelEnum.Table);
        String hisModelId = historyModel.getId();
        String latestModelId = bm.getId();
        String bmDataId = dataMap.get("ID");
        dataMap.remove("ID");
        Map<String, String> historyData = new HashMap<>();
        dataMap.forEach((latestColumnName, latestColumnValue) -> historyData.put(latestColumnName.replaceAll(latestModelId, hisModelId), latestColumnValue));
        historyData.put("ORIGIN_ID_" + hisModelId, bmDataId);
        String hisDataId = orientSqlEngine.getBmService().insertModelData(historyModel, historyData);
        return hisDataId;
    }

    @Override
    public CollabHistoryStruct saveHisStruct(String prjNodeId, Integer version) {
        CollabHistoryStruct hisStruct = collabHistoryStructService.get(Restrictions.eq("rootId", prjNodeId), Restrictions.eq("rootVersion", version));
        if (hisStruct == null) {
            CollabNode rootNode = collabNodeService.get(Restrictions.eq("id", prjNodeId), Restrictions.eq("version", version));
            HisStructure hisStructure = converToHisStructure(rootNode);
            CollabHistoryStruct collabHistoryStruct = new CollabHistoryStruct();
            collabHistoryStruct.setRootId(rootNode.getId());
            collabHistoryStruct.setRootVersion(rootNode.getVersion());
            collabHistoryStruct.setStructor(JSON.toJSONString(hisStructure));
            collabHistoryStructService.save(collabHistoryStruct);
        }
        return hisStruct;
    }

    @Override
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void saveDevStatuses(CollabNode node) {
        if (node != null && CollabDevConstants.NODE_TYPE_PLAN.equals(node.getType())) {
            CopyNodeAnalyzeContext context = new CopyNodeAnalyzeContext();
            context.copyTaskDevStatesInPlan(node.getId());
        }
    }

    /**************************************** Private Methods ************************************/
    /**
     * 获取最新的历史节点所绑定的历史模型数据id
     *
     * @param collabNode
     * @return
     */
    private String getLastHistoryBmDataId(CollabNode collabNode) {
        List<CollabHistoryNode> historyNodes = collabNode.getHistoryNodeList();
        if (CommonTools.isEmptyList(historyNodes)) {
            //create one
            Pair<IBusinessModel, Map<String, String>> refBmData = structureManager.getNodeRefBmData(collabNode);
            return createHisModelData(refBmData.fst, refBmData.snd);
        } else {
            return historyNodes.get(0).getBmDataId();
        }
    }

    private HisStructure converToHisStructure(CollabNode rootNode) {
        HisStructure hisStructure = new HisStructure();
        hisStructure.setId(rootNode.getId());
        hisStructure.setVersion(rootNode.getVersion());
        appendChildren(hisStructure, rootNode);
        return hisStructure;
    }

    private void appendChildren(HisStructure hisStructure, CollabNode fatherNode) {
        if (null != fatherNode && null != hisStructure) {
            List<CollabNode> childrenNode = collabNodeService.list(Restrictions.eq("pid", fatherNode.getId()));
            if (!CommonTools.isEmptyList(childrenNode)) {
                childrenNode.forEach(sonNode -> {
                    HisStructure sonHisNode = new HisStructure();
                    sonHisNode.setId(sonNode.getId());
                    sonHisNode.setVersion(sonNode.getVersion());
                    hisStructure.getChildren().add(sonHisNode);
                    appendChildren(sonHisNode, sonNode);
                });
            }
        }
    }

    @Autowired
    StructureBusiness structureBusiness;

    @Autowired
    ICollabHistoryNodeService collabHistoryNodeService;

    @Autowired
    IStructureVersionManager structureManager;

    @Autowired
    ICollabNodeWithRelationService collabNodeWithRelationService;

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    ICollabPrjVersionService collabPrjVersionService;

    @Autowired
    ICollabHistoryStructService collabHistoryStructService;

}
