package com.orient.collabdev.business.structure.aspect;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.business.structure.StructCore;
import com.orient.collabdev.business.structure.tool.IModelAndNodeHelper;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.sqlengine.extend.ModelDataOperate;
import com.orient.sqlengine.extend.annotation.ModelOperateExtend;
import com.orient.sysmodel.domain.collabdev.CollabNodeWithRelation;
import com.orient.utils.CommonTools;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 模型数据操作切面
 */
@ModelOperateExtend(modelNames = {"CB_PROJECT", "CB_PLAN", "CB_TASK"}, schemaName = "协同模型", order = -1)
public class BuildStructure implements ModelDataOperate {

    @Override
    public void beforeAdd(IBusinessModel bm, Map<String, String> dataMap) {
        //增加默认状态标识
        String modelId = bm.getId();
        dataMap.put("STATUS_" + modelId, ManagerStatusEnum.UNSTART.toString());
    }

    @Override
    public void afterAdd(IBusinessModel bm, Map<String, String> dataMap, String id) {
        structCore.add(bm, dataMap, id);
    }

    @Override
    public void beforeDelete(IBusinessModel bm, String dataIds) {
        //检验是否可以被删除
        List<CollabNodeWithRelation> collabNodeWithRelationList = modelAndNodeHelper.getNodeByBmData(bm, dataIds);
        //判断是否自身节点及子节点的状态都是未开始
        List<CollabNodeWithRelation> canotDelNodes = new ArrayList<>();
        filterNodeByStatus(collabNodeWithRelationList, canotDelNodes, ManagerStatusEnum.UNSTART.toString());
        if (canotDelNodes.size() > 0) {
            String nodeNames = CommonTools.list2String(canotDelNodes.stream().map(CollabNodeWithRelation::getName).collect(Collectors.toList()));
            throw new OrientBaseAjaxException("", nodeNames + "不可被删除");
        }
        structCore.delete(bm, dataIds);
    }


    @Override
    public void beforeUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId) {
        structCore.update(bm, dataMap, dataId);
    }

    @Override
    public void afterUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId, Boolean result) {

    }

    @Override
    public void afterDelete(IBusinessModel bm, String dataIds) {

    }

    @Override
    public void beforeDeleteCascade(IBusinessModel bm, String dataIds) {
        beforeDelete(bm, dataIds);
    }

    @Override
    public void afterDeleteCascade(IBusinessModel bm, String dataIds) {
        afterDeleteCascade(bm, dataIds);
    }


    private void filterNodeByStatus(List<CollabNodeWithRelation> list, List<CollabNodeWithRelation> result, String status) {
        list.stream().forEach(collabNodeWithRelation -> {
            if (!status.equalsIgnoreCase(collabNodeWithRelation.getStatus())) {
                result.add(collabNodeWithRelation);
                filterNodeByStatus(collabNodeWithRelation.getChildren(), result, status);
            }
        });
    }

    @Autowired
    StructCore structCore;

    @Autowired
    IModelAndNodeHelper modelAndNodeHelper;

}
