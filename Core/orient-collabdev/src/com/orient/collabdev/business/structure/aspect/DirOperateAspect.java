package com.orient.collabdev.business.structure.aspect;

import com.google.common.collect.Sets;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.collabdev.business.structure.util.CollabNodeExtrator;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sqlengine.extend.ModelDataOperate;
import com.orient.sqlengine.extend.annotation.ModelOperateExtend;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.utils.CommonTools;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * 模型数据操作切面
 */
@ModelOperateExtend(modelNames = {"CB_DIR"}, schemaName = "协同模型", order = -1)
public class DirOperateAspect implements ModelDataOperate {

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    ISqlEngine orientSqlEngine;

    @Autowired
    IBusinessModelService businessModelService;

    @Override
    public void beforeAdd(IBusinessModel bm, Map<String, String> dataMap) {

    }

    @Override
    public void afterAdd(IBusinessModel bm, Map<String, String> dataMap, String id) {

    }

    @Override
    public void beforeDelete(IBusinessModel bm, String dataIds) {
        //检验是否可以被删除
        List<String> nodePids = new ArrayList<>();
        String[] dataIdArray = dataIds.split(",");
        for (String dataId : dataIdArray) {
            nodePids.add("dir-" + dataId);
        }
        List<CollabNode> sonNodes = collabNodeService.list(Restrictions.in("pid", nodePids));
        Set<String> status = Sets.newConcurrentHashSet(sonNodes.stream().map(CollabNode::getStatus).collect(Collectors.toList()));
        if (status.size() > 0) {
            if (status.size() != 1 || !status.iterator().next().equals(ManagerStatusEnum.UNSTART.toString())) {
                throw new OrientBaseAjaxException("", "所有项目都是未启动的情况下，才可以删除项目分类");
            }
        }
        //remove son nodes
        Map<String, List<String>> toDelModelData = CollabNodeExtrator.extraModelNameAndIdsFromCollabNode(sonNodes);
        toDelModelData.forEach((modelName, dataIdList) -> {
            IBusinessModel businessModel = businessModelService.getBusinessModelBySName(modelName, COLLAB_SCHEMA_ID, Table);
            orientSqlEngine.getBmService().delete(businessModel, CommonTools.list2String(dataIdList));
        });
    }

    @Override
    public void afterDelete(IBusinessModel bm, String dataIds) {

    }

    @Override
    public void beforeDeleteCascade(IBusinessModel bm, String dataIds) {
    }

    @Override
    public void afterDeleteCascade(IBusinessModel bm, String dataIds) {
    }

    @Override
    public void beforeUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId) {
    }

    @Override
    public void afterUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId, Boolean result) {
    }
}
