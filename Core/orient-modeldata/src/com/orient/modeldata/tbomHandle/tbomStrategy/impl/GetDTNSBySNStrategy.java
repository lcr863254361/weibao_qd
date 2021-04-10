package com.orient.modeldata.tbomHandle.tbomStrategy.impl;

import com.orient.web.model.BaseNode;
import com.orient.modeldata.bean.TBomDynamicNodeMaterial;
import com.orient.modeldata.bean.TBomNode;
import com.orient.modeldata.bean.TBomStaticNode;
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

/**
 * 根据静态Tbom节点获取动态Tbom子节点
 * Get Dynamic Tbom Nodes By Static Node
 *
 * @author enjoy
 * @createTime 2016-05-22 10:42
 */
@Component
public class GetDTNSBySNStrategy implements TbomNodeStrategy {

    @Autowired
    protected RoleUtilImpl roleEngine;

    @Autowired
    protected TbomDynamicNodeBuilder tbomDynamicNodeBuilder;

    @Override
    public List<BaseNode> getSonTbomNodes(GetTbomNodesEventParam getTbomNodesEventParam) {
        List<BaseNode> retVal = new ArrayList<>();
        TBomNode fatherNode = getTbomNodesEventParam.getFatherNode();
        if (null != fatherNode) {
            //转化为静态节点
            TBomStaticNode staticParentNode = (TBomStaticNode) fatherNode;
            //获取其静态节点ID
            String staticDbId = staticParentNode.getStaticDbId();
            //获取所述tbomId
            String treeId = fatherNode.getTbomId();
            //获取Tbom根节点节点后端描述
            ITbom hibernateRootNode = roleEngine.getRoleModel(false).getTbomById(treeId);
            //获取父节点的tbom描述
            ITbom parentHibernateTbomNode = hibernateRootNode.getchildNodebyNodeId(staticDbId);
            //获取其下的所有动态子节点Tbom描述
            SortedSet<IDynamicTbom> alldynamicNodes = parentHibernateTbomNode.getDynamicTbomSet();
            //由于数据库平行保存动态节点 故只需要动态加载第一个即可
            //只加载第一个
            if (!alldynamicNodes.isEmpty()) {
                TBomDynamicNodeMaterial tBomDynamicNodeMaterial = new TBomDynamicNodeMaterial(staticDbId, treeId, fatherNode, alldynamicNodes.first(),null,parentHibernateTbomNode);
                retVal.addAll(tbomDynamicNodeBuilder.builderTBomNode(tBomDynamicNodeMaterial));
            }
        }
        return retVal;
    }
}

