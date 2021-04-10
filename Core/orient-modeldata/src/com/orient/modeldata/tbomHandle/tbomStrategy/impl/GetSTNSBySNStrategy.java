package com.orient.modeldata.tbomHandle.tbomStrategy.impl;

import com.orient.web.model.BaseNode;
import com.orient.modeldata.bean.TBomNode;
import com.orient.modeldata.bean.TBomStaticNode;
import com.orient.modeldata.bean.TBomStaticNodeMaterial;
import com.orient.modeldata.eventParam.GetTbomNodesEventParam;
import com.orient.modeldata.tbomHandle.tbomStrategy.TbomNodeStrategy;
import com.orient.modeldata.tbomHandle.template.TbomStaticNodeBuilder;
import com.orient.sysmodel.operationinterface.IRole;
import com.orient.sysmodel.operationinterface.ITbom;
import com.orient.sysmodel.roleengine.impl.RoleUtilImpl;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 根据静态Tbom节点获取静态Tbom子节点
 * Get Static Tbom Nodes By Static Node
 *
 * @author enjoy
 * @createTime 2016-05-22 10:41
 */
@Component
public class GetSTNSBySNStrategy implements TbomNodeStrategy {

    @Autowired
    protected RoleUtilImpl roleEngine;

    @Autowired
    protected TbomStaticNodeBuilder tbomStaticNodeBuilder;

    @Override
    public List<BaseNode> getSonTbomNodes(GetTbomNodesEventParam getTbomNodesEventParam) {
        List<BaseNode> retVal = new ArrayList<>();
        TBomNode parentNode = getTbomNodesEventParam.getFatherNode();
        if (null != parentNode) {
            if (parentNode.getNodeType().equalsIgnoreCase(TBomNode.STATIC_NODE)) {
                //如果父节点是静态节点
                TBomStaticNode tBomStaticNode = (TBomStaticNode) parentNode;
                //获取静态子节点
                List<TBomNode> staticChildrenNodes = getStaticChildNodesByStaticPNode(tBomStaticNode);
                //静态子节点自动展开
                staticChildrenNodes.forEach(node->{
                    node.setExpanded(true);
                });
                retVal.addAll(staticChildrenNodes);
            }
        }
        //排序
        retVal.sort((n1, n2) -> ((TBomStaticNode) n1).getOrder().intValue() - ((TBomStaticNode) n2).getOrder().intValue());
        return retVal;
    }

    /**
     * @param parentNode 父节点信息
     * @return 根据静态父节点 获取 其下的静态子节点信息
     */
    private List<TBomNode> getStaticChildNodesByStaticPNode(TBomStaticNode parentNode) {
        //返回值
        List<TBomNode> retVal = new ArrayList<>();
        //获取当前登录用户ID
        String userId = UserContextUtil.getUserId();
        //获取所属tbom
        String treeId = parentNode.getTbomId();
        //获取当前结点ID
        String nodeId = parentNode.getStaticDbId();
        //get current user's role list
        List<IRole> roleList = roleEngine.getRoleModel(false).getRolesOfUser(userId);
        List<ITbom> exists = new ArrayList<>();
        //for each
        roleList.forEach(role -> {
            //get all tboms in this role
            List<ITbom> tboms = role.getAllTboms();
            tboms.forEach(tbom -> {
                if (!exists.contains(tbom)) {
                    if (tbom.getId().equalsIgnoreCase(treeId)) {
                        //获取当前tbom下的子节点信息
                        ITbom node = tbom.getchildNodebyNodeId(nodeId);
                        Set<ITbom> childNodes = node.getChildTboms();
                        //遍历循环
                        childNodes.forEach(cn -> {
                            //如果节点有效
                            if (cn.getIsValid() != 0) {
                                //注入基本属性
                                TBomStaticNodeMaterial tBomStaticNodeMaterial = new TBomStaticNodeMaterial(cn.getId(), treeId, parentNode, cn);
                                retVal.addAll(tbomStaticNodeBuilder.builderTBomNode(tBomStaticNodeMaterial));
                            }
                        });
                    }
                    exists.add(tbom);
                }
            });
        });
        return retVal;
    }
}

