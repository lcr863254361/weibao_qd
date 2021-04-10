package com.orient.collabdev.business.common.noderefopera.impl;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collabdev.business.common.annotation.NodeRefOperate;
import com.orient.collabdev.business.common.noderefopera.NodeRefOperateInterface;
import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.utils.CommonTools;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * 删除权限相关
 *
 * @author panduanduan
 * @create 2018-08-20 10:37 AM
 */
@NodeRefOperate
public class OtherSettingsRef extends AbstractNodeRefOperate implements NodeRefOperateInterface {

    @Override
    public boolean deleteNodeRefData(Boolean isUnstarted, CollabNode... nodes) {
        if (isUnstarted) {
            IBusinessModel otherSettingsBM = businessModelService.getBusinessModelBySName(CollabDevConstants.PROJECT_SETTINGS, COLLAB_SCHEMA_ID, Table);
            List<String> nodeIds = getNodeIds(nodes);
            List<String> prjIds = new ArrayList<>();
            nodeIds.forEach(nodeId -> {
                CollabDevNodeDTO collabDevNodeDTO = structureBusiness.getNode(nodeId, null);
                if (CollabDevConstants.NODE_TYPE_PRJ.equalsIgnoreCase(collabDevNodeDTO.getType())) {
                    prjIds.add(collabDevNodeDTO.getBmDataId());
                }
            });
            if (!CommonTools.isEmptyList(prjIds)) {
                String modelid = otherSettingsBM.getId();
                otherSettingsBM.appendCustomerFilter(new CustomerFilter("PRJ_ID_" + modelid, EnumInter.SqlOperation.In, CommonTools.list2String(prjIds)));
                List<Map<String, String>> toDelData = orientSqlEngine.getBmService().createModelQuery(otherSettingsBM).list();
                List<String> toDelIds = toDelData.stream().map(dataMap -> dataMap.get("ID")).collect(Collectors.toList());
                if (!CommonTools.isEmptyList(toDelIds)) {
                    orientSqlEngine.getBmService().delete(otherSettingsBM, CommonTools.list2String(toDelIds));
                }
            }
        }
        return true;
    }

    @Autowired
    StructureBusiness structureBusiness;

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    public IBusinessModelService businessModelService;

}
