/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.ods.atfx.business;

import com.orient.ods.atfx.model.AoMeasurementNode;
import com.orient.ods.atfx.model.AoSubMatrixNode;
import com.orient.ods.atfx.model.ODSNode;
import com.orient.ods.atfx.model.OrientNameValueUnit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mengbin on 16/3/18.
 * Purpose:
 * Detail:
 */
@Service
public class AtfxMeasurmentBusiness extends  AtfxFileTreeBusiness{

    public List<AoMeasurementNode> getMesurenmentNodes(ODSNode rootNode){
        List<AoMeasurementNode> retList = new ArrayList<>();
        List<ODSNode>  nodeList = getNodesByName(rootNode,"*",ODSNode.BASETYPE.AoMeasurement);
        for (ODSNode node: nodeList) {
            retList.add((AoMeasurementNode) node);
        }
        return retList;
    }

    public AoMeasurementNode getAoMeasureMentNodeById(ODSNode rootNode, String Id){
        List<ODSNode>  nodeList = getNodesById(rootNode,Id,ODSNode.BASETYPE.AoMeasurement);
        if (nodeList.size()==0){
            return null;
        }
        return (AoMeasurementNode) nodeList.get(0);
    }

    public List<Map<String,String>> getMeasurementInfo(AoMeasurementNode node){

        List<Map<String,String>> retList = new ArrayList<>();
        Map<String,String> retMap = new HashMap<>();
        retMap.put("key","id");
        retMap.put("value",node.getStringId());
        retList.add(retMap);
        retMap = new HashMap<>();
        retMap.put("key","name");
        retMap.put("value",node.getNodeName());
        retList.add(retMap);
        for (OrientNameValueUnit name_value:node.getProperties().values()) {
            retMap = new HashMap<>();
            retMap.put("key",name_value.name);
            retMap.put("value",name_value.value);
            retList.add(retMap);
        }
        return  retList;
    }






}
