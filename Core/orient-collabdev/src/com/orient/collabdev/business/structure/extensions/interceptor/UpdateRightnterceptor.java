package com.orient.collabdev.business.structure.extensions.interceptor;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.business.designing.CollabRightSetttingBusiness;
import com.orient.collabdev.business.structure.constant.StructOperateType;
import com.orient.collabdev.business.structure.extensions.mng.CollabDevStructInterceptor;
import com.orient.collabdev.business.structure.extensions.mng.CollabDevStructMarker;
import com.orient.collabdev.business.structure.tool.IModelAndNodeHelper;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 如果更新操作中包含了 更新 执行人的操作，则更新自身权限及子节点权限
 */
@CollabDevStructMarker(structOperateType = {StructOperateType.UPDATE})
public class UpdateRightnterceptor implements CollabDevStructInterceptor {

    @Override
    public boolean preHandle(IBusinessModel bm, List<Map<String, String>> sourceMap, List<Map<String, String>> targetMap, CollabNode rootNode
            , StructOperateType structOperateType) throws Exception {
        List<String> dataIds = targetMap.stream().map(dataMap -> dataMap.get("ID")).collect(Collectors.toList());
        if (!CommonTools.isEmptyList(dataIds)) {
            List<CollabNode> toAssignNodes = modelAndNodeHelper.getOriginNodeByBmData(bm, dataIds);
            if (!CommonTools.isEmptyList(toAssignNodes)) {
                String modelId = bm.getId();
                toAssignNodes.forEach(collabNode -> {
                    Predicate<Map<String, String>> nodeFilter = dataMap -> dataMap.get("ID").equals(collabNode.getBmDataId());
                    if (targetMap.stream().filter(nodeFilter).count() > 0 && sourceMap.stream().filter(nodeFilter).count() > 0) {
                        Map<String, String> originalMap = sourceMap.stream().filter(nodeFilter).findFirst().get();
                        Map<String, String> updatedMap = targetMap.stream().filter(nodeFilter).findFirst().get();
                        String originalAssign = CommonTools.Obj2String(originalMap.get("PRINCIPAL_" + modelId));
                        String targetAssign = CommonTools.Obj2String(updatedMap.get("PRINCIPAL_" + modelId));
                        if (!StringUtil.isEmpty(originalAssign) && !StringUtil.isEmpty(targetAssign) && !originalAssign.equalsIgnoreCase(targetAssign)) {
                            collabRightSetttingBusiness.replaceAssign(collabNode.getId(), originalAssign, targetAssign, true);
                        }
                    }
                });
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(IBusinessModel bm, List<Map<String, String>> sourceMap, List<Map<String, String>> targetMap, CollabNode rootNode
            , StructOperateType structOperateType, Object processResult) throws Exception {

    }

    @Autowired
    IModelAndNodeHelper modelAndNodeHelper;

    @Autowired
    CollabRightSetttingBusiness collabRightSetttingBusiness;
}
