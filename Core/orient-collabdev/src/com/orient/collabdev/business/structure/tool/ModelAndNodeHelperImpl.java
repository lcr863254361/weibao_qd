package com.orient.collabdev.business.structure.tool;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeWithRelation;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.sysmodel.service.collabdev.ICollabNodeWithRelationService;
import com.orient.utils.CommonTools;
import com.orient.utils.Pair;
import com.orient.web.base.BaseBusiness;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-02 11:50 AM
 */
@Component
public class ModelAndNodeHelperImpl extends BaseBusiness implements IModelAndNodeHelper {

    @Override
    public List<CollabNodeWithRelation> getNodeByBmData(IBusinessModel bm, String dataIds) {
        List<String> dataList = CommonTools.stringToList(dataIds);
        return getNodeByBmData(bm, dataList);
    }

    @Override
    public List<CollabNodeWithRelation> getNodeByBmData(IBusinessModel bm, List<String> dataIds) {
        String bmName = bm.getName();
        String type = CollabDevConstants.MODELNAME_NODETYPE_MAPPING.get(bmName);
        List<CollabNodeWithRelation> collabNodeWithRelationList = collabNodeWithRelationService.list(Restrictions.eq("type", type), Restrictions.in("bmDataId", dataIds));
        return collabNodeWithRelationList;
    }

    @Override
    public List<CollabNode> getOriginNodeByBmData(IBusinessModel bm, String dataIds) {
        List<String> dataList = CommonTools.stringToList(dataIds);
        return getOriginNodeByBmData(bm, dataList);
    }

    @Override
    public List<CollabNode> getOriginNodeByBmData(IBusinessModel bm, List<String> dataIds) {
        String bmName = bm.getName();
        String type = CollabDevConstants.MODELNAME_NODETYPE_MAPPING.get(bmName);
        List<CollabNode> collabNodeWithRelationList = collabNodeService.list(Restrictions.eq("type", type), Restrictions.in("bmDataId", dataIds));
        return collabNodeWithRelationList;
    }

    @Override
    public List<Pair<IBusinessModel, List<Map<String, String>>>> getParentModelAndIds(String nodeId) {
        List<Pair<IBusinessModel, List<Map<String, String>>>> retVal = new ArrayList<>();
        CollabNodeWithRelation startNode = collabNodeWithRelationService.getById(nodeId);
        if (null != startNode && !CollabDevConstants.NODE_TYPE_PRJ.equalsIgnoreCase(startNode.getType())) {
            Map<String, List<String>> tempData = new HashMap<>();
            extractParentModelAndDataId(startNode, tempData);
            if (!CommonTools.isEmptyMap(tempData)) {
                tempData.forEach((modelName, dataIds) -> {
                    IBusinessModel businessModel = businessModelService.getBusinessModelBySName(modelName, COLLAB_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
                    businessModel.appendCustomerFilter(new CustomerFilter("id", EnumInter.SqlOperation.In, CommonTools.list2String(dataIds)));
                    List<Map<String, String>> queryList = orientSqlEngine.getBmService().createModelQuery(businessModel).list();
                    retVal.add(new Pair<>(businessModel, queryList));
                });
            }
        }
        return retVal;
    }

    private void extractParentModelAndDataId(CollabNodeWithRelation startNode, Map<String, List<String>> tempData) {
        if (null != startNode && !CollabDevConstants.NODE_TYPE_PRJ.equalsIgnoreCase(startNode.getType())) {
            CollabNodeWithRelation parent = startNode.getParent();
            String type = parent.getType();
            String bmDataId = parent.getBmDataId();
            String bmModelName = CollabDevConstants.NODETYPE_MODELNAME_MAPPING.get(type);
            if (!tempData.containsKey(bmModelName)) {
                tempData.put(bmModelName, new ArrayList<>());
            }
            tempData.get(bmModelName).add(bmDataId);
            extractParentModelAndDataId(parent, tempData);
        }
    }

    @Autowired
    ICollabNodeWithRelationService collabNodeWithRelationService;

    @Autowired
    ICollabNodeService collabNodeService;
}
