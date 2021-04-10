package com.orient.collabdev.business.structure.extensions.interceptor;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.business.common.annotation.NodeRefOperate;
import com.orient.collabdev.business.common.noderefopera.NodeRefOperateInterface;
import com.orient.collabdev.business.structure.constant.StructOperateType;
import com.orient.collabdev.business.structure.extensions.mng.CollabDevStructInterceptor;
import com.orient.collabdev.business.structure.extensions.mng.CollabDevStructMarker;
import com.orient.collabdev.business.structure.tool.IModelAndNodeHelper;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.utils.CommonTools;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 删除任意节点时 删除其关联信息
 *
 * @author panduanduan
 * @create 2018-08-13 5:24 PM
 */
@CollabDevStructMarker(structOperateType = StructOperateType.DELETE, order = -10)
public class RemoveRefDataInterceptor implements CollabDevStructInterceptor {

    @Override
    public boolean preHandle(IBusinessModel bm, List<Map<String, String>> sourceMap, List<Map<String, String>> targetMap, CollabNode rootNode
            , StructOperateType structOperateType) throws Exception {

        List<String> dataIds = sourceMap.stream().map(dataMap -> dataMap.get("ID")).collect(Collectors.toList());
        if (!CommonTools.isEmptyList(dataIds)) {
            List<CollabNode> toDelNodes = modelAndNodeHelper.getOriginNodeByBmData(bm, dataIds);
            List<NodeRefOperateInterface> nodeHandler = getNodeHandler();
            nodeHandler.forEach(handler -> handler.deleteNodeRefData(rootNode.getStatus().equalsIgnoreCase(ManagerStatusEnum.UNSTART.toString()), list2Array(toDelNodes)));
        }
        return true;
    }

    @Override
    public void afterCompletion(IBusinessModel bm, List<Map<String, String>> sourceMap, List<Map<String, String>> targetMap, CollabNode rootNode
            , StructOperateType structOperateType, Object processResult) throws Exception {
    }

    private List<NodeRefOperateInterface> getNodeHandler() {
        Map<NodeRefOperateInterface, Integer> tmpMap = new HashMap<>();
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(NodeRefOperateInterface.class);
        for (String beanName : beanNames) {
            NodeRefOperateInterface nodeRefOperateInterface = OrientContextLoaderListener.Appwac.getBean(beanName, NodeRefOperateInterface.class);
            Class operateClass = nodeRefOperateInterface.getClass();
            NodeRefOperate classAnnotation = (NodeRefOperate) operateClass.getAnnotation(NodeRefOperate.class);
            int order = classAnnotation.order();
            tmpMap.put(nodeRefOperateInterface, order);
        }
        return CommonTools.getSortedDataByValue(tmpMap);
    }

    private CollabNode[] list2Array(List<CollabNode> toDelNodes) {
        CollabNode[] retVal = new CollabNode[toDelNodes.size()];
        for (int i = 0; i < toDelNodes.size(); i++) {
            retVal[i] = toDelNodes.get(i);
        }
        return retVal;
    }

    @Autowired
    IModelAndNodeHelper modelAndNodeHelper;
}
