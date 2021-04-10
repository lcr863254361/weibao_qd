package com.orient.ods.atfx.business;

import com.orient.ods.atfx.model.AoSubMatrixNode;
import com.orient.ods.atfx.model.ODSNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengbin on 16/3/10.
 * Purpose:
 * Detail:
 */
@Service
public class AtfxMatrixBusiness extends AtfxFileTreeBusiness {

    public AoSubMatrixNode getAoSubMatrixNodeByName(String name, ODSNode rootNode){

        List<ODSNode>  nodeList = getNodesByName(rootNode,name,ODSNode.BASETYPE.AoSubMatrix);
        for (ODSNode node: nodeList)
        {
            return (AoSubMatrixNode) node;
        }
        return null;
    }

    public AoSubMatrixNode getAoSubMatrixNodeById(String nodeId, ODSNode rootNode ){
        List<ODSNode>  nodeList = getNodesById(rootNode,nodeId,ODSNode.BASETYPE.AoSubMatrix);
        for (ODSNode node: nodeList)
        {
            return (AoSubMatrixNode) node;
        }
        return null;
    }

}

