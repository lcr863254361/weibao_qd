package com.orient.ods.atfx.business;

import com.orient.ods.atfx.model.*;
import org.asam.ods.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mengbin on 16/3/5.
 * Purpose:
 * Detail:
 */
@Service
public class AtfxFileTreeBusiness {

    public ODSNode getRootNode (AtfxSession atfxSession) throws Exception{
        if (atfxSession.getRootODSNode()!=null){
            return atfxSession.getRootODSNode();
        }
        ApplicationElement[] appl_elems = atfxSession.getAoSession().getApplicationStructure().getElementsByBaseType(AtfxTagConstants.BASE_ELEM_AOENVIMENT);
        if(appl_elems.length<1){
            return null;
        }
        else {
            ApplicationElement appl_elem = appl_elems[0];
            AoEnvirmentNode root = new AoEnvirmentNode();

            root.setNodetype(ODSNode.BASETYPE.AOEnvirment);
            root.setBaseAppElem(appl_elem);
            root.setApplicationElemName(appl_elem.getName());
            InstanceElementIterator itor =   appl_elem.getInstances("*");
            for(int i = 0 ; i <itor.getCount();i++ )
            {
                InstanceElement instanceElem = itor.nextOne();
                root.setId(instanceElem.getId());
                root.setNodeName(instanceElem.getName());
            }
            this.getChildNode(root,true);
            atfxSession.setRootODSNode(root);
            return atfxSession.getRootODSNode();
        }
    }


    public List<ODSNode> getChildNode(ODSNode parentNode,boolean cascade)throws Exception{
        ODSNode.BASETYPE nodeType =   parentNode.getNodetype();
        if(nodeType.equals(ODSNode.BASETYPE.AOEnvirment)){
            AoEnvirmentNode node = (AoEnvirmentNode)parentNode;
            return node.constructChildNodes(cascade);

        }
        else if(nodeType.equals(ODSNode.BASETYPE.AOTest)){
            AoTestNode node = (AoTestNode)parentNode;
            return node.constructChildNodes(cascade);
        }
        else if (nodeType.equals(ODSNode.BASETYPE.AOSubTest)){
            AoSubTestNode node = (AoSubTestNode)parentNode;
            return node.constructChildNodes(cascade);
        }
        else if (nodeType.equals(ODSNode.BASETYPE.AoMeasurement)){
            AoMeasurementNode node = (AoMeasurementNode)parentNode;
            return node.constructChildNodes(cascade);
        }
        else if (nodeType.equals(ODSNode.BASETYPE.AoMeasurementQuantity)){
            AoMeasurementQuantityNode node = (AoMeasurementQuantityNode)parentNode;
            return node.constructChildNodes(cascade);
        }
        else if (nodeType.equals(ODSNode.BASETYPE.AoSubMatrix)){
            AoSubMatrixNode node = (AoSubMatrixNode)parentNode;
            return node.constructChildNodes(cascade);
        }
        return parentNode.getChild();
    }


    /**
     * 根据类型和名字获取节点列表
     * @param parentNode 遍历开始的节点
     * @param name  节点名称,"*"表示所有的节点
     * @param baseType  节点的类型
     * @return
     */
    protected List<ODSNode> getNodesByName(ODSNode parentNode,String name, ODSNode.BASETYPE baseType ){
        List<ODSNode> retList =  new ArrayList<ODSNode>();

        List<ODSNode> children = parentNode.getChild();
        for (ODSNode node: children) {
            if (node.getNodetype()==baseType&&(node.getNodeName().contains(name)||name.equals("*"))) {
                retList.add(node);
            }
            List<ODSNode> ret =  this.getNodesByName(node,name,baseType);
            retList.addAll(ret);
        }
        return retList;
    }

    /**
     *
     * @param parentNode
     * @param nodeId
     * @param baseType
     * @return
     */
    protected List<ODSNode> getNodesById(ODSNode parentNode,String nodeId, ODSNode.BASETYPE baseType ){
        List<ODSNode> retList =  new ArrayList<ODSNode>();

        List<ODSNode> children = parentNode.getChild();
        for (ODSNode node: children) {
            if (node.getNodetype()==baseType&&nodeId.equals(node.getStringId())) {
                retList.add(node);
            }
            List<ODSNode> ret =  this.getNodesById(node,nodeId,baseType);
            retList.addAll(ret);
        }
        return retList;
    }


    /**
     * 获取树形向上的所有Node
     * @param treeNode
     * @return node的列表是从根往后排  root->child->child->....->child
     */
    public List<ODSNode> getParentNodeList(ODSNode treeNode){
        List<ODSNode> retList = new ArrayList<>();
        while (true){
            treeNode = treeNode.getParentNode();
            if ( treeNode==null) {
                break;
            }
            retList.add(treeNode);
        }
        return  retList;
    }

}
