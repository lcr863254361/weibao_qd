package com.orient.collabdev.business.version.status;

import com.google.common.collect.Lists;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.collabdev.business.version.ICollabVersionMng;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 进行中的节点数据修改
 *
 * @author panduanduan
 * @create 2018-07-28 10:20 AM
 */
@Component
@MngStatus(status = ManagerStatusEnum.PROCESSING)
public class ProcessingVersionModifyer extends AbstractVersionModifyer {

    @Override
    public void influentByCreate(IBusinessModel bm, Map<String, String> dataMap, String id) {
        //get prj node version
        HttpServletRequest request = RequestUtil.getHttpServletRequest();
        String parentNodeId = RequestUtil.getString(request, "parentNodeId");
        //insert node with prjNodeVersion+1
        Integer nextVersion = collabVersionMng.getNextVersion(parentNodeId);
        CollabNode collabNode = createAndBindNode(bm, dataMap, id, nextVersion);
        //update parent nodes
        collabVersionMng.updateParentVersion(nextVersion, collabNode);
    }


    @Override
    public void influentByUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId, String type) {

        CollabNode collabNode = getBindNode(dataId, type);
        if (null != collabNode) {
            Integer nextVersion = collabVersionMng.getNextVersion(collabNode.getId());
            if (!collabNode.getVersion().equals(nextVersion)) {
                addToHistory(bm, dataId, Lists.newArrayList(collabNode));
                CollabNode updatedNode = updateNode(bm, dataMap, dataId, type, nextVersion);
                //update parent nodes
                collabVersionMng.updateParentVersion(nextVersion, updatedNode);
            }
        }
    }

    @Override
    public void influentByDelete(IBusinessModel bm, String dataIds, String type) {
        //add to history
        List<CollabNode> collabNodeList = getBindNodes(dataIds, type);
        if (!CommonTools.isEmptyList(collabNodeList)) {
            addToHistory(bm, dataIds, collabNodeList);
            //remove
            Integer nextVersion = collabVersionMng.getNextVersion(collabNodeList.get(0).getId());
            collabNodeList.forEach(collabNodeWithRelation -> collabNodeService.delete(collabNodeWithRelation));
            collabVersionMng.updateParentVersion(nextVersion, collabNodeList.get(0));
        }
    }

    /**
     * add current businessmodel data to history table
     *
     * @param bm
     * @param ids
     */
    private void addToHistory(IBusinessModel bm, String ids, List<CollabNode> nodes) {
        //get original data
        if (!StringUtil.isEmpty(ids) && null != bm) {
            //get original business model data
            CustomerFilter customerFilter = new CustomerFilter("id", EnumInter.SqlOperation.In, ids);
            bm.appendCustomerFilter(customerFilter);
            List<Map<String, String>> originalData = orientSqlEngine.getBmService().createModelQuery(bm).list(true);
            if (!CommonTools.isEmptyList(originalData)) {
                //ADD TO HISTORY
                originalData.forEach(dataMap -> {
                    String bmDataId = dataMap.get("ID");
                    Predicate<CollabNode> filter = collabNode -> bmDataId.equalsIgnoreCase(collabNode.getBmDataId());
                    if (nodes.stream().filter(filter).count() > 0) {
                        CollabNode latestNode = nodes.stream().filter(filter).findFirst().get();
                        String hisDataId = collabVersionMng.createHisModelData(bm, dataMap);
                        collabVersionMng.addToHistory(latestNode, hisDataId);
                    }
                });
            }
        }
    }

    @Autowired
    ICollabVersionMng collabVersionMng;

    @Autowired
    public IBusinessModelService businessModelService;

    @Autowired
    protected ISqlEngine orientSqlEngine;
}
