package com.orient.modeldata.tbomHandle.tbomStrategy.impl;

import com.orient.web.model.BaseNode;
import com.orient.modeldata.bean.TBomDynamicNode;
import com.orient.modeldata.bean.TBomDynamicNodeMaterial;
import com.orient.modeldata.bean.TBomNode;
import com.orient.modeldata.eventParam.GetTbomNodesEventParam;
import com.orient.modeldata.tbomHandle.tbomStrategy.TbomNodeStrategy;
import com.orient.modeldata.tbomHandle.template.TbomDynamicNodeBuilder;
import com.orient.sysmodel.operationinterface.IDynamicTbom;
import com.orient.sysmodel.operationinterface.ITbom;
import com.orient.sysmodel.roleengine.impl.RoleUtilImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

/**
 * 根据动态父节点获取其下的动态子节点
 * Get Dynamic Tbom Nodes By Dynamic Node
 *
 * @author enjoy
 * @createTime 2016-05-22 10:57
 */

@Component
public class GetDTNSByDNStrategy implements TbomNodeStrategy {

    @Autowired
    protected RoleUtilImpl roleEngine;

    @Autowired
    protected TbomDynamicNodeBuilder tbomDynamicNodeBuilder;

    @Override
    public List<BaseNode> getSonTbomNodes(GetTbomNodesEventParam getTbomNodesEventParam) {
        List<BaseNode> retVal = new ArrayList<>();
        TBomNode fatherNode = getTbomNodesEventParam.getFatherNode();
        if (null != fatherNode) {
            //获取其静态节点ID
            String staticDbId = fatherNode.getStaticDbId();
            //获取所述tbomId
            String treeId = fatherNode.getTbomId();
            //获取Tbom根节点节点后端描述
            ITbom hibernateRootNode = roleEngine.getRoleModel(false).getTbomById(treeId);
            //如果父节点为动态节点
            TBomDynamicNode dynamicParentNode = (TBomDynamicNode) fatherNode;
            //获取最近的静态节点
            ITbom nearestHibernateStaticNode = hibernateRootNode.getchildNodebyNodeId(staticDbId);
            //获取该静态节点下 动态节点描述
            SortedSet<IDynamicTbom> alldynamicNodes = nearestHibernateStaticNode.getDynamicTbomSet();
            //获取父节点后台描述
            IDynamicTbom fatherHibernateDynamicNode = alldynamicNodes.stream().filter(dynamicTbom -> dynamicTbom.getId().equals(dynamicParentNode.getDynamicId())).findFirst().get();
            //获取兄弟节点信息
            List<IDynamicTbom> subDynamicNodes = alldynamicNodes.stream().filter(dynamicTbom -> dynamicTbom.getPid() != null && dynamicTbom.getPid().equalsIgnoreCase(dynamicParentNode.getDynamicId())).collect(Collectors.toList());
            //动态子节点
            if (!subDynamicNodes.isEmpty()) subDynamicNodes.forEach(dynamicTbom -> {
                TBomDynamicNodeMaterial tBomDynamicNodeMaterial = new TBomDynamicNodeMaterial(staticDbId, treeId, fatherNode, dynamicTbom, fatherHibernateDynamicNode, null);
                retVal.addAll(tbomDynamicNodeBuilder.builderTBomNode(tBomDynamicNodeMaterial));
            });
        }
        return retVal;
    }
}

