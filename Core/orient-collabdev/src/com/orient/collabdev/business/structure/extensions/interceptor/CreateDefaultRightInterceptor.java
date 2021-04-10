package com.orient.collabdev.business.structure.extensions.interceptor;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.business.designing.CollabRightSetttingBusiness;
import com.orient.collabdev.business.structure.constant.StructOperateType;
import com.orient.collabdev.business.structure.extensions.mng.CollabDevStructInterceptor;
import com.orient.collabdev.business.structure.extensions.mng.CollabDevStructMarker;
import com.orient.collabdev.business.structure.tool.IModelAndNodeHelper;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.utils.CommonTools;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-13 5:24 PM
 */
@CollabDevStructMarker(structOperateType = {StructOperateType.CREATE})
public class CreateDefaultRightInterceptor implements CollabDevStructInterceptor {

    @Override
    public boolean preHandle(IBusinessModel bm, List<Map<String, String>> sourceMap, List<Map<String, String>> targetMap, CollabNode rootNode
            , StructOperateType structOperateType) throws Exception {

        return true;
    }

    @Override
    public void afterCompletion(IBusinessModel bm, List<Map<String, String>> sourceMap, List<Map<String, String>> targetMap, CollabNode rootNode
            , StructOperateType structOperateType, Object processResult) throws Exception {
        //get node ref
        List<String> dataIds = targetMap.stream().map(dataMap -> dataMap.get("ID")).collect(Collectors.toList());
        if (!CommonTools.isEmptyList(dataIds)) {
            List<CollabNode> toAssignNodes = modelAndNodeHelper.getOriginNodeByBmData(bm, dataIds);
            if (!CommonTools.isEmptyList(toAssignNodes)) {
                String modelId = bm.getId();
                toAssignNodes.forEach(collabNode -> {
                    Predicate<Map<String, String>> nodeFilter = dataMap -> dataMap.get("ID").equals(collabNode.getBmDataId());
                    if (targetMap.stream().filter(nodeFilter).count() > 0) {
                        String assigner = targetMap.stream().filter(nodeFilter).findFirst().get().get("PRINCIPAL_" + modelId);
                        collabRightSetttingBusiness.createDefaultRoles(assigner, collabNode.getId());
                    }
                });
            }
        }
    }

    @Autowired
    IModelAndNodeHelper modelAndNodeHelper;

    @Autowired
    CollabRightSetttingBusiness collabRightSetttingBusiness;
}
